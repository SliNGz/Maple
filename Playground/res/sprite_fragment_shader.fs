#version 440

in vec2 v_TexCoords;

out vec4 out_Color;

uniform sampler2D u_Texture;

void main()
{
	out_Color = texture(u_Texture, v_TexCoords);
}