package io.oz.xv.ecs.s;

import io.oz.xv.test.WGameTest;

public class TestBasicTween extends WGameTest {

	@Override
	public void create () {
		super.create();
		setScreen(new TestBasicTweenView(this));
	}
}
