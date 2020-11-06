package io.oz.xv.ecs.c;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

/**<p>3D Stage Object</p>
 * Each transformation is acctually happened in {@link ModelInstance#transform}.
 * 
 * @see <a href='https://odys-z.github.io/wordnet-weaver/reference/gdx-modelbatch.html#model-instance-transform'></a>
 *
 * @author Odys Zhou
 */
public class Obj3 implements Component {
	public Vector3 pos;
	public Vector3 scl;
	public ModelInstance modInst;
	/** Plane meshs that always facing screen */
	public ModelInstance orthoFace;
}
