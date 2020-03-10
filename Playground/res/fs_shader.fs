#version 440

in gl_PerFragment
{
    vec4 gl_Color;
};

out vec4 out_color;

void main()
{
	out_color = gl_Color;
}