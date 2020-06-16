#version 440

in vec2 v_TexCoords;

out vec4 out_Color;

uniform sampler2D u_Texture;
uniform float u_Exposure;

void main()
{
	const float gamma = 2.2;
    vec3 hdrColor = texture(u_Texture, v_TexCoords).rgb;

    // exposure tone mapping
    vec3 mapped = vec3(1.0) - exp(-hdrColor * u_Exposure);
    // gamma correction
    mapped = pow(mapped, vec3(1.0 / gamma));

    out_Color = vec4(mapped, 1.0);
}