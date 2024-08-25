From openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/TwitterHTTPalert-0.0.1-SNAPSHOT.jar app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
