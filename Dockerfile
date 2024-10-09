FROM openjdk:17-alpine AS builder
RUN apk add --no-cache maven
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17
WORKDIR /app
COPY --from=builder ./app/target/api-0.0.1-SNAPSHOT.jar ./api-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "api-0.0.1-SNAPSHOT.jar"]