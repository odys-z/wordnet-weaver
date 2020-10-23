package io.oz.xv.material;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;

import io.oz.xv.glsl.Glsl.ShaderFlag;
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
	private ShaderFlag acceptShader;

	@Override
	public Material copy() {
		return new XMaterial(this);
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof XMaterial) && ((other == this) || ((((XMaterial)other).id.equals(id)) && super.equals(other)));
	}

	protected WShader shader;

	/**Create XMaterial with shader xshader.
	 * @param matId material id
	 * @param xshader
	 * @param attr
	 */
	public XMaterial(String matId, WShader xshader, Attribute... attr) {
		super(matId, attr);
		this.shader = xshader;
		acceptShader = xshader.flag();
	}

	public XMaterial(XMaterial from) {
		super(from.id, from);
		this.shader = from.shader();
	}

	public WShader shader() {
		return this.shader;
	}

	/**Can this material been rendered by wShader?
	 * See class remarks for details.
	 * @param wShader
	 * @return
	 */
	public boolean acceptShader(WShader wShader) {
		return acceptShader == wShader.flag();
	}

}
