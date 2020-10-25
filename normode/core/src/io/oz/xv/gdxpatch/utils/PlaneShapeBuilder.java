package io.oz.xv.gdxpatch.utils;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BaseShapeBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class PlaneShapeBuilder extends BaseShapeBuilder {

	/** Add a box. Requires GL_POINTS, GL_LINES or GL_TRIANGLES primitive type. */
	public static void build (MeshPartBuilder builder, VertexInfo corner000, VertexInfo corner010, VertexInfo corner100, VertexInfo corner110) {
		builder.ensureVertices(4);
		final short i000 = builder.vertex(corner000);
		final short i100 = builder.vertex(corner100);
		final short i010 = builder.vertex(corner010);
		final short i110 = builder.vertex(corner110);

		final int primitiveType = builder.getPrimitiveType();
		if (primitiveType == GL20.GL_LINES) {
			builder.ensureIndices(4);
			builder.rect(i000, i100, i110, i010);
			builder.index(i000, i010, i110, i100);
		} else if (primitiveType == GL20.GL_POINTS) {
			builder.ensureRectangleIndices(2);
			builder.rect(i000, i100, i110, i010);
		} else { // GL20.GL_TRIANGLES
			builder.ensureRectangleIndices(1);
			builder.rect(i000, i010, i110, i100);
		}
	}

	/** Add a rectangle. Requires GL_POINTS, GL_LINES or GL_TRIANGLES primitive type. */
	public static void build (MeshPartBuilder builder, Vector3 corner000, Vector3 corner010, Vector3 corner100, Vector3 corner110) {
		if ((builder.getAttributes().getMask() & (Usage.Normal | Usage.BiNormal | Usage.Tangent | Usage.TextureCoordinates)) == 0) {
			build(builder, vertTmp1.set(corner000, null, null, null), vertTmp2.set(corner010, null, null, null),
				vertTmp3.set(corner100, null, null, null), vertTmp4.set(corner110, null, null, null));
		} else {
			builder.ensureVertices(24);
			builder.ensureRectangleIndices(6);
			Vector3 nor = tmpV1.set(0f, 0f, 1f);
			builder.rect(corner000, corner010, corner110, corner100, nor);
		}
	}

	/** Add a box given the matrix. Requires GL_POINTS, GL_LINES or GL_TRIANGLES primitive type. */
	public static void build (MeshPartBuilder builder, Matrix4 transform) {
		build(builder, obtainV3().set(-0.5f, -0.5f, -0f).mul(transform), obtainV3().set(-0.5f, 0.5f, -0.f).mul(transform),
			obtainV3().set(0.5f, -0.5f, -0.f).mul(transform), obtainV3().set(0.5f, 0.5f, -0.f).mul(transform));
		freeAll();
	}

	/** Add a box with the specified dimensions. Requires GL_POINTS, GL_LINES or GL_TRIANGLES primitive type. */
	public static void build (MeshPartBuilder builder, float width, float height, float depth) {

		final float hw = width * 0.5f;
		final float hh = height * 0.5f;
		final float x0 =  -hw, y0 = -hh, x1 = hw, y1 = hh;
		build(builder, //
			obtainV3().set(x0, y0, 0), obtainV3().set(x0, y1, 0), obtainV3().set(x1, y0, 0), obtainV3().set(x1, y1, 0));
		freeAll();
	}
}
