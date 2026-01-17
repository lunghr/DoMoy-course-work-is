FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY . .
RUN ./gradlew clean build
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./build/libs/DoMoy-course-work-0.0.1-SNAPSHOT.jar"]