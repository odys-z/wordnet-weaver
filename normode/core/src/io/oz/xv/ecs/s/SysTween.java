package io.oz.xv.ecs.s;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import io.oz.xv.ecs.c.Tweened;

/**Tween driver for updating uniforms, transformation, time tick in shaders, and more.
 * @author Odys Zhou
 *
 */
public class SysTween extends IteratingSystem {

	public SysTween() {
		super(Family.all(Tweened.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
	}

}
