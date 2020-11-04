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

	protected ComponentMapper<RayPickable> mPickable;
	protected int currentPcking;
	protected PerspectiveCamera cam;

	public RayPicker(PerspectiveCamera camera) {
		super(Family.all(RayPickable.class).get());
		mPickable = ComponentMapper.getFor(RayPickable.class);

		cam = camera;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		RayPickable pcked = mPickable.get(entity);

		if (currentPcking != pcked.id)
			;
	}

	@Override
	public boolean keyDown(int keycode) { return false; }

	@Override
	public boolean keyUp(int keycode) { return false; }

	@Override
	public boolean keyTyped(char character) { return false; }

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

	@Override
	public boolean mouseMoved(int screenX, int screenY) { return false; }

	@Override
	public boolean scrolled(int amount) { return false; }

}
