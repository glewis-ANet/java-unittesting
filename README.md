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

Runs the code coverage support.  You **must** run `mvn test` first.  To see the report, open `target/site/jacoco/index.html`

## Notes

Code coverage shows as less than 100% since JaCoCo will only exclude Lombok generated code where the whole method is generated.  In places where only part of the method is generated (e.g., placing `@NonNull` on a method parameter) JaCoCo still considers that code when calculating code coverage.  Such code is deliberately not targeted in the included unit tests since it is generated.
