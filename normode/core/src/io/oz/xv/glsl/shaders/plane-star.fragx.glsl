#version 300 es
precision highp float;

layout (location = 0) out vec4 fragColor;

uniform float u_alpha;
uniform float u_time;
uniform sampler2D u_tex0;

//Flaring by nimitz (stormoid.com)
uniform float u_brightness; //! slider[0.01, 0.9, 3]
uniform float u_ray_brightness; //!slider [0.01, 12.0, 20]
uniform float u_gamma; //! slider[0.01, 2.33, 20]
uniform float u_spot_brightness; //! slider[0, 1.75, 10]
uniform float u_ray_density; //! slider[0.01, 3.3, 9.0]
uniform float u_curvature; //! slider[0.001, 0.6, 30]
uniform float u_sin_freq; //! slider[0, 54, 150];

in vec2 vUv;
in vec4 vcolor;
in float vRoty;

float occlude( in vec2 uv ) {
	float d = 1. - length(uv - 0.5);
	return pow( d, 5. ) + pow( d, 2. );
}

float hash(float n) {
    return fract(sin(n) * 43758.5453);
}

float noise( in vec2 x) {
    // return texture(u_tex0, x / (1. + x)).x;
    x = x / (1. + x);
	if (x.x > x.y)
		return smoothstep(0.1, 0.9, x.x);
	else
		return smoothstep(0.1, 0.9, -x.y);
}

mat2 m2 = mat2(0.80, 0.60, -0.60, 0.80);
float fbm( in vec2 p, in float sin_freq) {
    float z = 2.;
    float rz;
    p *= 0.25;
    for (float i = 1.; i < 6.; i++) {
        rz += (sin(noise(p) * sin_freq) * 0.5 + 0.5) / z;
        z = z * 2.;
        p = p * 2. * m2;
    }
    return rz;
}

void main() {
	float brightness = u_brightness; //! slider[0.01, 0.9, 3]
	brightness = 0.9;

	float ray_brightness = u_ray_brightness; //!slider [0.01, 12.0, 20]
	ray_brightness = 12.0;

	float gamma = u_gamma; //! slider[0.01, 2.33, 20]
	gamma = 2.33;

	float spot_brightness = u_spot_brightness; //! slider[0, 1.75, 10]
	spot_brightness = 7.; // 1.75;

	float ray_density = u_ray_density; //! slider[0.01, 3.3, 9.0]
	ray_density = 3.3;

	float curvature = u_curvature; //! slider[0.001, 0.6, 30]
	curvature = 3.6; // 0.6

	float sin_freq = u_sin_freq; //! slider[0, 54.0, 150];
	sin_freq = 54.0;
	
	float t = -u_time * 0.03;
	vec2 uv = vUv;
	uv.x *= 4. / 3.; // how ? iResolution.x / iResolution.y;
	uv *= curvature * .1 + 0.0001;

	float r = sqrt(dot(uv, uv));
	float x = dot(normalize(uv), vec2(.5, 0.)) + t;
	float y = dot(normalize(uv), vec2(.0, .5)) + t;

	float val;
	val = fbm(vec2(r + y * ray_density, r + x * ray_density - y), sin_freq);
	val = smoothstep(gamma * .02 - .1,
			ray_brightness + (gamma * 0.02 - .1) + .1, val);
	val = sqrt(val);

	vec3 col = vec3(val / vcolor.r, val / vcolor.g, val / vcolor.b);
	col = clamp(1. - col, 0., 1.);
	col = mix(col, vec3(1.),
			// spot_brightness - r / 0.1 / curvature * 200. / brightness);
			spot_brightness - r / 0.1 / curvature * 200. / brightness);

	float occ = occlude( vUv );
	fragColor = vec4(col * occ, occ);

	// debug section
	// fragColor = texture(u_tex0, vUv);
	fragColor.r = length(uv);
}

