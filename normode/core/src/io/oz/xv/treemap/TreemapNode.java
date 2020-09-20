package io.oz.xv.treemap;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class TreemapNode {

	private Vector3 cellIx;
	private float scl;
	private Quaternion q;
	private Vector3 offset;

	/**
	 * @param cellIx position index
	 */
	public TreemapNode(TreeContext cxt, int x, int y, int z) {
		this.cellIx = new Vector3(x, y, z);
		this.scl = cxt.scale;
		q = new Quaternion();
		offset = new Vector3();
	}

	public Vector3 pos() {
		return new Vector3(cellIx).scl(scl);
	}

	public Vector3 scale() { return new Vector3(scl, scl, scl); }

	public Quaternion rotate() { return q; }
	public Vector3 offset() { return offset; }
}
