package io.oz.xv.treemap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import io.oz.wnw.ecs.cmp.Affines;
import io.oz.wnw.ecs.cmp.ds.AffineTrans;
import io.oz.wnw.ecs.cmp.ds.AffineType;

public class TreeContext {
	private static final Color color = new Color(1f, 1f, 0f, 1f);

	private static final int maxLevel = 3;

	public int level;

	private int[] count = new int[maxLevel];

	public Color color(String word) {
		return color;
	}

	public void setAffine(Affines aff, String word) {
		int c = count[level]++;
		
		aff.pos = new Vector3(c * 40f, 0f, 0f);

		aff.transforms = new Array<AffineTrans>();
		aff.transforms.add(new AffineTrans(AffineType.scale).scale(0.04f * (maxLevel - level)));
		aff.transforms.add(new AffineTrans(AffineType.rotation).rotate(60f * (1 - level), -30f, 0f));
		aff.transforms.add(new AffineTrans(AffineType.translate).translate(100f * c, -40f * level, 30f * level * c));
	}

}
