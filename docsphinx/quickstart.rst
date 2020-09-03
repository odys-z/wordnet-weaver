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
