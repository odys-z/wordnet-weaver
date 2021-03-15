package io.oz.xv.ecs.s;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import io.oz.xv.ecs.c.AffineAnim;
import io.oz.xv.ecs.c.Obj3;
import io.oz.xv.gdxpatch.g3d.XModelInstance;
import patch.net.mgsx.gltf.scene3d.animation.XAnimationController;

/**Is this the transformation tweener?
 * 
 * @author Odys Zhou
 *
 */
public class SysAffine3 extends IteratingSystem {

	private ComponentMapper<AffineAnim> mAffine;
	private ComponentMapper<Obj3> mObj3;
	private boolean inAction; // FIXME move to AffineAnim

	public SysAffine3() {
		super(Family.all(AffineAnim.class, Obj3.class).get());
		mAffine = ComponentMapper.getFor(AffineAnim.class);
		mObj3 = ComponentMapper.getFor(Obj3.class);
		inAction = true; // check at first
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		AffineAnim a = mAffine.get(entity);
		if (inAction) {
			// inAction = false;

			Obj3 obj = mObj3.get(entity);
			XModelInstance mi = obj.modInst;
			XAnimationController anctrl = a.controllor;
			if (mi != null && anctrl != null) {
				anctrl.update(deltaTime);
				mi.calculateTransforms(); // not necessary?

				inAction |= anctrl.inAction;
			}
		}
	}
	
	public SysAffine3 notifiy() {
		inAction = true;
		return this;
	}

}
