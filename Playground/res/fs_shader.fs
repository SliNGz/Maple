#version 440

in gl_PerFragment
{
    vec4 gl_Color;
};

out vec4 out_Color;

void main()
{
	out_Color = gl_Color;
}