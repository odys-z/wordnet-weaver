package io.oz.wnw.norm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Profile {
	/**Local preference file */
	public final static String locFile = ".weaver";

	public static void load () {
		try {
			FileHandle filehandle = Gdx.files.external(locFile);
			
			String[] strings = filehandle.readString().split("\n");
			
			for (int i = 0; i < 5; i++) {
				Integer.parseInt(strings[i+1]);
			}
		} catch (Throwable e) {
		}
	}

	public static void save () {
		try {
			FileHandle filehandle = Gdx.files.external(locFile);
			
			for (int i = 0; i < 5; i++) {
				filehandle.writeString(Integer.toString(i)+"\n", true);
			}
		} catch (Throwable e) {
		}
	}

}
