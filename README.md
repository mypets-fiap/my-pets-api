# MyPets API

API para o Startup One.

Execute o `initialize.sh` para uma execução simples do projeto.

## Patterns do projeto

Utilizamos o pattern clean architecture para conseguir ter uma arquitetura em camadas simplificada, facil e extensível, onde a idéia do código usado nesse padrão deve ser fácil de se manter, reutilizável e desacoplado. 

Dividimos as camadas da aplicaçao por config, controllers, domain, services e repository.

![img_1.png](img_1.png)

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
Implementamos testes unitários nos services para validar se as regras de negócio estão sendo executadas corretamente.

    ./run-tests.sh

## Arquitetura
![img.png](img.png)


## API endpoints
Esses endpoints permitem que você cadastre um usuário e seus respectivos pets e controle autenticação

### Autenticaçao
    
#### POST api/v1/auth/authenticate
Gerar um token valido para conseguir efetuar chamadas para outros endpoints.

**Request**

```
{
    "email":"teste@hotmail.com",
    "password": "teste"
}
```
**Response**

```
{
    "content": 
    {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaWNrY2hhbmc3QGhvdG1haWwuY29tIiwiZmlyc3ROYW1lIjoiSGVucmlxdWUiLCJsYXN0TmFtZSI6IkNoYW5nIiwiaWF0IjoxNjgwMTM2MzYwLCJleHAiOjE2ODAxMzc4MDB9.wVoH823VfIiuQatEf4iJHLELRYNEVZcOaAltVd6vxk8"
    },
    "message": null
}
```
___

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
    "content": 
    {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaWNrY2hhbmc3QGhvdG1haWwuY29tIiwiZmlyc3ROYW1lIjoiSGVucmlxdWUiLCJsYXN0TmFtZSI6IkNoYW5nIiwiaWF0IjoxNjgwMTM2MzYwLCJleHAiOjE2ODAxMzc4MDB9.wVoH823VfIiuQatEf4iJHLELRYNEVZcOaAltVd6vxk8"
    },
    "message": null
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
    "content": 
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
    },
    "message": null
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
    "content": {
        "id": "e9f0cebe-7a51-4a44-9322-a51bac213216",
        "nome": "luna",
        "raca": "lulu",
        "user": {
            "id": 1,
            "firstName": "Ricardo",
            "lastName": "Oliveira",
            "email": "Ricardo@hotmail.com"
        }
    },
    "message": null
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
    "content": {
        "id": "4639dbb8-29a1-4460-9928-fcfb8e9a6269",
        "nome": "Rodolfo",
        "raca": "lulu",
        "user": {
            "id": 1,
            "firstName": "Ricardo",
            "lastName": "Oliveira",
            "email": "Ricardo@hotmail.com"
        }
    },
    "message": null
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
{
    "content": null,
    "message": "Pet excluído com sucesso."
}
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
  "iat": 1680219942,
  "exp": 1680221382
}
```

**VERIFY SIGNATURE**
```
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  your-256-bit-secret
)
```