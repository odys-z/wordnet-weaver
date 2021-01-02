package io.oz.xv.ecs.s;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;

import io.oz.xv.ecs.c.RayPickable;
import io.oz.xv.ecs.c.Visual;

/**Visual state manager, where {@link Visual} is roughly equivalent of material,
 * but with extended attributes like shader states, etc.
 * 
 * This system is not the only one modifying Visual component.
 * @author Odys Zhou
 *
 */
public class SysVisual extends EntitySystem {

	ImmutableArray<Entity> entities;
	ComponentMapper<RayPickable> mRaypick;
	ComponentMapper<Visual> mVisual;
	
	SysVisual() {
		mVisual = ComponentMapper.getFor(Visual.class);
		mRaypick = ComponentMapper.getFor(RayPickable.class);
	}

	@Override
	public void update(float deltaTime) {
		for (int i = 0; i < entities.size(); ++i) {
			Entity entity = entities.get(i);
			RayPickable pick = mRaypick.get(entity);
			Visual visual = mVisual.get(entity);
			if (pick != null && visual != null)
				if (pick.selectUp) {
					uselect(visual, 1);
					break; // should only one
				}
				else if (pick.deselectDown) {
					uselect(visual, 0);
					break; // should only one
				}
		}
	}

//	private void selectOff(Visual visual) {
//		 visual.shader.setVisual(Cubic.cmdTurnOn, 0);
//	}
//
//	private void selectOn(Visual visual) {
//		 visual.shader.setVisual(Cubic.cmdTurnOn, 1);
//	}

	/**Set/reset uniform u_select.
	 * @param visual
	 * @param selection
	 */
	private void uselect(Visual visual, float selection) {
		visual.uniforms.put(visual.shader.u_mode, selection);
		visual.needsUpdateUniforms = true;
	}
}
