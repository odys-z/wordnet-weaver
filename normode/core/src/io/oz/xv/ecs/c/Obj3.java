package io.oz.xv.ecs.c;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**<p>3D Stage Object</p>
 * <p>In this component, the transformation is decomposed into pos, scl, rot,then tweened
 * at each loop and re-composed into transform matrix of model instance.</p>
 * 
 * Each transformation is actually happened in {@link ModelInstance#transform}.
 * 
 * @see <a href='https://odys-z.github.io/wordnet-weaver/reference/gdx-modelbatch.html#model-instance-transform'></a>
 *
 * @author Odys Zhou
 */
public class Obj3 implements Component {
	/** needs to be decomposed and re-composed */
	public boolean dirty = true;
	public Vector3 pos = new Vector3();
	public Vector3 scl = new Vector3(1f, 1f, 1f);
	public Quaternion rot = new Quaternion();
	
	public ModelInstance modInst;
	/** Plane meshs that always facing screen */
	public ModelInstance orthoFace;
}
