ModelBatch & Renderable
=======================

ModelBatch Core
---------------

ModelBatch has two essential function:

.. code-block:: java

    /** Flushes the batch, causing all {@link Renderable}s in the batch to be rendered.
     * Can only be called after the call to {@link #begin(Camera)} and before the
     * call to {@link #end()}.
     */
    public void flush () {
      sorter.sort(camera, renderables);
      Shader currentShader = null;
      for (int i = 0; i < renderables.size; i++) {
        final Renderable renderable = renderables.get(i);
        if (currentShader != renderable.shader) {
            if (currentShader != null) currentShader.end();
            currentShader = renderable.shader;
            currentShader.begin(camera, context);
        }
        currentShader.render(renderable);
      }
      if (currentShader != null) currentShader.end();
      renderablesPool.flush();
      renderables.clear();
    }

    /** Add a single {@link Renderable} to the batch. The {@link ShaderProvider}
     * will be used to fetch a suitable {@link Shader}.
     * Can only be called after a call to {@link #begin(Camera)} and before a
     * call to {@link #end()}.
     * @param renderable The {@link Renderable} to be added.
     */
    public void render (final Renderable renderable) {
      renderable.shader = shaderProvider.getShader(renderable);
      renderables.add(renderable);
    }
..

BaseShaderProvider is an example of shader & mesh working together.

.. code-block:: java

    public abstract class BaseShaderProvider implements ShaderProvider {
        protected Array<Shader> shaders = new Array<Shader>();

        @Override
        public Shader getShader (Renderable renderable) {
            Shader suggestedShader = renderable.shader;
            if (suggestedShader != null && suggestedShader.canRender(renderable))
                return suggestedShader;

            for (Shader shader : shaders) {
                if (shader.canRender(renderable)) return shader;
            }

            final Shader shader = createShader(renderable);
            if (!shader.canRender(renderable))
                throw new GdxRuntimeException("unable to provide a shader for this renderable");
            shader.init();
            shaders.add(shader);
            return shader;
        }

        protected abstract Shader createShader (final Renderable renderable);

        @Override
        public void dispose () {
            ...
        }
    }
..

This is more straightforward than the explain[1].

reference
_________

1. `ModelBatch, LibGDX Wiki <https://github.com/libgdx/libgdx/wiki/ModelBatch>`_

2. `ModelBuilder, LibGDX Wiki <https://github.com/libgdx/libgdx/wiki/ModelBuilder,-MeshBuilder-and-MeshPartBuilder>`_

.. _model-instance-transform:

ModelInstance
-------------

- RenderableProvider

ModelInstance is a RenderableProvider, which provides shader a Renderable for rendering.

- Shader

Typically a shader uses Renderable's world transform for uniform mat4.

- Transform

Randerable's world transform matrix is set when ModelBatch calling RenderableProvider's
getRenderable(). For normal objects, it's actually instance's transform. See
ModelInstanc.getRenderable().

.. code-block:: java

    public Renderable getRenderable (final Renderable out, final Node node, final NodePart nodePart) {
        nodePart.setRenderable(out);
        if (nodePart.bones == null && transform != null)
            out.worldTransform.set(transform).mul(node.globalTransform);
        else if (transform != null)
            out.worldTransform.set(transform);
        else
            out.worldTransform.idt();
        out.userData = userData;
        return out;
    }
..
