FROM openjdk:11-jre-slim
WORKDIR /app
ARG JAR_FILE=target/spring-boot-rest-app-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar","--spring.datasource.url=jdbc:mysql://host.docker.internal:3306/test_db"]