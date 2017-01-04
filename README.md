# Introduction

This is a server providing a RESTful API for outsourcing calculations with big integers,
its main purpose is to serve as a backend for [famodulus-client](https://github.com/mainini/famodulus-client).
It is based on Java/JAX-RS and runs standalone using grizzly.

# Installation and Running

famodulus-server is compiled using [maven](https://maven.apache.org), which has to be installed on your system first.
Compilation is known to work using OpenJDK 1.8 and maven 3.0.5 or later.

To install, clone this repository (if you haven't already) and change to it. Then, run a clean build:

    mvn clean install

You may run the unit tests to see if compilation was successful and if the server works the way it should:

    mvn test

To start the server, you may simply run

    mvn exec:java

The server should now be running on its default port, check by connecting to [http://localhost:8081/](http://localhost:8081/) .

Logging occurs through java.util.logging, you can thus increase logging verbosity by adjusting the levels for `ch.mainini.famodulus`
and `org.glassfish.grizzly` according to your preferences.

## Change Listening Port

The change of the port on which famodulus-server is listening can be made using the system property `famodulus.base` which
has to be a URI for the base of the server. For instance, to change the port to 80, the server can be started as follows:

    MAVEN_OPTS='-Dfamodulus.base=http://localhost:80/' mvn exec:java

## Configure the Access-Control-Allow-Origin Header

[CORS](https://en.wikipedia.org/wiki/Cross-origin_resource_sharing) which is used by famodulus-client requires a set of HTTP headers to be set. Amongst them, the `Access-Control-Allow-Origin` controls from where connections to the server may occur.
By default, this is set to `"*"`, allowing access from anywhere. Using the system property `famodulus.allow_origin`, this header can be configured according to your needs.

## Enabling Montgomery Multiplication

Java JRE 1.8 on Linux and Solaris supports Montgomery multiplication which may provide significant performance improvements for the calculations performed by famodulus-server. See [JDK-8153189](https://bugs.openjdk.java.net/browse/JDK-8153189) for details.

To enable Montgomery multiplication, you may use the following command line:

    MAVEN_OPTS='-XX:+UseMontgomeryMultiplyIntrinsic -XX:+UseMontgomerySquareIntrinsic' mvn exec:java

# Usage

See [API](api.md) for a description of the API.

# Version History

## 1.0.0 (2017-01-04)

* Initial version
* RESTful API with JSON support
* Support for modular exponentiation using /api/modexp
* Configurable base URI and CORS allow origin
