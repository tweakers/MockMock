FROM maven:3.8.1-jdk-11-openj9 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:11
COPY --from=build /home/app/target/MockMock-1.4.0.one-jar.jar /usr/local/lib/app.jar
EXPOSE 8282
ENTRYPOINT [ "java", "-jar", "/usr/local/lib/app.jar" ]