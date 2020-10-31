#version 300 es

uniform mat4 u_vpMat4;
uniform mat4 u_viewM4;
uniform mat4 u_modelMat4;
uniform mat4 u_shiftM4;

in vec4 a_position;
in vec3 a_normal;
in vec4 a_color;
in vec2 a_texCoord0;

out vec2 vUv;
out float vRoty;

void main() {
    // gl_Position = u_vpMat4 * u_modelMat4 * vec4((vec3(a_position.xy, 0.) + a_normal), 1.);

	/*
    vec4 v0 = u_vpMat4 * u_modelMat4 * vec4(a_normal, 1.);
    gl_Position = vec4(a_position.xy, 0., 0.) + v0;
    */
 
 	// solution 2
 	// http://www.opengl-tutorial.org/intermediate-tutorials/billboards-particles/billboards/
	// camRight = {ViewMatrix[0][0], ViewMatrix[1][0], ViewMatrix[2][0]}
	// camUp = {ViewMatrix[0][1], ViewMatrix[1][1], ViewMatrix[2][1]}

	vec3 camRight = vec3(u_viewM4[0][0], u_viewM4[1][0], u_viewM4[2][0]);
	vec3 camUp = vec3(u_viewM4[0][1], u_viewM4[1][1], u_viewM4[2][1]);

	vec3 wordp = a_normal
		+ camRight * a_position.x
		+ camUp * a_position.y;

	gl_Position = u_vpMat4 * vec4(wordp, 1.);

    vUv = vec2( floor(a_position.z), fract(a_position.z) * 10. );
    vRoty = a_texCoord0.y;
}
