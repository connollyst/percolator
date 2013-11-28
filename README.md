PERCOLATOR
==========

Overview
--------

Build
-----

1. Install latest Android SDK
2. Install Android SDK artifacts into local Maven repository
    The [Maven Android SDK Deployer](https://github.com/mosabua/maven-android-sdk-deployer)
    makes this easy. Sonatype have [some good documentation](http://books.sonatype.com/mvnref-book/reference/android-dev-sect-config-build.html)
    for this step.
3. Build Percolator
    - `mvn clean package`

Release
-------

To build Percolator for release, simply build with the 'release' profile:
`mvn clean package -Prelease`

Note that to sign the apk for release on Google Play, you will need to have the
keystore used to sign Percolator.