package io.oz.xv.glsl.shaders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Matrix4;

import io.oz.xv.ecs.c.Visual;

/**Uniforms helper, in ecs &amp; xv style, not gdx's.
 * 
 * @author Odys Zhou
 */
class XUniforms {
	/** the component usually used for driven by {@link io.oz.xv.ecs.s.SysTween SysTween}, etc. */
	private Visual vis;
	private WShader shader;

	public XUniforms(WShader shader, Visual visual) {
		this.shader = shader;
		this.vis = visual;
	}

	public XUniforms f1(int name, Float f) {
		vis.uniforms.put(name, (Float)f);
		return this;
	}

	public XUniforms f1(int name) {
		float f = (float)vis.uniforms.get(name);
		shader.set(name, f );
		return this;
	}

	public XUniforms f1(int name, float f) {
		vis.uniforms.put(name, (Float)f);
		shader.set(name, f );
		return this;
	}

	public XUniforms sampler2D(int texName, Texture tex, RenderContext context) {
		int texBound; 
		if (!vis.uniforms.containsKey(texName)) {
			texBound = context.textureBinder.bind(tex);
			vis.uniforms.put(texName, texBound);
		}
		else
			texBound = (int)vis.uniforms.get(texName);

		shader.set(texName, texBound);
		return this;
	}

	public XUniforms sampler2D(int texName) {
		int texBound = (int)vis.uniforms.get(texName);
		shader.set(texName, texBound);
		return this;
	}

	public void m4(int name, Matrix4 m) {
		vis.uniforms.put(name, m);
		shader.set(name, m);
	}

	public void m4(int name) {
		Matrix4 m = (Matrix4) vis.uniforms.get(name);
		shader.set(name, m);
	}
}