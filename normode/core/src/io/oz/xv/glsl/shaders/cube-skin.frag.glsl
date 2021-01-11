uniform float u_alpha;
uniform float u_t;
uniform float u_mode;
uniform sampler2D u_tex0;

varying vec2 v_uv;

void main() {
	gl_FragColor = texture2D(u_tex0, v_uv);
	gl_FragColor.a = u_alpha;
	if (u_mode > 0.5) {
		gl_FragColor.g = 1.;
	}
	else {
		gl_FragColor.b = 1.;
	}
}
