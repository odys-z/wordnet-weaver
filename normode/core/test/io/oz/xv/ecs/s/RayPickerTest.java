package io.oz.xv.ecs.s;

import io.oz.xv.test.WGameTest;

public class RayPickerTest extends WGameTest {

	@Override
	public void create () {
		super.create();
		setScreen(new RayPickingTestView(this));
	}
}
