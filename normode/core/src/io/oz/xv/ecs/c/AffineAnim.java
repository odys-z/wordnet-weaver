package io.oz.xv.ecs.c;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

import net.mgsx.gltf.scene3d.animation.AnimationControllerHack;
import patch.net.mgsx.gltf.scene3d.animation.XAnimationController;

/**ECS component wrapper of {@link NodeAnimation} - scripts of Affine transformation
 * TODO renamed as Affine3
 * @author odys-z@github.com
 *
 */
public class AffineAnim extends NodeAnimation implements Component {

	// public boolean dirty = true;
	public XAnimationController controllor;

}
