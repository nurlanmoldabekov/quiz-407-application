# Test Java application made by Nurlan for the 407

#### How to start the application

From command-line, from the root directory, run `mvn clean compile package`.

Then `java -jar ./targer/router-application`.

Swagger URL: http://localhost:8080/swagger-ui/index.html

Alternatively, you can execute it without Spring Boot:

`java -jar ./target/route-application.jar withoutSpringBoot "QEW" "Highway 403"`.

Passing `withoutSpringBoot` as 1st argument, `from` and `to` as 2nd and 3rd. 