FROM maven:3-eclipse-temurin-25 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B package

FROM eclipse-temurin:25
WORKDIR /app

COPY --from=build /app/target/Challenge-1.0-SNAPSHOT.jar app.jar

CMD ["java", "-cp", "app.jar", "com.juan.Main"]