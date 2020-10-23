package io.oz.xv.glsl.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;

import io.oz.xv.glsl.Glsl.ShaderFlag;
import io.oz.xv.glsl.WShader;
import static net.andreinc.aleph.AlephFormatter.str;

/**General Blinn-Phong shader reduced from gdx DefaultShader with:<pre>
   Directional Shadow: on
   Points Shadow:  on
   Specular Color: on
   Ambient Color:  on
   u_texture:      0
   u_aplha:        on
   fog:            off
   emission:       off
 </pre> 
 * @author Odys Zhou
 *
 */
public class PhongShadow extends WShader {
	protected int u_projviewM4 = register("u_projViewTrans");
	protected int u_normalM3 = register("u_normalMatrix");
	protected int u_modelM4 = register(new Uniform("u_worldTrans"));
	protected int u_camposV4 = register(new Uniform("u_cameraPosition"));

	protected int u_shininess = register("u_shininess");
	protected int u_ambientLight = register("u_ambientLight");

	private String vs;
	private String fs;

	public PhongShadow( ) {
		super(ShaderFlag.phong);
		// https://github.com/nomemory/aleph-formatter
		vs = Gdx.files.classpath("io/oz/xv/glsl/shaders/xlight.vert.glsl").readString();
		fs = Gdx.files.classpath("io/oz/xv/glsl/shaders/xlight.frag.glsl").readString();
		
		vs = str(vs).arg("u_dirLights", GlChunks.u_dirLights(1))
				.arg("u_pointLights", GlChunks.u_pointLights(1))
				.fmt();
		
		fs = str(fs)
				.arg("u_dirLights", GlChunks.u_dirLights(1))
				.arg("u_pointLights", GlChunks.u_pointLights(1))
				.fmt();
	}

	@Override
	public void begin (Camera camera, RenderContext context) {
		program.begin();
		context.setDepthTest(GL20.GL_LEQUAL, 0f, 1f);
		set(u_vpM4, camera.combined);
	}
	@Override
	public void render(Renderable renderable) {
		set(u_modelM4, renderable.worldTransform);

		// https://stackoverflow.com/questions/8594703/lwjgl-transparency
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		renderable.meshPart.render(program);	
	}
}
