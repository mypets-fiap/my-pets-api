# MyPets API

API para o Startup One.

`run-tests.sh` runs a simplistic test and generates the API
documentation below.

It uses `run-curl-tests.rb` which runs each command defined in
`commands.yml`.

## Executando o projeto

### Via script

    ./initialize.sh
Obs: Caso não seja possível executá-lo, favor torná-lo um arquivo executável através do comando ```chmod +x initialize.sh``` 

### Via docker
    $ ./gradlew bootJar
    $ docker build -t my-pets-api .
    $ docker run -p 8080:8080 my-pets-api
## Validando a subida

http://localhost:8080/health

## Executando os testes

    ./run-tests.sh

## Collection do Postman