package io.oz.wnw.ecs.sys;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

import io.oz.wnw.ecs.cmp.Affine;
import io.oz.wnw.ecs.cmp.Obj3;

public class SysAffine extends IteratingSystem {

	private ComponentMapper<Affine> mAffine;
	private ComponentMapper<Obj3> mObj3;

	public SysAffine() {
		super(Family.all(Affine.class, Obj3.class).get());
		mAffine = ComponentMapper.getFor(Affine.class);
		mObj3 = ComponentMapper.getFor(Obj3.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Affine a = mAffine.get(entity);
		Obj3 obj = mObj3.get(entity);
		ModelInstance mi = obj.modInst;
	}

}
