package io.oz.xv.gdxpatch;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import io.oz.xv.glsl.WShader;

/**A modifed version of gdx <a href='https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/graphics/g3d/utils/BaseShaderProvider.java'>
 * BaseShaderProvider</a>
 * 
 * @author Odys Zhou
 *
 */
public abstract class XShaderProvider implements ShaderProvider {
	protected Array<Shader> shaders = new Array<Shader>();

	@Override
	public Shader getShader (Renderable renderable) {
		Shader suggestedShader = renderable.shader;
		if (suggestedShader != null && suggestedShader.canRender(renderable))
			return suggestedShader;

		for (Shader shader : shaders) {
			if (shader.canRender(renderable)) return shader;
		}

		final Shader shader = createShader(renderable);
		if (!shader.canRender(renderable))
			if (shader instanceof WShader)
				;
			else
				throw new GdxRuntimeException("unable to provide a shader for this renderable");

		shader.init();
		shaders.add(shader);
		return shader;
	}

	protected abstract Shader createShader (final Renderable renderable);

	@Override
	public void dispose () {
		for (Shader shader : shaders) {
			shader.dispose();
		}
		shaders.clear();
	}
}
