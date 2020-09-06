#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

uniform sampler2D u_texture;
uniform float u_lower;
uniform float u_upper;

varying LOWP vec4 v_color;
varying vec2 v_texCoord;

void main() {
    float dist = texture2D(u_texture, v_texCoord).a;
   	float alpha = smoothstep(u_lower, u_upper, dist);
    // gl_FragColor = vec4(v_color.rgb, dist);
	// gl_FragColor.ra = vec2(0.5);
    // gl_FragColor = vec4(texture2D(u_texture, v_texCoord).rgb, alpha);

	// gl_FragColor = v_color * dist;
	// gl_FragColor = vec4(alpha, 0., 0., dist);
	gl_FragColor = vec4(dist);
}

/*
void bisheng() {
    vec4 colCoord = texture2D(u_texture1, v_texCoord);
    ivec2 paras = floor(colCoord.ba * 10.0);
    float dist = texture2D(u_texture0, colCoord.xy).a;
   	float alpha = smoothstep(u_lower, u_upper, dist);
    gl_FragColor = vec4(v_color.rgb, alpha);
}
*/