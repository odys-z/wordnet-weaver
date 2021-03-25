package io.oz.xv.ecs.s;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import io.oz.xv.ecs.c.AffineAnim;
import io.oz.xv.ecs.c.Obj3;
import io.oz.xv.gdxpatch.g3d.XModelInstance;
import patch.net.mgsx.gltf.scene3d.animation.XAnimationController;

/**Affine transformation driver?
 * 
 * @author Odys Zhou
 *
 */
public class SysAffine3 extends IteratingSystem {

	private ComponentMapper<AffineAnim> mAffine;
	private ComponentMapper<Obj3> mObj3;

	public SysAffine3() {
		super(Family.all(AffineAnim.class, Obj3.class).get());
		mAffine = ComponentMapper.getFor(AffineAnim.class);
		mObj3 = ComponentMapper.getFor(Obj3.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		AffineAnim a = mAffine.get(entity);
		if (a.playing) {

			Obj3 obj = mObj3.get(entity);
			XModelInstance mi = obj.modInst;
			XAnimationController anctrl = a.controllor;
			if (mi != null && anctrl != null) {
				a.playing |= anctrl.update(deltaTime);
			}
		 }
	}
	
	public SysAffine3 notifiy() {
		return this;
	}

}
