#version 440

in gl_PerFragment
{
    vec4 gl_Color;
};

in VS_OUT {
    vec3 Normal;
    vec3 FragPosition;
    vec4 FragPositionLightSpace;
} fs_in;

out vec4 out_Color;

uniform sampler2D u_DepthMap;
uniform vec3 u_CameraPosition;
uniform vec3 u_LightPosition;
uniform vec4 u_LightColor;
uniform float u_LightAttenuationIntensity;
uniform float u_AmbientIntensity;

bool isShadowed()
{
    vec3 projCoords = fs_in.FragPositionLightSpace.xyz / fs_in.FragPositionLightSpace.w;
    projCoords = projCoords * 0.5 + 0.5;
    float fragDepthPerceivedByLight = texture(u_DepthMap, projCoords.xy).r;

    float fragDepth = projCoords.z;
    if (fragDepth > 1.0) {
        // Outside shadow map.
        return false;
    }

    if (fragDepth - 0.05 > fragDepthPerceivedByLight) {
        // Shadowed
        return true;
    }

    return false;
}

void main()
{
    vec3 normal = normalize(fs_in.Normal);

    vec3 lightDirection = normalize(u_LightPosition - fs_in.FragPosition);
    float diffuseIntensity = max(dot(normal, lightDirection), 0.0);

    vec3 cameraDirection = normalize(u_CameraPosition - fs_in.FragPosition);
    vec3 reflectedLightDirection = reflect(-lightDirection, normal);

    // Blinn-Phong for future use
    // vec3 halfwayDirection = normalize(cameraDirection + lightDirection);
    // float specularIntensity = pow(max(dot(halfwayDirection, normal), 0.0F), 32);

    float specularIntensity = pow(max(dot(cameraDirection, reflectedLightDirection), 0.0), 32) * 0.5;

    float lightDistance = distance(u_LightPosition, fs_in.FragPosition);
    float attenuation = clamp(1.0 / (1 + u_LightAttenuationIntensity * pow(lightDistance, 2)), 0.0, 1.0);

    float shadow = isShadowed() ? 0 : 1;

    vec4 lightColor = (u_AmbientIntensity + shadow * attenuation * (diffuseIntensity + specularIntensity)) * u_LightColor;

	out_Color = gl_Color * lightColor;
}