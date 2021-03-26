package io.oz.xv.ecs.s;

import com.badlogic.ashley.core.Component;

import aurelienribon.tweenengine.TweenManager;

public class Unitween implements Component {

	/** Needs auto update variables like u_time etc. */
	public boolean autoUpdate;

	/** uniforms need to be update */
	public boolean dirty;

	public TweenManager manager;

	/** bound name of uniform float u_time */
	public int u_time;

	/** bound name of uniform float u_t */
	public int u_t;

	/**Uniform u_t, the tween value driven by {@link aurelienribon.tweenengine.Tween Tween},
	 * Updated by {@link SysUnitween}. */
	public float t = 0;
}
