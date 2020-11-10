package io.oz.xv.glsl.shaders;

import com.badlogic.gdx.graphics.Texture;

import io.oz.xv.ecs.c.Visual;
import io.oz.xv.glsl.WShader;

/**Uniforms helper, in ecs &amp; xv style, not gdx's.
 * 
 * @author Odys Zhou
 */
class XUniforms {
	private Visual vis;
	private WShader shader;

	public XUniforms(WShader shader, Visual visual) {
		this.shader = shader;
		this.vis = visual;
	}

	public XUniforms f1(Visual visual, int name, Float f) {
		vis.uniforms.put(name, (Object)f);
		return this;
	}

	public XUniforms f1(int name) {
		float f = (float)vis.uniforms.get(name);
		shader.set(name, f );
		return this;
	}

	public XUniforms sampler2D(int texName, Texture tex) {
		int texBound; 
		if (!vis.uniforms.containsKey(texName)) {
			texBound = shader.context.textureBinder.bind(tex);
			vis.uniforms.put(texName, texBound);
		}
		else
			texBound = (int)vis.uniforms.get(texName);

		shader.set(texName, texBound);
		return this;
	}
}