Building Shape
==============

Mesh(Part)Build
---------------

Usage:

.. code-block:: java

    vis = new VertexInfo[4] { /* inited */ };

    Node node = builder.node();
    node.id = String.format("p1");
    node.scale.scl(paperScl);
    node.translation.set(0f, 0f, 0f);

    MeshPartBuilder mpbuilder = new ModelBuilder().part(
            "part1",
            GL20.GL_TRIANGLES, Usage.Position | Usage.Normal,
            inkMat);
    mpbuilder.rect(vi4[0], vi4[1], vi4[2], vi4[3]);
..

ModelBuilder#part() Creates a new MeshPart within the current Node and returns
a MeshPartBuilder interface's implementation, MeshBuilder, which can be used to
build the shape of the part.

.. code-block:: java

    public MeshPartBuilder part (final String id, int primitiveType,
           final Material material) {
        final MeshBuilder builder = getBuilder(attributes);
        part(builder.part(id, primitiveType), material);
        return builder;
    }

    public MeshPart part (final String id, final int primitiveType) {
        part = new MeshPart();
        part.id = id;
        parts.add(part);

        setColor(null);
        setVertexTransform(null);
        setUVRange(null);

        return part;
    }
..

When calling MeshBuilder.rect(), it push vertex into it's buffer.

.. code-block:: java

    public void rect (VertexInfo corner00, VertexInfo corner10, VertexInfo corner11, VertexInfo corner01) {
        ensureVertices(4);
        index(vertex(corner00), vertex(corner10), vertex(corner11), vertex(corner01));
    }

    public void index (short value1, short value2, short value3, short value4, short value5, short value6) {
        ensureIndices(6);
        indices.add(value1);
        indices.add(value2);
        indices.add(value3);
        indices.add(value4);
        indices.add(value5);
        indices.add(value6);
    }

    // unpack VertextInfo then:
    public short vertex (Vector3 pos, Vector3 nor, Color col, Vector2 uv) {
        if (vindex > Short.MAX_VALUE) throw new GdxRuntimeException("Too many vertices used");

        vertex[posOffset] = pos.x;
        if (posSize > 1) vertex[posOffset + 1] = pos.y;
        if (posSize > 2) vertex[posOffset + 2] = pos.z;

        if (norOffset >= 0) {
            if (nor == null) nor = tmpNormal.set(pos).nor();
            vertex[norOffset] = nor.x;
            vertex[norOffset + 1] = nor.y;
            vertex[norOffset + 2] = nor.z;
        }

        if (colOffset >= 0) {
            if (col == null) col = Color.WHITE;
            vertex[colOffset] = col.r;
            vertex[colOffset + 1] = col.g;
            vertex[colOffset + 2] = col.b;
            if (colSize > 3) vertex[colOffset + 3] = col.a;
        } else if (cpOffset > 0) {
            if (col == null) col = Color.WHITE;
            vertex[cpOffset] = col.toFloatBits(); // FIXME cache packed color?
        }

        if (uv != null && uvOffset >= 0) {
            vertex[uvOffset] = uv.x;
            vertex[uvOffset + 1] = uv.y;
        }

        addVertex(vertex, 0); // vertex been pushed into vertices
        return lastIndex;
    }
..

At the end of building, when MeshBuilder.end() called, all vertices been collected,
with indices.

.. code-block:: java

    public Mesh end () {
        Mesh mesh = new Mesh();
        mesh.setVertices(vertices.items, 0, vertices.size);
        mesh.setIndices(indices.items, 0, indices.size);

        for (MeshPart p : parts)
            p.mesh = mesh;
        parts.clear();

        attributes = null;
        vertices.clear();
        indices.clear();

        return mesh;
    }
..
