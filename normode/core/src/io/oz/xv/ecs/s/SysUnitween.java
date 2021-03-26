package io.oz.xv.ecs.s;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import aurelienribon.tweenengine.TweenManager;
import io.oz.xv.ecs.c.Obj3;
import io.oz.xv.ecs.c.Visual;

/**Uniform tween driver. If a shader are driven by SysUnitween,
 * via {@link Unitween} component, it must have 2 uniform, u_time &amp; u_t.
 * 
 * @author odys-z@github.com
 *
 */
public class SysUnitween extends IteratingSystem {

	private static TweenManager unimanager;
	private ComponentMapper<Unitween> mUnitween;
	private ComponentMapper<Visual> mVis;

	public SysUnitween() {
		super(Family.all(Unitween.class, Obj3.class).get());
		mUnitween = ComponentMapper.getFor(Unitween.class);
		mVis = ComponentMapper.getFor(Visual.class);
	}

	@Override
	public void update(float deltaTime) {
		unimanager.update(deltaTime);
		super.update(deltaTime);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Unitween u = mUnitween.get(entity);
		Visual v = mVis.get(entity);
		if (u.dirty || u.autoUpdate) {
			u.manager.update(deltaTime);
			v.uniforms.put(u.u_time, deltaTime);
			v.uniforms.put(u.u_t, u.t);
		}

	}

	/**Digest uniform scripts, setup tween animation.
	 * @param ut with ut.scripts to be digested.
	 */
	public static void initUniform(Unitween ut, Visual vis) {
		if (unimanager == null)
			unimanager = new TweenManager();
		
		ut.manager = unimanager;
		
		ut.u_time = vis.shader.register("u_time");
		ut.u_t = vis.shader.register("u_t");
		ut.t = 0;
	}

}
