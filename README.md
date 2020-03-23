# spark-gradle-template
A project template for spark projects with Scala using Gradle

## Prerequisites
* JDK 1.8
* Intellij or Eclipse for IDE
* Scala plugin in case of IDE

## General
The project consists of multi modules to develop various spark applications, manage them seperately, and finally deploy as distribution. It also contains example to develop spark application locally, unit test and automate the build process. The examples are depicted such a way that business logics are isolated from spark dependency to migrate to different ecosystem in future if required.
### Modules
The project contains below modules for sample.
#### spark1
A simple issue count retrieval based on simple join with all test cases.

#### spark2
An empty project as its just to show the build process.

### Features
* Spotbugs - Static analysis to look for bugs in Scala Code
* SCoverage - Code coverage tool for scala that offers statement and branch coverage
* Gemnasium - Project dependencies analyser
* Scalastyle - Scale code examiner and similar to Checkstyle
* ScalaTest - Unit test all the components 

### Build scripts

* Compilation and build artifacts
```
./gradlew -x test -x check clean build
```
* Execute all the tests
```
./gradlew test
```
* All the checks including dependency, code coverage, sast, etc
```
./gradlew check
```
* In case of publishing the artifacts either individual module jar or the tarball from distribution, maven-publish can be used.

### Customization
All the properties in gradle.properties can be customized to control the build operation
```
scala_version=2.11
scoverage_minimum_rate=0.95
spotbugs_level=high
project_version=local
scalastyle_fail_on_warning=false
spark_version=2.4.4
spark_testing_base_version=2.4.5_0.14.0
logback_classic_version=1.2.3
log4j_over_slf4j_version=1.7.25
scopt_version=3.7.1
scala_test_version=3.1.1
junit_version=4+
```

### Reports
* Test Reports - build/reports/allTests/index.html
* Scalastyle - spark1/build/reports/scalastyle/main.xml, spark1/build/reports/scalastyle/test.xml 
* Spotbugs - spark1/build/reports/spotbugs/main.html
* Scoverage - spark1/build/reports/scoverage/index.html
* Gemnasium - spark1/build/reports/gemnasium/gradle-dependencies.json

### Binary distribution
Final distribution is available in build/distributions/ both in zip and tar.gz format

## References
* Multiple github account setup [https://gist.github.com/oanhnn/80a89405ab9023894df7]
* Scalastyle Plugin [https://github.com/alisiikh/gradle-scalastyle-plugin] [http://www.scalastyle.org/]
* Multiple Java versions in MacOS [https://medium.com/@brunofrascino/working-with-multiple-java-versions-in-macos-9a9c4f15615a]