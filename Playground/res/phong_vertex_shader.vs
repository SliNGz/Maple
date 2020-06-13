#version 440

layout (location = 0) in vec4 a_Position;
layout (location = 1) in vec4 a_Color;
layout (location = 2) in vec3 a_Normal;

out gl_PerVertex
{
	vec4 gl_Position;
	vec4 gl_Color;
};

out vec3 v_Normal;
out vec3 v_FragPosition;

uniform mat4 u_ViewProjection;
uniform mat4 u_Model;

void main()
{
    vec4 worldPosition = u_Model * a_Position;

	gl_Position = u_ViewProjection * worldPosition;
	gl_Color = a_Color;
	v_Normal = mat3(transpose(inverse(u_Model))) * a_Normal;
	v_FragPosition = worldPosition.xyz;
};