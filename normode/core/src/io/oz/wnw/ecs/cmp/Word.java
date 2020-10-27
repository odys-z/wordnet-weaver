package io.oz.wnw.ecs.cmp;

import java.util.HashMap;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

import io.oz.jwi.WMemory;

public class Word implements Component {

	public String word;

	public HashMap<String,WMemory> children;

	public Color color;

}
