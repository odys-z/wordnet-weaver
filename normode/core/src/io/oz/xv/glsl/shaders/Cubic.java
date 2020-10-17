package io.oz.xv.glsl.shaders;

import io.oz.xv.glsl.Glsl.ShaderFlag;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import io.oz.xv.glsl.WShader;

public class Cubic extends WShader {

	static String vs = String.join(GlChunks.delimiter, 
			"uniform mat4 u_modelMat4;",
			"uniform mat4 u_vpMat4;",

			"attribute vec4 a_position;",
			"attribute vec3 a_normal;",
			"attribute vec2 a_texCoord0;",
			"varying vec2 v_uv;",

			"void main() {",
			"    gl_Position = u_vpMat4 * u_modelMat4 * a_position;",
			"    v_uv = a_texCoord0;",
			"}");

	static String fs = String.join(GlChunks.delimiter, 
			"uniform float u_alpha;",
			"uniform float u_t;",
			"uniform sampler2D u_texture;",

			"varying vec2 v_uv;",

			"void main() {",
			"	gl_FragColor = texture2D(u_texture, v_uv);",
			"	gl_FragColor.a = u_alpha;",
			"}");

	public Cubic() {
		super(ShaderFlag.cubic);
		program = new ShaderProgram(vs, fs);
	}
	
}
