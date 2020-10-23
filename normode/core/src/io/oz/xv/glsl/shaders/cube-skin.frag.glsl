uniform float u_alpha;
uniform float u_t;
uniform sampler2D u_tex0;

varying vec2 v_uv;

void main() {
	gl_FragColor = texture2D(u_tex0, v_uv);
	gl_FragColor.a = u_alpha;
	gl_FragColor.g *= 3.;
}
