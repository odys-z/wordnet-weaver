package io.oz.xv.ecs.s;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;

import io.oz.xv.ecs.c.RayPickable;
import io.oz.xv.ecs.c.Visual;

/**Visual state manager, where {@link Visual} is roughly equivalent of material, but more flexible.
 * 
 * @author Odys Zhou
 *
 */
public class SysVisual extends EntitySystem {

	private ImmutableArray<Entity> entities;
	private ComponentMapper<RayPickable> mRaypick;
	private ComponentMapper<Visual> mVisual;
	
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
					selectOn(visual);
					break; // should only one
				}
				else if (pick.deselectDown) {
					selectOff(visual);
					break; // should only one
				}
		}
	}

	private void selectOff(Visual visual) {
		
	}

	private void selectOn(Visual visual) {
		
	}
}
