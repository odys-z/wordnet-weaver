#version 300 es
precision highp float;

layout (location = 0) out vec4 fragColor;

uniform float u_alpha;
uniform float u_t;
uniform sampler2D u_tex0;

in vec2 vUv;
in vec4 vcolor;
in float vRoty;

float occlude( in vec2 uv ) {
	float d = 1. - length(uv - 0.5);
	return pow( d, 5. ) + pow( d, 2. );
}

void main() {
	vec2 uv = vUv;
	float occ = occlude(uv);
	fragColor = vcolor * occ;
	fragColor.a = occ;
}
