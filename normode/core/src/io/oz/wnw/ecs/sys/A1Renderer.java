package io.oz.wnw.ecs.sys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

/** @deprecated replaced by {@link SysModelRenderer}? */
public class A1Renderer extends IteratingSystem {

	public A1Renderer(Family family) {
		super(family);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

	}

}
