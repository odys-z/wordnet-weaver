uniform mat4 u_vpMat4;
uniform mat4 u_modelMat4;

attribute vec4 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;
varying vec2 v_uv;

void main() {
    gl_Position = u_vpMat4 * u_modelMat4 * a_position;
    v_uv = a_texCoord0;
}
