#version 440

layout (location = 0) in vec4 in_Position;
layout (location = 1) in vec4 in_Color;

out gl_PerVertex
{
	vec4 gl_Position;
	vec4 gl_Color;
};

uniform mat4 u_MVP;

void main()
{
	gl_Position = u_MVP * in_Position;
	gl_Color = in_Color;
};