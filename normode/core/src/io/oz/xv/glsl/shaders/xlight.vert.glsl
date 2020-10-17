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

uniform vec4 u_cameraPosition;

uniform mat4 u_projViewTrans;
uniform mat3 u_normalMatrix;
uniform mat4 u_worldTrans;

uniform float u_shininess;
uniform vec3 u_ambientLight;

attribute vec4 a_color;
attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;

varying vec4 v_color;
varying vec3 v_normal;
varying vec3 v_lightDiffuse;
varying vec3 v_lightSpecular;

${u_dirLights}
${u_pointLights}

uniform mat4 u_shadowMapProjViewTrans;
varying vec3 v_shadowMapUv;

varying vec3 v_ambientLight;

void main() {
	
	v_color = a_color;
	vec4 pos = u_worldTrans * vec4(a_position, 1.0);
	gl_Position = u_projViewTrans * pos;
		
	// ${shadowMapFlag}
		vec4 spos = u_shadowMapProjViewTrans * pos;
		v_shadowMapUv.xyz = (spos.xyz / spos.w) * 0.5 + 0.5;
		v_shadowMapUv.z = min(v_shadowMapUv.z, 0.998);
	
	// ${normalFlag - no skinning}
		vec3 normal = normalize(u_normalMatrix * a_normal);
		v_normal = normal;

    // ${no fogFlag}

	// ${lightingFlag}
        vec3 ambientLight = u_ambientLight;
		v_lightDiffuse = ambientLight;

		v_lightSpecular = vec3(0.0);
		vec3 viewVec = normalize(u_cameraPosition.xyz - pos.xyz);
			
		for (int i = 0; i < numDirectionalLights; i++) {
			vec3 lightDir = -u_dirLights[i].direction;
			float NdotL = clamp(dot(normal, lightDir), 0.0, 1.0);
			vec3 value = u_dirLights[i].color * NdotL;
			v_lightDiffuse += value;
			float halfDotView = max(0.0, dot(normal, normalize(lightDir + viewVec)));
			v_lightSpecular += value * pow(halfDotView, u_shininess);
		}

		for (int i = 0; i < numPointLights; i++) {
			vec3 lightDir = u_pointLights[i].position - pos.xyz;
			float dist2 = dot(lightDir, lightDir);
			lightDir *= inversesqrt(dist2);
			float NdotL = clamp(dot(normal, lightDir), 0.0, 1.0);
			vec3 value = u_pointLights[i].color * (NdotL / (1.0 + dist2));
			v_lightDiffuse += value;
			float halfDotView = max(0.0, dot(normal, normalize(lightDir + viewVec)));
			v_lightSpecular += value * pow(halfDotView, u_shininess);
		}
}
