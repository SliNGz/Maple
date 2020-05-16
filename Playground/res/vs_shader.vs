#version 440

layout (location = 0) in vec4 in_position;
layout (location = 1) in vec4 in_color;

out gl_PerVertex
{
	vec4 gl_Position;
	vec4 gl_Color;
};

uniform mat4 u_mvp;
uniform mat4 u_transform;

void main()
{
	gl_Position = u_mvp * u_transform * in_position;
	gl_Color = in_color;
};