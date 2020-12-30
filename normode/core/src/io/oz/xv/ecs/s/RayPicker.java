package io.oz.xv.ecs.s;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import io.oz.xv.ecs.c.Obj3;
import io.oz.xv.ecs.c.RayPickable;

public class RayPicker extends EntitySystem implements InputProcessor {
	public enum PickingShape {
		xzRect, xyRect, ellipse, box, sphere, xzHexaprism, ellipsoid
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
	
	/**<p>Set {@link #currentPicked} &amp; {@link #lastPickable}.</p>
	 * As touching / mouse listening changed {@link #pickingId}, this is where the
	 * picking events been raised and selected entity changed.
	 * @see com.badlogic.ashley.core.EntitySystem#update(float)
	 */
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
						currentPicked.selected = false;
						lastPickable = currentPicked;
						
						pick.selected = true;
						currentPicked = pick;
						currentPicked.selectUp = true;
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

	/**Find Pickable.id via ray picking.
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		pickingId = getObject(screenX, screenY);
		return this.pickingId >= 0;
	}

	/**Get Pickable.id with ray picking.
	 * @param screenX
	 * @param screenY
	 * @return
	 */
	protected int getObject(int screenX, int screenY) {
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
			float dist2 = intersects(ray, t, pickable, obj3);
			if (dist2 >= 0 && (distance < 0f || dist2 < distance)) { 
				result = pickable.id;
				distance = dist2;
			}
		}

		return result;
	}

	static private Vector3 _v3 = new Vector3();
	static private Vector3 _v3_1 = new Vector3();
//	static private Vector3 _v3_2 = new Vector3();

	static private float intersects(Ray ray, Matrix4 trans, RayPickable p, Obj3 obj3) {
		trans.getTranslation(_v3); // m4.col[3]
		// need this? _v3.add(obj3.pos);
		final float len = ray.direction.dot(_v3.x - ray.origin.x, _v3.y - ray.origin.y, _v3.z - ray.origin.z);
		if (len < 0f)
			return -1f;

		// TODO extending other shapes
		if (p.pickingShape == PickingShape.sphere) {
			float dist2 = _v3.dst2(ray.origin.x + ray.direction.x * len,
					ray.origin.y + ray.direction.y * len, ray.origin.z + ray.direction.z * len);
			return (dist2 <= p.radius * p.radius) ? dist2 : -1f;
		}
		else if (p.pickingShape == PickingShape.box) {
			if (Intersector.intersectRayBoundsFast(ray, _v3, p.whd.getDimensions(_v3_1))) {
                return _v3.dst2(ray.origin.x + ray.direction.x * len, ray.origin.y + ray.direction.y * len, ray.origin.z + ray.direction.z * len);
            }
		}
		return -1f;
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
