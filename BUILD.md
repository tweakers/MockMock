Building
========

To build MockMock with Maven, first download Apache Maven (http://maven.apache.org/), at least version 3.0.
Then, run:
`mvn package`

This will compile MockMock and package it in the target folder. Then it will repackage it with the dependencies so
there is only 1 jar file to redistribute: MockMock-<version>-jar-with-dependencies.jar