#version 300 es
layout (location = 0) out vec4 fragColor;

uniform float u_alpha;
uniform float u_t;
uniform sampler2D u_tex0;

in vec2 vUv;
in float vRoty;

void main() {
	// vec2 uv = vec2(dot(vec2(0., -cos(vRoty), vUv), dot(vec2(sin(vRoty), 0), vUv);
	uv = vUv;
	fragColor = texture(u_tex0, uv);
	fragColor.a = u_alpha;
	fragColor.g = vRoty;
}
