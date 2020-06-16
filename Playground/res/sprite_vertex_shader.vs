#version 440

layout (location = 0) in vec4 a_Position;
layout (location = 1) in vec2 a_TexCoords;

out gl_PerVertex
{
    vec4 gl_Position;
};
out vec2 v_TexCoords;

uniform mat4 u_ViewProjection;
uniform mat4 u_Model;

uniform vec2 u_MaskPosition;
uniform vec2 u_MaskDimensions;

void main()
{
	vec2 tex_coords = u_MaskPosition + u_MaskDimensions * a_TexCoords;

	gl_Position = u_ViewProjection * u_Model * a_Position;
	v_TexCoords = tex_coords;
};