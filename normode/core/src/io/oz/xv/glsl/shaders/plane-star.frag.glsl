#version 300 es
precision highp float;

layout (location = 0) out vec4 fragColor;

uniform float u_alpha;
uniform float u_t;
uniform sampler2D u_tex0;

in vec2 vUv;
in vec3 vcolor;
in float vRoty;

void main() {
	// vec2 uv = vec2(dot(vec2(0., -cos(vRoty), vUv), dot(vec2(sin(vRoty), 0), vUv);
	vec2 uv = vUv;
	fragColor = texture(u_tex0, uv);
	fragColor.a = u_alpha;
	fragColor.rgb = vcolor;
}
