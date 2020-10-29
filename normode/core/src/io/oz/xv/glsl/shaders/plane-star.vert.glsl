#version 300 es

uniform mat4 u_vpMat4;
uniform mat4 u_modelMat4;
uniform mat4 u_shiftM4;

in vec4 a_position;
in vec3 a_normal;
in vec2 a_texCoord0;

out vec2 vUv;
out float vRoty;

void main() {
    // gl_Position = u_vpMat4 * u_modelMat4 * vec4((vec3(a_position.xy, 0.) + a_normal), 1.);
    vec4 v0 = u_vpMat4 * u_modelMat4 * vec4(a_normal, 1.);
    gl_Position = vec4(a_position.xy, 0., 0.) + v0;

    vUv = vec2( floor(a_position.z), fract(a_position.z) * 10.);
    
    vRoty = a_texCoord0.y;
}
