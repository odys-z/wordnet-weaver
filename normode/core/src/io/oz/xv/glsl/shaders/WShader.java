package io.oz.xv.glsl.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;

import io.oz.xv.ecs.c.Visual;
import io.oz.xv.glsl.Glsl.ShaderFlag;
import io.oz.xv.material.XMaterial;

/**Common uniforms:
 * 1. u_vpMat4
 * 2. u_modelMat4
 * 3, u_selected
 * @author Odys Zhou
 *
 */
public class WShader extends BaseShader implements Shader {
	/** uniform name of 'float u_mode' - float be cause no bitwise operation */
	public int u_mode = -1;

	protected int u_vpM4 = register("u_vpMat4");
	/** u_viewM4, enabled via {@link #enableViewM4()} */
	protected int u_vM4 = -1;
	protected int u_modelM4 = register("u_modelMat4");

	protected ShaderFlag flag;
	Visual visual;
	XUniformer unis;

	public ShaderFlag flag() { return flag; }
	
	/**
	 * @param flag shader flag for testing with {@link #canRender(Renderable)}.
	 * @param visual given Visual that ecs tweening, moving or updating
	 * - can be null if not for ECS pattern.
	 */
	public WShader(ShaderFlag flag, Visual visual) {
		super();
		this.flag = flag;
		if (visual == null) {
			// this is not a ECS material
			this.visual = new Visual();
		}
		this.visual = visual;
		this.unis = new XUniformer(this, visual);

		u_mode = register("u_mode");
	}

	@Override
	public void init() {
		super.init(program, null);
	}
	
	public WShader enableViewM4() {
		u_vM4 = register("u_viewM4");
		return this;
	}

	@Override
	public int compareTo(Shader other) {
		return 0;
	}

	@Override
	public boolean canRender(Renderable instance) {
		Material mat = instance.material;

		// shader provide example:
		// if (renderable.material.has(TestAttribute.ID)) return new TestShader(renderable);
		// return mat instanceof XMaterial; // && ((XMaterial)mat).shader() == this;
		return mat instanceof XMaterial && ((XMaterial)mat).acceptShader(this);
	}

	@Override
	public void begin (Camera camera, RenderContext context) {
		program.begin();
		context.setDepthTest(GL20.GL_LEQUAL, 0f, 1f);
		context.setDepthMask(true);
		camera.update();

		unis.m4(u_vpM4, camera.combined);
		if (this.visual.needsUpdateUniforms)
			unis.f1(u_mode);
	}
	
	@Override
	public void render(Renderable renderable) {
		// unis.f1(u_mode);
		unis.m4(u_modelM4, renderable.worldTransform);

		// https://stackoverflow.com/questions/8594703/lwjgl-transparency
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		renderable.meshPart.render(program);	
	}

	/**Update visual, e.g. turn on when selected, should been ovrriden by subclasses.
	 * @return
	 */
	public WShader setVisual(int cmd, float val) {
		return this;
	}

}
