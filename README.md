<h1 align="center">
  API REST em Java
</h1>

<p align="center">
  <img alt="Language: Java" src="https://img.shields.io/badge/language-java-green">
  <img alt="Version: 1.0" src="https://img.shields.io/badge/version-1.0-yellowgreen">
</p>


## :rocket: Tecnologias Utilizadas

* Java
* Spring Boot
* Spring Data JPA
* H2
* Docker
* Kafka

## Como Executar

Para rodar esta API REST , siga os passos abaixo:

```bash
# Clone este repositório
git clone https://github.com/coutocouto/pagamento-simplificado.git

# Suba o docker do kafka
docker-compose up

Execute a aplicação spring

#Acesse em
`http://localhost:8080`.
```
## API

### Endpoints

#### Transação entre contas

- **POST /transfer** - Realiza uma transferência entre duas contas.

##### Corpo da Requisição

```json
{
    "payee": 11,
    "payer": 12,
    "value": 100.0
}
