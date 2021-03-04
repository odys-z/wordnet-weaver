package io.oz.xv.glsl.shader;

import io.oz.xv.test.WGameTest;

public class TestPlaneStar extends WGameTest {

	@Override
	public void create () {
		super.create();
		setScreen(new TestPlaneStarView(this));
	}
}
