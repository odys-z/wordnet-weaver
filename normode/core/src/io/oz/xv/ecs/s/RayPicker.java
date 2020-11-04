package io.oz.xv.ecs.s;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;

import io.oz.xv.ecs.c.RayPickable;

public class RayPicker extends IteratingSystem implements InputProcessor {
	private static int uuid = 0;
	public static int uuId() { return ++uuid; }

	protected PerspectiveCamera cam;
	protected ComponentMapper<RayPickable> mPickable;
	protected int currentPickId;
	protected Entity currentPick;

	public RayPicker(PerspectiveCamera camera) {
		super(Family.all(RayPickable.class).get());
		mPickable = ComponentMapper.getFor(RayPickable.class);

		cam = camera;
		currentPickId = -1;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		if (this.currentPickId < 0) {
			// deselect
			if (currentPick != null) {
				RayPickable pickable = currentPick.getComponent(RayPickable.class);
				pickable.deselectDown = true;
				currentPick = null;
			}
			return;
		}

		RayPickable pickable = mPickable.get(entity);
		if (currentPickId == pickable.id)
			pickable.selectUp = false;  // rising edge
		else { // currentPickId > 0 and != pickable 
			RayPickable currentPickable = currentPick.getComponent(RayPickable.class);
			currentPickable.deselectDown = true; // down edge
			pickable.selectUp = true;  // rising edge
			currentPick = entity;
		}
	}

	@Override
	public boolean keyDown(int keycode) { return false; }

	@Override
	public boolean keyUp(int keycode) { return false; }

	@Override
	public boolean keyTyped(char character) { return false; }

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.pcked = getObject(screenX, screenY);
		return this.pcked >= 0;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) { 
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

	@Override
	public boolean mouseMoved(int screenX, int screenY) { return false; }

	@Override
	public boolean scrolled(int amount) { return false; }

}
