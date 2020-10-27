package io.oz.wnw.my;

import io.oz.jwi.SynsetInf;

/**My weaver's client.
 * 
 * @author Odys Zhou
 *
 */
public class MyWeaver {

	public MyWeaver(ISettings settings) {
	}
	
	public SynsetInf myset() {
		return new SynsetInf("Dreamweaver")
				.setMemory("Moana", 1)
				.setMemory("Tinkle", 3)
				.setMemory("Coco", 2);
	}

}
