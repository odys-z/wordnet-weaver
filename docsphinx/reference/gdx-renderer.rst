GDX Renderer
============

Blending Mode
-------------

Final Color = (srcColor * srcBlendingFactor) + (dstColor * dstBlendingFactor)

* Destination Color:

the color in the buffer, which will (eventually) be drawn unless it is modified
or overwritten with new values.

* Source Color:

the color coming in from additional rendering commands, which may or may not
interact with the destination color (depending on our settings)

Blending Factors are::

    GL_ZERO: RGB(0,0,0) A(0)
    GL_ONE:  RGB(1,1,1) A(1)
    ...

See GDX API[2] for more.

Reference

1. stackoverflow question:
`How to do blending in LibGDX <https://stackoverflow.com/questions/25347456/how-to-do-blending-in-libgdx>`_

.. image:: https://i.stack.imgur.com/22m2l.jpg

2. GDX API
