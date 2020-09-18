package io.oz.wnw.ecs.cmp.ds;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class AffineTrans {
	public AffineType a;

	public Quaternion rotation;
	public AffineTrans(AffineType type) {
		a = type;
	}

	public AffineTrans rotate(Quaternion q) {
		this.rotation = q.cpy();
		return this;
	}

	/**
	 * @param yaw the rotation around the y axis in degrees
	 * @param pitch the rotation around the x axis in degrees
	 * @param roll the rotation around the z axis degrees
	 * @return
	 */
	public AffineTrans rotate(float yaw, float pitch, float roll ) {
		this.rotation = new Quaternion().setEulerAngles(yaw, pitch, roll);
		return this;
	}

	public Vector3 translate;
	public AffineTrans translate(Vector3 t) {
		this.translate = t.cpy();
		return this;
	}

	public AffineTrans translate(float x, float y, float z) {
		this.translate = new Vector3(x, y, z);
		return this;
	}

	public Vector3 scale;
	public AffineTrans scale(Vector3 s) {
		this.scale = s.cpy();
		return this;
	}

	public AffineTrans scale(float x, float y, float z) {
		this.scale = new Vector3(x, y, z);
		return this;
	}

	public AffineTrans scale(float scl) {
		this.scale = new Vector3(scl, scl, scl);
		return this;
	}


}
