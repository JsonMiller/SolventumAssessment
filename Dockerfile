FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY . /app

CMD ["./mvnw", "spring-boot:run"]