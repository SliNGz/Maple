#version 440

in gl_PerFragment
{
    vec4 gl_Color;
};
in vec2 in_TexCoords;

out vec4 out_Color;

uniform sampler2D u_Texture;
uniform vec4 u_Color;

void main()
{
	vec4 texture_color = texture(u_Texture, in_TexCoords);
	out_Color = vec4(texture_color.rgb * u_Color.rgb, texture_color.a);
}