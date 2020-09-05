package io.oz.wnw.norm.A;

import java.util.Map;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.ConeShapeBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.SphereShapeBuilder;

import io.oz.jwi.Synset;
import io.oz.jwi.SynsetInf;
import io.oz.xv.glsl.Glsl.ShaderFlag;
import io.oz.xv.glsl.WShader;
import io.oz.xv.material.XMaterial;

/**Scene A's world / objects manager.
 * 
 * @author Odys Zhou
 */
public class StageA {

	public Map<String, Synset> synsets;

	public StageA(PooledEngine ecs) {
	}

	public void init(ViewA1 viewA1, PooledEngine ecs) {
	}

	ModelInstance loadSnyset(SynsetInf sinf) {
		WShader sh = new WShader(ShaderFlag.sdfont);
		Material simpleMat = new XMaterial("smat", sh);

		ModelBuilder builder = new ModelBuilder();
		Node node;

		builder.begin();
		node = builder.node();
		node.id = "s1";
		node.translation.set(0, 0f, 0f);
		BoxShapeBuilder.build(builder.part("s1", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, simpleMat), 5, 5, 5);

		Model model = builder.end();
		return new ModelInstance(model);
	}

	ModelInstance sphereCones(ModelBatch modelBatch) {
		WShader sh2 = new WShader(ShaderFlag.test);
		WShader sh1 = new WShader(ShaderFlag.test);
		Material redMaterial = new Material("RedMaterial", ColorAttribute.createDiffuse(Color.RED));
		Material material1 = new XMaterial("TestMaterial1", sh1);
		Material material2 = new XMaterial("TestMaterial2", sh2);

		ModelBuilder builder = new ModelBuilder();
		Node node;

		builder.begin();
		node = builder.node();
		node.id = "cone1";
		node.translation.set(-10, 0f, 0f);
		ConeShapeBuilder.build(builder.part("cone1_", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, material1), 5, 5, 5, 20);

		node = builder.node();
		node.id = "redSphere";
		node.translation.set(0f, 6f, 0f);
		SphereShapeBuilder.build(builder.part("redSphere", GL20.GL_TRIANGLES, Usage.Position, redMaterial), 5, 5, 5, 20, 20);

		node = builder.node();
		node.id = "cone2";
		node.translation.set(10, 0f, 0f);
		ConeShapeBuilder.build(builder.part("cone2_", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, material2), 5, 5, 5, 20);

		Model model = builder.end();
		return new ModelInstance(model);
	}

	ModelInstance cube(ModelBatch modelBatch) {
		WShader sh = new WShader(ShaderFlag.simple);
		Material simpleMat = new XMaterial("cubeMat", sh);

		ModelBuilder builder = new ModelBuilder();
		Node node;

		builder.begin();
		node = builder.node();
		node.id = "cone1";
		node.translation.set(0, 0f, 0f);
		BoxShapeBuilder.build(builder.part("cone1", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, simpleMat), 5, 5, 5);

		Model model = builder.end();
		return new ModelInstance(model);
	}
}
