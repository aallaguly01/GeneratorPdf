FROM maven:3.6.0-jdk-8-slim AS build
COPY src /home/application/src
COPY pom.xml /home/application
USER root
RUN --mount=type=cache,target=/.m2 mvn -f /home/application/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /home/application/target/pdfproducer.jar /usr/local/lib/pdfproducer.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/pdfproducer.jar"]