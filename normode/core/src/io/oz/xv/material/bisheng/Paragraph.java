package io.oz.xv.material.bisheng;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import io.oz.wnw.norm.Assets;
import io.oz.xv.gdxpatch.g3d.XModelInstance;
import io.oz.xv.material.XMaterial;
import io.oz.xv.material.bisheng.GlyphLib.FontData;
import io.oz.xv.material.bisheng.GlyphLib.Glyph;

/** String mesh building context
 * @author Odys Zhou
 *
 */
public class Paragraph {
	private String str;
	private Color color;
	/** paper mesh material */
	private XMaterial inkMat;

	/**xy origin for glyph mesh */
	private float[] xy;// 
	private float paperScl = 1f;
	private float left = -200f;

	/**Vertex infomation buffer.<br. 
	 * vertices of plane: <pre>
	 3 - 2
	 |   |
	 0 - 1</pre>
	 * */
	private static VertexInfo[] vis;
	
	public Paragraph(String str, Color defultColor, XMaterial glyphMaterial) {
		this.str = str;
		this.color = defultColor;
		this.inkMat = glyphMaterial;
	}
	
	public Paragraph scale3(float paperScale) {
		this.paperScl = paperScale;
		return this;
	}

	public XModelInstance buildMesh(FontData fontData) {
		// init context buffer
		vis = new VertexInfo[4];
		for (int i = 0; i < 4; i++)
			vis[i] = new VertexInfo();
		// 0, 1: last x, y;
		// 2, 3: current (w, h) + offset
		xy = new float[] {0, 0, 0, 0};

		// TODO optimize builder usage
		ModelBuilder builder = new ModelBuilder();
		builder.begin();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			vis = setVertInfo(ch, vis, fontData);
			createMesh(builder, vis, i);
		}
		Model model = builder.end();
		model.calculateTransforms();
		return new XModelInstance(model);
	}

	/**<p>Create a rect node in builder. Call builder.end() to get the final results.<br>
	 * Primitive Type: * L20.GL_TRIANGLES,<br>
	 * Attributes: Usage.Position | Usage.ColorUnpacked | Usage.TextureCoordinates | Usage.Normal</p>
	 * See {@link ModelBuilder#part(String, int, long, Material)
	 * @param builder model builder already called begin().
	 * @param vi4 vertices information of plane<pre>
	 3 - 2
	 |   |
	 0 - 1</pre>
	 * @param chMaterial 
	 */
	private void createMesh(ModelBuilder builder, VertexInfo[] vi4, int chx) {
		xy[0] += 0f;
		// TODO wrap, kerning...
		char ch = str.charAt(chx);

		Node node = builder.node();
		node.id = String.format("%s %s", chx, ch);
		node.scale.scl(paperScl);
		node.translation.set(left + xy[0], 0f, 0f);
		xy[0] += xy[2];

		MeshPartBuilder mpbuilder = builder.part("" + ch,
				GL20.GL_TRIANGLES, Usage.Position | Usage.ColorUnpacked | Usage.TextureCoordinates | Usage.Normal,
				inkMat);
		mpbuilder.rect(vi4[0], vi4[1], vi4[2], vi4[3]);
	}

	/**Set vertex info, pivort at lowerlert + offset.
	 * @param ch
	 * @param vi4
	 * @param color
	 * @param data
	 * @return
	 */
	private VertexInfo[] setVertInfo(char ch, VertexInfo[] vi4, FontData data) {
		Glyph glyph = data.getGlyph(ch);
		data.regions[glyph.page].getTexture().bind(Assets.texGlyph);

		/* font sample
		 * 'D' id=68
		 * x=318 y=127 width=34 height=38 xoffset=0 yoffset=7 xadvance=30 page=0 chnl=0
		 * 'r' id=114   
		 * x=414 y=165 width=23 height=31 xoffset=0 yoffset=14 advance=17 page=0 chnl=0 
		 */
		int x = -glyph.xoffset;
		// y = 0 instead of -yoffset (font start at upper left, offset is to bottom)
		int y = 0;
		final float u = glyph.u, u2 = glyph.u2, v = glyph.v, v2 = glyph.v2;
		final float x2 = x + glyph.width, y2 = y + glyph.height;

		vi4[0].setPos(x , y , 0).setCol(color).setUV(u , v );
		vi4[1].setPos(x , y2, 0).setCol(Color.RED).setUV(u , v2);
		vi4[2].setPos(x2, y2, 0).setCol(Color.GREEN).setUV(u2, v2);
		vi4[3].setPos(x2, y , 0).setCol(Color.BLUE).setUV(u2, v );

		xy[2] = glyph.width + glyph.xoffset;
		xy[3] = glyph.height;
		return vi4;
	}
}

