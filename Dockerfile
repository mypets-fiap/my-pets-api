FROM openjdk:17
EXPOSE 8080
COPY build/libs/my-pets-1.0.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java","-jar", "my-pets-1.0.jar"]