package io.oz.xv.gdxpatch.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BaseShapeBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/** Helper class with static methods to build rectangle plane using {@link MeshPartBuilder}.
 * Rectangle vetices' attribute Nor been set to center position - mesh centre for translation.
 * See {@link MeshPartBuilder#rect(short, short, short, short)}.
 * 
 * @author Odys Zhou
 */
public class QuadShapeBuilder extends BaseShapeBuilder {

	private static Vector3 _v3;

	/** Add a rect plane. Requires GL_POINTS, GL_LINES or GL_TRIANGLES primitive type.
	 * @param builder
	 * @param v00 v00.z = u.v (0.0)
	 * @param v01 v00.z = u.v (0.1)
	 * @param v10 v00.z = u.v (1.0)
	 * @param v11 v00.z = u.v (1.1)
	 */
	public static void build (MeshPartBuilder builder, VertexInfo v00, VertexInfo v01, VertexInfo v10, VertexInfo v11) {
		builder.ensureVertices(4);
		final short i000 = builder.vertex(v00);
		final short i100 = builder.vertex(v10);
		final short i010 = builder.vertex(v01);
		final short i110 = builder.vertex(v11);

		final int primitiveType = builder.getPrimitiveType();
		if (primitiveType == GL20.GL_LINES) {
			builder.ensureIndices(4);
			builder.rect(i000, i100, i110, i010);
			builder.index(i000, i010, i110, i100);
		} else if (primitiveType == GL20.GL_POINTS) {
			builder.ensureRectangleIndices(1);
			builder.rect(i000, i100, i110, i010);
		} else { // GL20.GL_TRIANGLES
			builder.ensureRectangleIndices(1);
			builder.rect(i000, i100, i110, i010);
		}
	}

	/** Add a rectangle. Requires GL_POINTS, GL_LINES or GL_TRIANGLES primitive type.<br>
	 * Set a_col = Color(a_memory, 0, 0, 0), normal = pos.
	 * @param mpbuilder
	 * @param a_col 
	 * @param v00 v00.z = u.v (0.0)
	 * @param v01 v00.z = u.v (0.1)
	 * @param v10 v00.z = u.v (1.0)
	 * @param v11 v00.z = u.v (1.1)
	 */
	public static void build(MeshPartBuilder mpbuilder, Vector3 pos, Color a_col, Vector3 v00, Vector3 v01, Vector3 v10, Vector3 v11) {

		build(mpbuilder,
			vertTmp1.set(v00, pos, a_col, new Vector2(0, 0)), // uv = vertex-idx, xz-rotation (ccw radian)
			vertTmp2.set(v01, pos, a_col, new Vector2(1, 0)),
			vertTmp3.set(v10, pos, a_col, new Vector2(2, 0)),
			vertTmp4.set(v11, pos, a_col, new Vector2(3, 0)));
	}

	/** Add a plane given the matrix. Requires GL_POINTS, GL_LINES or GL_TRIANGLES primitive type.
	 * @param mpbuilder
	 * @param transform
	 * @param a_memory 
	 */
	public static void build (MeshPartBuilder mpbuilder, Matrix4 transform, Color colr) {
		build(mpbuilder, transform.getTranslation(_v3), colr,
			obtainV3().set(-0.5f, -0.5f, -0.0f).mul(transform), obtainV3().set(-0.5f, 0.5f, -0.1f).mul(transform),
			obtainV3().set( 0.5f, -0.5f, -1.0f).mul(transform), obtainV3().set( 0.5f, 0.5f, -1.1f).mul(transform));
		freeAll();
	}

	/** Add a plane with the specified dimensions. Requires GL_POINTS, GL_LINES or GL_TRIANGLES primitive type.
	 * @param builder
	 * @param colr 
	 * @param width
	 * @param height
	 */
	public static void build (MeshPartBuilder builder, Color colr, float width, float height) {
		build(builder, _v3.set(0, 0, 0), colr, width, height);
	}

	public static void build(MeshPartBuilder mpbuilder, Vector3 pos, Color colr, float width, float height) {
		final float hw = width * 0.5f;
		final float hh = height * 0.5f;
		build(mpbuilder, pos, colr,
			obtainV3().set(-hw, -hh, 0.0f), obtainV3().set(-hw, hh, 0.1f), // vec3.z = u.v
			obtainV3().set(hw, -hh, 1.0f), obtainV3().set(hw, hh, 1.1f));
		freeAll();
	}
}
