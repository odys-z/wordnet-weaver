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

import io.oz.xv.XWorld;
import io.oz.xv.ecs.c.Obj3;
import io.oz.xv.ecs.c.RayPickable;

public class RayPicker extends EntitySystem implements InputProcessor {
	public enum PickingShape {
		xzRect, xyRect, xyEllipse, box, sphere, xzHexaprism, ellipsoid
	}

	private static int uuid = 0;
	public static int uuId() { return ++uuid; }

	protected PerspectiveCamera cam;
	private Family family;
	private ImmutableArray<Entity> entities;

	protected ComponentMapper<RayPickable> mPickable;

	/** picked and handling by ecs */
	protected RayPickable currentPicked;
	RayPickable lastPickable;
	private boolean pickingFired;

	/** picked by user, not handled by ecs */
	private RayPickable picking;

	public RayPicker(PerspectiveCamera camera) {
		super();
		this.family = Family.all(RayPickable.class).get();
		mPickable = ComponentMapper.getFor(RayPickable.class);

		cam = camera;

		picking = null;
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
		// 0. clear events
		if (lastPickable != null) {
			lastPickable.deselectDown = false;
			lastPickable = null;
		}

		// side effect: filter out currentPicked == null && picking == null
		if (!this.pickingFired) return;
		this.pickingFired = false;
		
		// 1. select new
		if (picking != null && currentPicked == null) {
			currentPicked = picking;
			currentPicked.selectUp = true;
			if (XWorld.log(5))
				System.out.println(String.format("[5] select new: %d", currentPicked.uuid));
		}
		// 2. unselect (click again)
		else if (picking != null && picking.uuid == currentPicked.uuid) {
			if (XWorld.log(5))
				System.out.println(String.format("[5] unselect old: %d", currentPicked.uuid));

			currentPicked.selectUp = false;
			currentPicked.deselectDown = true;
			lastPickable = currentPicked;
			currentPicked = null;
		}
		// 3. select another
		else if (picking != null && currentPicked != null){
			if (XWorld.log(5))
				System.out.println(String.format("[5] select form %d to %d", currentPicked.uuid, picking.uuid));

			currentPicked.selectUp = false;
			currentPicked.deselectDown = true;
			lastPickable = currentPicked;
			currentPicked = picking;
			currentPicked.selectUp = true;
		}
		// 4. select none (click at blank)
		else if (picking == null && currentPicked != null){
			if (XWorld.log(5))
				System.out.println(String.format("[5] unselect as blank: %d", currentPicked.uuid));
			currentPicked.deselectDown = true;
			lastPickable = currentPicked;
			currentPicked = null;
		}
		else if (XWorld.log(5))
			System.out.println("[5]");

		picking = null;
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
		picking = getObject(screenX, screenY);
		this.pickingFired = true;
		return this.picking != null;
	}

	/**Get Pickable.id with ray picking.
	 * @param screenX
	 * @param screenY
	 * @return RayPickalble.id
	 */
	protected RayPickable getObject(int screenX, int screenY) {
		if (entities == null || entities.size() == 0) return null;
		Ray ray = cam.getPickRay(screenX, screenY);

		RayPickable result = null;
		float distance = -1;
		for (Entity e : entities) {
			Obj3 obj3 = e.getComponent(Obj3.class);
			if (obj3 == null) continue;

			RayPickable pickable = e.getComponent(RayPickable.class);
			if (pickable == null) continue;

			Matrix4 t = obj3.modInst.transform;
			float dist2 = intersects(ray, t, pickable, obj3);
			if (dist2 >= 0 && (distance < 0f || dist2 < distance)) { 
				result = pickable;
				distance = dist2;
			}
		}

		return result;
	}

	static private Vector3 _v3a = new Vector3();
	static private Vector3 _v3b = new Vector3();

	static private float intersects(Ray ray, Matrix4 trans, RayPickable p, Obj3 obj3) {
		trans.getTranslation(_v3a); // m4.col[3]
		final float len = ray.direction.dot(_v3a.x - ray.origin.x, _v3a.y - ray.origin.y, _v3a.z - ray.origin.z);
		if (len < 0f)
			return -1f;

		// TODO extending other shapes
		if (p.pickingShape == PickingShape.sphere) {
			float dist2 = _v3a.dst2(ray.origin.x + ray.direction.x * len,
					ray.origin.y + ray.direction.y * len, ray.origin.z + ray.direction.z * len);
			return (dist2 <= p.radius * p.radius) ? dist2 : -1f;
		}
		else if (p.pickingShape == PickingShape.box) {
			if (Intersector.intersectRayBoundsFast(ray, _v3a, p.whd.getDimensions(_v3b))) {
                return _v3a.dst2(ray.origin.x + ray.direction.x * len, ray.origin.y + ray.direction.y * len, ray.origin.z + ray.direction.z * len);
            }
		}
		return -1f;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

	@Override
	public boolean mouseMoved(int screenX, int screenY) { return false; }

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}

}
