FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn install -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/stage-two-hng-0.0.1-SNAPSHOT.jar stage-two-hng.jar

EXPOSE  8080
ENTRYPOINT ["java", "-jar","stage-two-hng.jar"]






