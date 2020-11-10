package io.oz.xv.material;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;

import io.oz.xv.ecs.c.Visual;
import io.oz.xv.glsl.WShader;

/**<p>Design Memo:</p>
 * 1. Material has a default shader. <br>
 * 2. ModelBatch will try to reuse shader<br>
 * - call {@link WShader#canRender(com.badlogic.gdx.graphics.g3d.Renderable) WShder.canRender()},
 * which in turn call {@link #acceptShader(WShader)}
 *    
 * @author Odys Zhou
 *
 */
public class XMaterial extends Material {

	@Override
	public Material copy() {
		return new XMaterial(this);
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof XMaterial) && ((other == this) || ((((XMaterial)other).id.equals(id)) && super.equals(other)));
	}

	// protected WShader shader;

	/** XMaterial new is actually a glue layer of ecs and gdx material */
	private Visual visual;

	/**Create XMaterial with shader xshader.
	 * @param matId material id
	 * @param xshader
	 * @param attr
	 */
	public XMaterial(String matId, Attribute... attr) {
		super(matId, attr);
	}

	public XMaterial(XMaterial from) {
		super(from.id, from);
		this.visual = clone(from.visual);
	}

	public XMaterial visual(Visual v) {
		this.visual = v;
		return this;
	}

	public WShader shader() {
		return this.visual.shader;
	}

	/**Can this material been rendered by wShader?
	 * See class remarks for details.
	 * @param wShader
	 * @return
	 */
	public boolean acceptShader(WShader wShader) {
		return visual.acceptShader == wShader.flag();
	}

	private Visual clone(Visual from) {
		visual = new Visual();
		visual.uniforms = from.uniforms; // really?
		visual.acceptShader = from.acceptShader;
		return visual;
	}
}
