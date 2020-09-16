package io.oz.xv.utils;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.math.Vector3;

import io.oz.xv.material.XMaterial;
import io.oz.xv.material.bisheng.Inkstone;
import io.oz.xv.math.Geoshape;

public class Xutils {

	/**A {@link ModelPartBuilder} wrapper building cube.
	 * @param cube
	 * @return
	 */
	public static ModelInstance modelInstance(Geoshape cube, Vector3 size, XMaterial... matrl) {
		ModelBuilder builder = new ModelBuilder();
		builder.begin();

		Node node = builder.node();
		node.id = String.format("n-cube");
		node.translation.set(0f, 0f, 0f);
		
		Material mat = matrl != null && matrl.length > 0 ? matrl[0] : Inkstone.pixel("grey");
		MeshPartBuilder mpbuilder = builder.part("cube",
				GL20.GL_TRIANGLES, Usage.Position | Usage.ColorUnpacked | Usage.TextureCoordinates | Usage.Normal,
				mat);
		BoxShapeBuilder.build(mpbuilder, size.x, size.y, size.z);;

		Model model = builder.end();
		model.calculateTransforms();
		return new ModelInstance(model);
	}

}
