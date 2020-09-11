package io.oz.wnw.ecs.sys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import io.oz.wnw.ecs.cmp.WTween;

public class SysTween extends IteratingSystem {

	public SysTween() {
		super(Family.all(WTween.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
	}

}
