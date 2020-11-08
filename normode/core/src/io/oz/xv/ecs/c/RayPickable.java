package io.oz.xv.ecs.c;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.collision.BoundingBox;

import io.oz.xv.ecs.s.RayPicker.PickingShape;

/**Basically the Shape of this <a href='https://xoppa.github.io/blog/using-collision-shapes/'>
 * tutorial</a>'s
 * <a href='https://github.com/xoppa/blog/blob/master/tutorials/src/com/xoppa/blog/libgdx/g3d/shapes/step4/ShapeTest.java'>
 * code</a>.
 * 
 * @author Odys Zhou
 */
public class RayPickable implements Component {
	public int id;
	public PickingShape pickingShape;

	public float radius;
	public BoundingBox whd = new BoundingBox();

	public boolean selected = false;
	/** on selected events fired */
	public boolean selectUp = false;
	/** on deselected events fired */
	public boolean deselectDown = false;
}
