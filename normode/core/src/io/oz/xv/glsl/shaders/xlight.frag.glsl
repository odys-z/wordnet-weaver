#ifdef GL_ES
#define LOWP lowp
#define MED mediump
#define HIGH highp
precision mediump float;
#else
#define MED
#define LOWP
#define HIGH
#endif

uniform vec4 u_diffuseColor;
uniform sampler2D u_shadowTexture;
uniform float u_shadowPCFOffset;
// u_tex0?
uniform sampler2D u_diffuseTexture;
uniform vec4 u_specularColor;

varying vec3 v_normal;
varying vec4 v_color;
varying float v_opacity;

varying vec3 v_lightDiffuse;
varying vec3 v_lightSpecular;

varying vec3 v_shadowMapUv;
varying vec3 v_ambientLight;

float getShadowness(vec2 offset)
{
    const vec4 bitShifts = vec4(1.0, 1.0 / 255.0, 1.0 / 65025.0, 1.0 / 16581375.0);
    return step(v_shadowMapUv.z, dot(texture2D(u_shadowTexture, v_shadowMapUv.xy + offset), bitShifts));//+(1.0/255.0));
}

float getShadow()
{
	return (//getShadowness(vec2(0,0)) +
			getShadowness(vec2(u_shadowPCFOffset, u_shadowPCFOffset)) +
			getShadowness(vec2(-u_shadowPCFOffset, u_shadowPCFOffset)) +
			getShadowness(vec2(u_shadowPCFOffset, -u_shadowPCFOffset)) +
			getShadowness(vec2(-u_shadowPCFOffset, -u_shadowPCFOffset))) * 0.25;
}


void main() {
	vec3 normal = v_normal;

	vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV) * u_diffuseColor * v_color;
	vec3 specular = u_specularColor.rgb * v_lightSpecular;
	gl_FragColor.rgb = (diffuse.rgb * (getShadow() * v_lightDiffuse + v_ambientLight)) + specular;
	gl_FragColor.rgb = getShadow() * (diffuse.rgb * v_lightDiffuse);
	gl_FragColor.a = diffuse.a * v_opacity;

}
