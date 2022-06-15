### Spring boot app with REST API

- Java, RestAssured, Retrofit, MockMvc, Mockito, Allure, Cypress, Gatling
- Run app: `mvn spring-boot:run`
- Run app in Docker container: `make run`
- Stop and remove Docker containers: `make stop`
- Run tests: `mvn clean test`
- Run cypress: `npx cypress open`
- Run Gatling tests: `mvn gatling:test`
- Allure report: `mvn allure:serve`
- Gatling report is generated to: `target/gatling`
- Jacoco report is generated to: `target/site/jacoco/index.html`
- Swagger ui: `http://localhost:{port}/swagger-ui/index.html`
- Default port is 8080

### Requirements

- Java 11
- MySQL
- Docker
