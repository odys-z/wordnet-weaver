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
out vec4 vcolor;
out float vRoty;

void main() {
    vUv = vec2( floor(a_position.z), fract(a_position.z) * 10. );

    gl_Position = u_vpMat4 * u_modelMat4 * vec4(a_normal, 1.);
    gl_Position.xyz += vec3(a_position.xy, 0.); // w already there

    vRoty = a_texCoord0.y;
    vcolor = a_color;
}
