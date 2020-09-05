package io.oz.wnw.norm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import io.oz.wnw.my.ISettings;

public class Settings implements ISettings {
	public static boolean soundEnabled = true;
	public final static int[] highscores = new int[] {100, 80, 50, 30, 10};
	public final static String file = ".dreamer";

	@Override
	public ISettings load () {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			
			String[] strings = filehandle.readString().split("\n");
			
			soundEnabled = Boolean.parseBoolean(strings[0]);
			for (int i = 0; i < 5; i++) {
				highscores[i] = Integer.parseInt(strings[i+1]);
			}
		} catch (Throwable e) {
			// :( It's ok we have defaults
		}
		return this;
	}

	public ISettings save () {
		try {
			FileHandle filehandle = Gdx.files.external(file);
			
			filehandle.writeString(Boolean.toString(soundEnabled)+"\n", false);
			for (int i = 0; i < 5; i++) {
				filehandle.writeString(Integer.toString(highscores[i])+"\n", true);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return this;
	}

	public ISettings put (String k, Object v) {
		if ( "score".equals(k) )
			addScore((int) v);
		// else not used
		return this;
	}

	@Override
	public Object get(String k) {
		return "";
	}

	public ISettings addScore (int score) {
		for (int i = 0; i < 5; i++) {
			if (highscores[i] < score) {
				for (int j = 4; j > i; j--)
					highscores[j] = highscores[j - 1];
				highscores[i] = score;
				break;
			}
		}
		return this;
	}
}