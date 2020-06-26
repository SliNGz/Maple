#version 440

layout (location = 0) in vec4 a_Position;
layout (location = 1) in vec4 a_Color;

out gl_PerVertex
{
	vec4 gl_Position;
	vec4 gl_Color;
};

uniform mat4 u_ViewProjection;
uniform mat4 u_Model;

void main()
{
	gl_Position = u_ViewProjection * u_Model * a_Position;
	gl_Color = a_Color;
};