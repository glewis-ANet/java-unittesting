# java-unittesting

Example unit testing project for Java

This project demonstrates a variety of unit testing features in Java using

* JUnit 4 (unit test framework)
* EasyMock (mocking framework)
* Mockito (mocking framework)
* JaCoCo (code coverage)

Relevant targets:

```
mvn test
```

Runs the unit tests

```
mvn jacoco:report
```

Runs the code coverage support.  You **must** run `mvn test` first.  To see the report,
open `target/site/jacoco/index.html`
