# product-ms

> Neste microserviço é possível criar, alterar, visualizar e excluir um determinado produto, além de visualizar a lista de produtos atuais disponíveis. Também é possivel realizar a busca de produtos filtrando por name, description e price.

- As instruções abaixo vão demonstrar alguns pontos importantes para o desenvolvimento do projeto.
- OpenJDK 11.0.14;
- Maven 3.6.3;
- Docker

## Build Aplication

> Construa o arquivo JAR da aplicação. Execute o seguinte comando de um shell na raiz do projeto.

```
    ./mvnw clean package 
```
> O comando irá baixar todas as dependências do projeto e criar um diretório target com os artefatos construídos, que incluem o arquivo jar do projeto. Inclusive executar os teste unitários do projeto.

### Run tests

> Para rodar apenas os testes, execute o seguinte comando de um shell na raiz do projeto:

```
    ./mvnw clean test
```

### Build the Docker containers
> Para criar o container docker da aplicação, execute o seguinte comando na raiz do seu projeto:
``` 
    docker-compose build
```

> Para realizar o start da aplicação, usando o docker compose, execute o seguinte comando na raiz do seu projeto:

```
    docker-compose up -d
```
> Para finalizar a aplicação use o docker compose, execute o seguinte comando na raiz do seu projeto:
```
    docker-compose down
```

## Documentação da API
Para acessar documentação da API é necessario que o aplicação esteja sendo executada, será possivel acessar através dos links abaixo:
```
swagger.json: http://127.0.0.1:9999/v2/api-docs
swagger-ui: http://127.0.0.1:9999/swagger-ui.html
```