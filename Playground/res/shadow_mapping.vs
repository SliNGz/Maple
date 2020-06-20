#version 440

layout (location = 0) in vec4 a_Position;
layout (location = 1) in vec4 a_Color;
layout (location = 2) in vec3 a_Normal;

out gl_PerVertex
{
	vec4 gl_Position;
	vec4 gl_Color;
};

out VS_OUT {
    vec3 Normal;
    vec3 FragPosition;
    vec4 FragPositionLightSpace;
} vs_out;

uniform mat4 u_ViewProjection;
uniform mat4 u_Model;

uniform mat4 u_LightViewProjection;

void main()
{
    vec4 worldPosition = u_Model * a_Position;

	gl_Position = u_ViewProjection * worldPosition;
	gl_Color = a_Color;
	vs_out.Normal = mat3(transpose(inverse(u_Model))) * a_Normal;
	vs_out.FragPosition = worldPosition.xyz;
	vs_out.FragPositionLightSpace = u_LightViewProjection * worldPosition;
};