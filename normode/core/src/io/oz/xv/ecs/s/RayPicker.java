package io.oz.xv.ecs.s;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.collision.Ray;

import io.oz.xv.ecs.c.Obj3;
import io.oz.xv.ecs.c.RayPickable;

public class RayPicker extends EntitySystem implements InputProcessor {
	public enum PickingShape {

	}

	private static int uuid = 0;
	public static int uuId() { return ++uuid; }

	protected PerspectiveCamera cam;
	private Family family;
	private ImmutableArray<Entity> entities;

	protected ComponentMapper<RayPickable> mPickable;

	/** picked by user, not handled by ecs */
	protected int pickingId;

	/** picked and handling by ecs */
	protected RayPickable currentPicked;
	private RayPickable lastPickable;

	public RayPicker(PerspectiveCamera camera) {
		super();
		this.family = Family.all(RayPickable.class).get();
		mPickable = ComponentMapper.getFor(RayPickable.class);

		cam = camera;
		pickingId = -1;
	}

	@Override
	public void addedToEngine (Engine engine) {
		entities = engine.getEntitiesFor(family);
	}

	@Override
	public void removedFromEngine (Engine engine) {
		entities = null;
	}
	
	@Override
	public void update(float deltaTime) {
		// 1. clear events
		if (lastPickable != null) {
			lastPickable.deselectDown = false;
			lastPickable = null;
		}
		if (currentPicked != null) {
			currentPicked.selectUp = false;
			
			// deselect
			if (pickingId < 0) {
				currentPicked.deselectDown = true;
				lastPickable = currentPicked;
				currentPicked = null;
			}
			// change selection (pickingId >= 0)
			else if (pickingId == currentPicked.id) {
				currentPicked.deselectDown = true;
				for (Entity e : entities) {
					RayPickable pick = e.getComponent(RayPickable.class);
					if (pickingId == pick.id) {
						lastPickable = currentPicked;
						currentPicked = pick;
						pickingId = -1;
						break;
					}
				}
			}
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
		pickingId = getObject(screenX, screenY);
		return this.pickingId >= 0;
	}

	private int getObject(int screenX, int screenY) {
		if (entities.size() == 0) return -1;
		Ray ray = cam.getPickRay(screenX, screenY);

		int result = -1;
		float distance = -1;
		for (Entity e : entities) {
			Obj3 obj3 = e.getComponent(Obj3.class);
			if (obj3 == null) continue;

			RayPickable pickable = e.getComponent(RayPickable.class);
			if (pickable == null) continue;

			Matrix4 t = obj3.modInst.transform;
			float dist2 = intersects(ray, t, pickable);
			if (dist2 >= 0 && (distance < 0f || dist2 < distance)) { 
				result = pickable.id;
				distance = dist2;
			}
		}

		return result;
	}

	private float intersects(Ray ray, Matrix4 t, RayPickable p) {
		return -1;
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
