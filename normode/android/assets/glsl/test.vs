uniform vec3 CameraPos;
uniform mat4 ModelWorld;
uniform mat4 vpMatrix;

attribute vec4 a_position;
attribute vec3 a_normal;

varying vec3 vColor;

void main() {
	vec4 pos4 = ModelWorld * vec4( a_position );
	vColor = normalize(pos4.xyz);
	gl_Position = vpMatrix * pos4;
}

