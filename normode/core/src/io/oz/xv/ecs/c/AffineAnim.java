package io.oz.xv.ecs.c;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;

import patch.net.mgsx.gltf.scene3d.animation.XAnimationController;

/**ECS component wrapper of {@link NodeAnimation} - scripts of Affine transformation
 * @author odys-z@github.com
 *
 */
public class AffineAnim extends NodeAnimation implements Component {
	public XAnimationController controllor;
	public boolean playing = true;
}
