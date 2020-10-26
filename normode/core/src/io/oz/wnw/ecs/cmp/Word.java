package io.oz.wnw.ecs.cmp;

import java.util.ArrayList;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

import io.oz.jwi.SynsetInf;

public class Word implements Component {

	public String word;

	public ArrayList<SynsetInf> children;

	public Color color;

}
