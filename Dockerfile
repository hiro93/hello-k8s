FROM openjdk:11

WORKDIR /code
COPY target/hello-k8s-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "hello-k8s-0.0.1-SNAPSHOT.jar"]
