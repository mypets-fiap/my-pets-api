# MyPets API

API para o Startup One.

Execute o `initialize.sh` para uma execução simples do projeto.

## Executando o projeto

### Via script

    ./initialize.sh
Obs: Caso não seja possível executá-lo, favor torná-lo um arquivo executável através do comando ```chmod +x initialize.sh``` 

### Via docker-compose
    $ ./gradlew bootJar
    $ docker-compose build
    $ docker-compose up
## Validando a subida

http://localhost:8080/health

## Executando os testes

    ./run-tests.sh

## Collection do Postman