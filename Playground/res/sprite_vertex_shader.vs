#version 440

layout (location = 0) in vec4 in_Position;
layout (location = 1) in vec2 in_TexCoords;

out gl_PerVertex
{
    vec4 gl_Position;
};
out vec2 out_TexCoords;

uniform mat4 u_MVP;

uniform vec2 u_MaskPosition;
uniform vec2 u_MaskDimensions;

void main()
{
	vec2 tex_coords = u_MaskPosition + u_MaskDimensions * in_TexCoords;

	gl_Position = u_MVP * in_Position;
	out_TexCoords = tex_coords;
};