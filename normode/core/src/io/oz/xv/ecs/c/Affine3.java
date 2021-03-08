package io.oz.xv.ecs.c;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**3D stage object transformation
 * 
 * @author Odys Zhou
 *
 */
public class Affine3 implements Component {
	public boolean dirty = true;

	/** current (last) pos */
	public Vector3 pos;
	/** current (last) scale */
	public Vector3 scl;
	/** current (last) rotation */
	public Quaternion qut;

	/** Transforms apply to pos, scl at each updating */
	public NodeAnimation transforms;

}
