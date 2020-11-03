package io.oz.xv.ecs.s;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import io.oz.xv.ecs.c.Tweened;

public class SysTween extends IteratingSystem {

	public SysTween() {
		super(Family.all(Tweened.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
	}

}
