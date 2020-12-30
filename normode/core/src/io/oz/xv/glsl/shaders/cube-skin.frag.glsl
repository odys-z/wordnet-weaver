uniform float u_alpha;
uniform float u_t;
uniform float u_select;
uniform sampler2D u_tex0;

varying vec2 v_uv;

void main() {
	gl_FragColor = texture2D(u_tex0, v_uv);
	gl_FragColor.a = u_alpha;
	// gl_FragColor.g *= 3.;
	if (u_select > 0.5)
		gl_FragColor.rb = gl_FragColor.br;
}
