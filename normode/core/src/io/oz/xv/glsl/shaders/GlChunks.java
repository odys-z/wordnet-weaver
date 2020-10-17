package io.oz.xv.glsl.shaders;

public class GlChunks {
	/**Line delimiter for joining glsl source string */
	public static final CharSequence delimiter = System.getProperty("line.separator");

	/**<pre>
       struct DirectionalLight {
     	 vec3 color;
     	 vec3 direction;
       };
       uniform DirectionalLight u_dirLights[numDirectionalLights];</pre>
	 */
	public static final String u_dirLights(int numDirectionalLights) {
		return String.join(delimiter,
			"");
	};

	/**<pre>
       struct PointLight {
         vec3 color;
         vec3 position;
       };
       uniform PointLight u_pointLights[numPointLights];<pre>
	 * @param numPointLights 
	 * @return
	 */
	public static final String u_pointLights(int numPointLights) {
		return String.join(delimiter,
			"");
	};

}
