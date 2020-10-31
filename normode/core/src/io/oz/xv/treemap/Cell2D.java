package io.oz.xv.treemap;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Cell2D {

	private Vector3 cellIx;
	/** grid distance scale */
	private float scl;

	private Quaternion q;
	public Quaternion rotate() { return q; }

	/** position offset from grid point */
	private Vector3 offset;
	/** position offset from grid point */
	public Vector3 offset() { return offset; }

	/**
	 * @param cellIx position index
	 */
	public Cell2D(Space2dContext cxt, int x, int y, int z) {
		this.cellIx = new Vector3(x, y, z);
		this.scl = 1f;
		q = new Quaternion();
		offset = new Vector3();
	}

	public Vector3 pos() {
		return new Vector3(cellIx).scl(scl);
	}

	public Vector3 scale() { return new Vector3(scl, scl, scl); }

	/**
	 * 
	 * @param yaw the rotation around the y axis in degrees
	 * @param pitch the rotation around the x axis in degrees
	 * @param roll the rotation around the z axis degrees
	 * @return
	 */
	public Cell2D rotate (float yaw, float pitch, float roll) {
		q.setEulerAngles(yaw, pitch, roll);
		return this;
	}
}
