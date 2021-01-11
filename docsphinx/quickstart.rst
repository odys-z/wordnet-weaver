Start from sources
==================

Clone project::

    git clone https://github.com/odys-z/wordnet-weaver.git wnw

Import wn-server
----------------

In Eclipse (tested on Oxygen)::

    import -> existing maven project -> browse to file://wnw/wn-serv

Import anclient.weaver
----------------------

In Eclipse (tested on Oxygen)::

    import -> existing maven project -> browse to file://wnw/lib/anclient

Import normod
-------------

The normod is a `libGDX <https://libgdx.badlogicgames.com/>`_ gradle project with
sub-prorjects `created with gdx-setup <https://libgdx.badlogicgames.com/documentation/gettingstarted/Creating%20Projects.html>`_.

The default gradle project is been set using gradle wrapper's local mode. If your
need a newer version, or using gradle online mode, change this file::

    wnw/normode/gradle/wrapper/gradle-wrapper.properties

comment out line 5 and use this line::

    distributionUrl=https\://services.gradle.org/distributions/gradle-5.4.1-bin.zip

If using local mode, the gradle binary zip must been downloaded and saved locally,
and the file path needing to be changed in configure file.

Gradle.bin.zip can be `download from gradle home page <https://gradle.org/releases/>`_.

To import normod, in Eclipse::

    import -> existing gradle project -> browse to wnw/normode

Note: According to the author's experience, you better run desktop project in
Eclipse and run Android project from Android Studio.

*Note*

The normal project depends on Ashley and `Universal-tween-engine <https://github.com/AurelienRibon/universal-tween-engine`_.
Both have some problem for newest GDX version to depends on. And have to be installed
in local maven repository.

To install Ashley locally, see `Ashely issue #279 <https://github.com/libgdx/ashley/pull/279>`_.

To install Universal-tween-engine, run it's gradle task.

If Eclipse doesn't recognize dependency class, this may help::

    right click build.gradle -> gradle -> refresh project

Troublshootings
---------------

Class of Dependency not Found
_____________________________

Error:

When running desktop, report error message like::

    Exception in thread "LWJGL Application" com.badlogic.gdx.utils.GdxRuntimeException: java.lang.NoClassDefFoundError: io/oz/wnw/my/ISettings
    at com.badlogic.gdx.backends.lwjgl.LwjglApplication$1.run(LwjglApplication.java:135)
    Caused by: java.lang.NoClassDefFoundError: io/oz/wnw/my/ISettings
    ...

Cause:

The normode/core gradle sub-project depends on another maven project, anclient.weaver.
The desktop application can't find it's class in run time environment.

Solution:

In core/gradle.build, add compile dependency after applied Java plugin. (
`Otherwise the compile command will failed <https://stackoverflow.com/questions/23796404/could-not-find-method-compile-for-arguments-gradle>`_.)
::

    dependencies {
	    compile 'io.github.odys-z:anclient.weaver:0.0.1-SNAPSHOT'
    }

Update anclient.weaver dependency or install it to local repository::

    mvn install

then have normal/gradle.build use mavenLocal::

    repositories {
        mavenLocal()
        ...
    }

Now the gradlew run task should start the desktop application.

When using Eclipse to debug, the depending project must been added to runtime
classpath.

.. image:: imgs/002-mvn-prj-dependency.png

Gradle failed on Resolving tween-engine-api
___________________________________________

Error::

    FAILURE: Build failed with an exception.

    * What went wrong:
    A problem occurred configuring root project 'normode'.
    > Could not resolve all artifacts for configuration ':classpath'.
       > Could not resolve com.aurelienribon:tween-engine-api:6.3.3.
         Required by:
             project :
          > Could not resolve com.aurelienribon:tween-engine-api:6.3.3.
             > Could not get resource 'https://repo.maven.apache.org/maven2/com/aurelienribon/tween-engine-api/6.3.3/tween-engine-api-6.3.3.pom'.
                > Could not GET 'https://repo.maven.apache.org/maven2/com/aurelienribon/tween-engine-api/6.3.3/tween-engine-api-6.3.3.pom'.
                   > No route to host (Host unreachable)

Solution:

Install `universal-tween-engine <https://github.com/AurelienRibon/universal-tween-engine>`_
locally.

It's recommended use the forked version on Ubuntu.

::

    git clone https://github.com/odys-z/universal-tween-engine.git
	cd universal-tween-engine
	gradle

The defualt task is configure as installing local repo.

Installing Universal-tween-engine on Ubuntu
___________________________________________

Error

Gradle complain about command not found while installing to local repository.

Cause:

The gradle task script can’t do the job of installing tween-engine locally.

Solution:

Try this `modified build.gradle version <https://github.com/odys-z/universal-tween-engine/blob/master/build.gradle>`_

Can not attach source to GDX.jar
________________________________

This is probably caused by using mavenLocal in gradle project. Just set::

    DdownloadSources=true
    -DdownloadJavadocs=true

won't work. See `similar report <https://stackoverflow.com/a/26529202/7362888>`_.

It's a weired behavior `reported and solved by Andreas Kuhrwahl <https://stackoverflow.com/a/12836295>`_.

To solve the problem, see::

    normode/core/gradle.build:

.. code-block:: groovy

    eclipse.classpath.file {
        withXml { xml ->
            def node = xml.asNode()
            node.remove( node.find { it.@path == 'org.eclipse.jst.j2ee.internal.web.container' } )
            node.appendNode( 'classpathentry', [ kind: 'con', path: 'org.eclipse.jst.j2ee.internal.web.container', exported: 'true'])
        }
    }
..

Also source.jar and javadoc.jar can be download manually, e.g. ::

    wget https://repo.maven.apache.org/maven2/com/badlogicgames/gdx/gdx/1.9.11/gdx-1.9.11-sources.jar
