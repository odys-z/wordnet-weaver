package io.oz.xv.ecs.c;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import io.oz.wnw.ecs.cmp.ds.AffineTrans;

/**3D stage object transformation
 * @deprecated
 * @author Odys Zhou
 *
 */
public class Affines implements Component {
	public boolean dirty = true;

	public Vector3 pos;
	public Vector3 scl;
	public Vector3 dir;

	/** Transforms apply to pos, scl at each updating */
	public Array<AffineTrans> transforms;

}
