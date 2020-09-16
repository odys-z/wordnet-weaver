package io.oz.wnw.ecs.sys;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;

import io.oz.wnw.ecs.cmp.Affines;
import io.oz.wnw.ecs.cmp.Obj3;
import io.oz.wnw.ecs.cmp.ds.AffineTrans;

public class SysAffine extends IteratingSystem {

	private ComponentMapper<Affines> mAffine;
	private ComponentMapper<Obj3> mObj3;

	/**Buffer */
	Matrix4 _m4;

	public SysAffine() {
		super(Family.all(Affines.class, Obj3.class).get());
		mAffine = ComponentMapper.getFor(Affines.class);
		mObj3 = ComponentMapper.getFor(Obj3.class);
		
		_m4 = new Matrix4().idt();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Affines a = mAffine.get(entity);
		if (a.dirty) {
			a.dirty = false;
			Obj3 obj = mObj3.get(entity);
			ModelInstance mi = obj.modInst;
			mi.calculateTransforms(); // not necessary?
			transform(mi.transform, a);
		}
	}

	private Matrix4 transform(Matrix4 target, Affines a) {
		for (AffineTrans t : a.transforms)
			switch (t.a) {
				case rotation:
					_m4.rotate(t.rotation);
					break;
				case translate:
					_m4.translate(t.translate);
					break;
				case scale:
					_m4.scl(t.scale);
					break;
				default:
			}
		return target.set(_m4);
	}

}
