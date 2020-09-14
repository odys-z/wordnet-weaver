package io.oz.wnw.ecs.cmp;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

/**3D stage object transformation
 * 
 * @author Odys Zhou
 *
 */
public class Affine implements Component {
	public Vector3 pos;
	public Vector3 scl;
	public Vector3 dir;

	/** Transforms apply to pos, scl at each updating */
	public Vector3 transf[];

	public Affine pos(float x, float y, float z) {
		pos = new Vector3(x, y, z);
		return this;
	}
}
