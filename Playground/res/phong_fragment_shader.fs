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
uniform float u_AmbientIntensity;

void main()
{
    vec3 normal = normalize(v_Normal);

    vec4 ambient = u_AmbientIntensity * u_LightColor;

    vec3 lightDirection = normalize(u_LightPosition - v_FragPosition);
    float diffuseIntensity = max(dot(normal, lightDirection), 0.0F);
    vec4 diffuse = diffuseIntensity * u_LightColor;

    vec3 cameraDirection = normalize(u_CameraPosition - v_FragPosition);
    vec3 reflectedLightDirection = reflect(-lightDirection, normal);
    float specularIntensity = pow(max(dot(cameraDirection, reflectedLightDirection), 0.0F), 32);
    vec4 specular = specularIntensity * 0.5F * u_LightColor;

	out_Color = gl_Color * (ambient + diffuse + specular);
}