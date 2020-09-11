package io.oz.wnw.ecs.sys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import io.oz.wnw.ecs.cmp.Affine;

public class SysAffine extends IteratingSystem {

	public SysAffine() {
		super(Family.all(Affine.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
	}

}
