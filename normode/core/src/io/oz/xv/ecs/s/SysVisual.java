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

	RayPicker picker;
	
	/**
	 * @param rayPicker FIXME Should been merged with RayPicker?
	 */
	public SysVisual(RayPicker rayPicker) {
		mVisual = ComponentMapper.getFor(Visual.class);
		mRaypick = ComponentMapper.getFor(RayPickable.class);

		picker = rayPicker;
	}

	@Override
	public void update(float deltaTime) {
		if (picker != null) {
			if (picker.currentPicked != null && picker.currentPicked.selectUp) {
				Visual v = mVisual.get(picker.currentPicked.entity);
				if (v != null)
					umode(v, 1);
			}

			if (picker.lastPickable != null && picker.lastPickable.deselectDown) {
				Visual v = mVisual.get(picker.lastPickable.entity);
				if (v != null)
					umode(v, 0);
			}
		}
	}

	/**Set/reset uniform u_select.
	 * @param visual
	 * @param selection
	 */
	private void umode(Visual visual, float selection) {
		visual.uniforms.put(visual.shader.u_mode, selection);
		visual.needsUpdateUniforms = true;
	}
}
