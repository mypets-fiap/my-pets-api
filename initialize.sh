./gradlew bootJar
docker build -t my-pets-api .
docker run -p 8080:8080 my-pets-api
