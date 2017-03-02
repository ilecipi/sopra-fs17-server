# SoPra RESTful Service Template

## Spring Boot

* Documentation: http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/
* Guides: http://spring.io/guides
  * Building a RESTful Web Service: http://spring.io/guides/gs/rest-service/
  * Building REST services with Spring: http://spring.io/guides/tutorials/bookmarks/


## Setup this Template with Eclipse

Note: Feel free to develop in the IDE of your choice (e.g., [IntelliJ IDEA](https://www.jetbrains.com/idea/)).

1. Install Gradle Eclipse plugin: http://marketplace.eclipse.org/content/gradle-integration-eclipse-44
2. File -> Import... -> Gradle Project
3. Browse to `sopra-fs17-template-server` and `Build Model`

To run right click the `build.gradle` and choose `Run As` -> `gradle run`


## Building with Gradle

* Gradle installation: http://gradle.org/installation
  * Mac OS X with [Homebrew](http://brew.sh/): ``brew install gradle``

### Run

```bash
gradle bootRun
```

### Test

```bash
gradle test
```
