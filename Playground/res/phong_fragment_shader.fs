#version 440

in gl_PerFragment
{
    vec4 gl_Color;
};

in vec3 v_Normal;
in vec3 v_FragPosition;

out vec4 out_Color;

uniform vec3 u_CameraPosition;
uniform vec3 u_LightPosition;
uniform vec4 u_LightColor;
uniform float u_LightAttenuationIntensity;
uniform float u_AmbientIntensity;

void main()
{
    vec3 normal = normalize(v_Normal);

    vec3 lightDirection = normalize(u_LightPosition - v_FragPosition);
    float diffuseIntensity = max(dot(normal, lightDirection), 0.0);

    vec3 cameraDirection = normalize(u_CameraPosition - v_FragPosition);
    vec3 reflectedLightDirection = reflect(-lightDirection, normal);

    // Blinn-Phong for future use
    // vec3 halfwayDirection = normalize(cameraDirection + lightDirection);
    // float specularIntensity = pow(max(dot(halfwayDirection, normal), 0.0F), 32);

    float specularIntensity = pow(max(dot(cameraDirection, reflectedLightDirection), 0.0), 32) * 0.5;

    float lightDistance = distance(u_LightPosition, v_FragPosition);
    float attenuation = clamp(1.0 / (1 + u_LightAttenuationIntensity * pow(lightDistance, 2)), 0.0, 1.0);

    vec4 lightColor = (u_AmbientIntensity + attenuation * (diffuseIntensity + specularIntensity)) * u_LightColor;

	out_Color = gl_Color * lightColor;
}