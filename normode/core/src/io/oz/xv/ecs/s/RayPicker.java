package io.oz.xv.ecs.s;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import io.oz.xv.ecs.c.RayPickable;

public class RayPicker extends IteratingSystem {
	private static int uuid = 0;
	public static int uuId() { return ++uuid; }

	private ComponentMapper<RayPickable> mPickable;
	private int currentPcking;

	public RayPicker() {
		super(Family.all(RayPickable.class).get());
		mPickable = ComponentMapper.getFor(RayPickable.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		RayPickable pcked = mPickable.get(entity);

		if (currentPcking != pcked.id)
			;
	}

}
