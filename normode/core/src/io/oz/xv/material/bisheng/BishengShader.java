package io.oz.xv.material.bisheng;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;

/**Distance Field Font shader. */
class BishengShader extends ShaderProgram {
	public BishengShader (String ...font) {
		super(Gdx.files.internal(fontPath("vs", font)),
			  Gdx.files.internal(fontPath("fs", font)));
		if (!isCompiled()) {
			throw new RuntimeException("Shader compilation failed:\n" + getLog());
		}
	}

	private static String fontPath(String suffix, String... font) {
		return String.format("glsl/%s.%s",
			font != null && font.length > 0 ? font[0] : "distancefield", "vs");
	}

	/** @param smoothing a value between 0 and 1 */
	public void setUniforms (float smoothing) {
		float delta = 0.5f * MathUtils.clamp(smoothing, 0, 1);
		setUniformf("u_lower", 0.5f - delta);
		setUniformf("u_upper", 0.5f + delta);
		setUniformi("u_texture", 0);
	}
}