package io.oz.xv.ecs.s;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

/**ECS component wrapper of {@link NodeAnimation} - scripts of Affine transformation
 * TODO renamed as Affine3
 * @author odys-z@github.com
 *
 */
public class AffineAnim extends NodeAnimation implements Component {

	// public boolean dirty = true;
	public AnimationController controllor;

}
