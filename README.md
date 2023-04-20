# MyPets API

API para o Startup One.

## Patterns do projeto

### Clean Architecture
Utilizamos o pattern clean architecture para conseguir ter uma arquitetura em camadas simplificada, facil e extensível, onde a idéia do código usado nesse padrão deve ser fácil de se manter, reutilizável e desacoplado. 

Dividimos as camadas da aplicaçao por config, controllers, domain, services e repository.

![img_1.png](img_1.png)

### Dependence Injection
Utilizamos nas classes a injeção de dependência dissociando o uso de um objeto de sua criação. 
seguindo o principio de inversão de dependência e responsabilidade única so SOLID. 

## Executando o projeto

### Pré-Requisitos
* JDK 17+
* Docker e Docker Compose


### Via script

    ./initialize.sh
Obs: Caso não seja possível executá-lo, favor torná-lo um arquivo executável através do comando ```chmod +x initialize.sh``` 

### Via docker-compose
    $ ./gradlew bootJar
    $ docker-compose build
    $ docker-compose up

### Via IDE (Intellij IDEA, Eclipse, etc)

1. Ajustar as configurações do banco de dados no arquivo [application.yml](src%2Fmain%2Fresources%2Fapplication.yml)
2. Executar a classe [MyPetsApplication.java](src%2Fmain%2Fjava%2Fbr%2Fcom%2Ffiap%2Fmypets%2FMyPetsApplication.java)

## Validando a subida

Para validar se tudo foi executado com sucesso, basta acessar está URL:

http://localhost:8080/health

## Executando os testes
Implementamos testes unitários no serviço para validar se as regras de negócio estão sendo executadas corretamente.

### Via script
    ./run-tests.sh

### Via terminal (na raíz do projeto)
    gradlew clean build test

## Arquitetura
![img.png](img.png)


# API endpoints
Todos os endpoints do projeto se encontram na collection _**automatizada**_ que está em [MyPets API.postman_collection.json](collection_postman%2FMyPets%20API.postman_collection.json)

### Detalhes dos endpoints

#### POST api/v1/auth/register
Cadastrar um novo usuário no sistema

**Request**

```
{
    "firstName": "Ricardo",
    "lastName": "Oliveira",
    "email": "Ricardo@hotmail.com",
    "password": "teste"
}
```
**Response**

```
{
    "id": 1,
    "firstName": "Ricardo",
    "lastName": "Oliveira",
    "email": "Ricardo@hotmail.com"
}
```
___

#### POST api/v1/auth/authenticate
Gerar um token valido para conseguir efetuar chamadas para outros endpoints.

**Request**

```
{
    "email":"Ricardo@hotmail.com",
    "password": "teste"
}
```
**Response**

```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaWNrY2hhbmc3QGhvdG1haWwuY29tIiwiZmlyc3ROYW1lIjoiSGVucmlxdWUiLCJsYXN0TmFtZSI6IkNoYW5nIiwiaWF0IjoxNjgwMTM2MzYwLCJleHAiOjE2ODAxMzc4MDB9.wVoH823VfIiuQatEf4iJHLELRYNEVZcOaAltVd6vxk8"
}
```
___

### Pets

#### GET api/v1/pet/{id}
Buscar um pet a partir do id

**Parameters**

|            Name | Required |  Type  | Description                          |
|----------------:|:--------:|:------:|--------------------------------------|
|       `id`      | required |  path  | Id do pet                            |
| `Authorization` | required | header | Token JWT de autenticaçao do usuario |

**Response**

```
{
    "id": "0de203b6-75cc-47a4-b598-6c235b5357d7",
    "nome": "luna",
    "raca": "lulu",
    "user": {
        "id": 1,
        "firstName": "Ricardo",
        "lastName": "Oliveira",
        "email": "Ricardo@hotmail.com"
    }
}
```
___

#### POST api/v1/pet
Cadastrar um pet para o usuário

|            Name | Required |  Type  | Description                          |
|----------------:|:--------:|:------:|--------------------------------------|
| `Authorization` | required | header | Token JWT de autenticaçao do usuario |

**Request**

```
{
    "nome":"luna",
    "raca":"lulu"
}
```
**Response**

```
{
    "id": "e9f0cebe-7a51-4a44-9322-a51bac213216",
    "nome": "luna",
    "raca": "lulu",
    "user": {
        "id": 1,
        "firstName": "Ricardo",
        "lastName": "Oliveira",
        "email": "Ricardo@hotmail.com"
    }
}
```
___

#### PUT api/v1/pet/{id}
Alterar os dados do pet

|            Name | Required |  Type  | Description                          |
|----------------:|:--------:|:------:|--------------------------------------|
|       `id`      | required |  path  | Id do pet                            |
| `Authorization` | required | header | Token JWT de autenticaçao do usuario |

**Request**

```
{
    "nome":"Rodolfo",
    "raca":"lulu"
}
```
**Response**

```
{
    "id": "4639dbb8-29a1-4460-9928-fcfb8e9a6269",
    "nome": "Rodolfo",
    "raca": "lulu",
    "user": {
        "id": 1,
        "firstName": "Ricardo",
        "lastName": "Oliveira",
        "email": "Ricardo@hotmail.com"
    }
}
```
___

#### DELETE api/v1/pet/{id}
Deletar um pet de seu usuário

|            Name | Required |  Type  | Description                          |
|----------------:|:--------:|:------:|--------------------------------------|
|       `id`      | required |  path  | Id do pet                            |
| `Authorization` | required | header | Token JWT de autenticaçao do usuario |

**Response**

```
 "Pet excluído com sucesso."
```
___

## Token JWT

**HEADER**
```
{
  "alg": "HS256"
}
```

**PAYLOAD**
```
{
  "sub": "rickchang7@hotmail.com",
  "firstName": "Henrique",
  "lastName": "Chang",
  "userId": 1,
  "iat": 1680219942,
  "exp": 1680221382
}
```