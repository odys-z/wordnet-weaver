package io.oz.xv.glsl.shaders;

import io.oz.xv.glsl.Glsl.ShaderFlag;
import io.oz.xv.glsl.WShader;

public class Phong extends WShader {

	public Phong( ) {
		super(ShaderFlag.phong);
		// https://github.com/nomemory/aleph-formatter
		vs = "";
		fs = "";
	}

}
