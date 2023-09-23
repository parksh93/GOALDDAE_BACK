FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENV SKIP_TESTS=true
ENTRYPOINT ["java","-jar","/app.jar"]