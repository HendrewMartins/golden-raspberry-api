# Golden Raspberry API

API RESTful para leitura da lista de indicados e vencedores da categoria **Pior Filme** do Golden Raspberry Awards (Razzies).\
A API permite consultar os produtores que tiveram o menor e maior intervalo entre dois prêmios consecutivos.

---

## 📦 Estrutura do Projeto

O projeto segue a **arquitetura em camadas (Layered Architecture)**:

```
br.hendrew.goldenraspberryapi/
│
├── calculator/    # Classes que implementam regras de cálculo e lógica específica (ex: ProducerIntervalCalculator)
├── controller/    # REST Controllers que expõem endpoints da API
├── service/       # Serviços com a lógica de negócio principal e integração entre repositórios e cálculos
├── repository/    # Interfaces JPA para acesso e persistência de dados no banco
├── entity/        # Entidades JPA que representam tabelas do banco de dados
├── dto/           # Data Transfer Objects (DTOs) usados para enviar/receber dados na API
├── config/        # Configurações gerais da aplicação (ex: beans)
├── exception/     # Exceções customizadas e tratamento de erros
├── mapper/        # MapStruct mappers para converter entre DTOs e entidades
├── parse/         # Classes para parse e processamento de arquivos (ex: CSV)
└── properties/    # Classes para leitura de propriedades externas (application.yaml/properties)
```

---

## 📝 Requisitos

### Funcionais

1. Ler arquivo CSV com colunas: `year, title, studios, producers, winner`.
2. Inserir dados em banco H2 ao iniciar a aplicação.
3. Expor endpoint RESTful para:
    - Obter produtor(es) com **maior intervalo** entre dois prêmios consecutivos.
    - Obter produtor(es) com **menor intervalo** entre dois prêmios consecutivos.
4. Retornar JSON com duas listas: `min` e `max`. Cada objeto contém:
   ```json
   {
     "producer": "Nome do Produtor",
     "interval": 1,
     "previousWin": 1980,
     "followingWin": 1981
   }
   ```

### Não funcionais

1. Web service RESTful no **Nível 2 de Richardson**.
2. Testes de integração garantindo que os dados estejam corretos.
3. Banco em memória H2 (não exige instalação externa).
4. Código versionado em repositório Git.

---

## ⚡ Tecnologias

- **Java 21**
- **Spring Boot 3.5**
- **Spring Web** (REST API)
- **Spring Data JPA** (acesso ao banco)
- **H2 Database** (em memória)
- **JUnit 5 + Spring Boot Test** (testes de integração)
- **Lombok** (redução de boilerplate, opcional)
- **Springdoc OpenAPI / Swagger UI** (opcional, documentação da API)

---

## 🚀 Como executar

1. Clone o repositório:

```bash
git clone https://github.com/HendrewMartins/golden-raspberry-api.git
cd golden-raspberry-api
```

2. Coloque o arquivo `src/main/resources/simulation/Movielist.csv` ou altere o caminho da propertie `csv-movie.directory:` no application.yaml :

```
year,title,studios,producers,winner
1980,Movie A,Studio X,Producer 1,yes
1981,Movie B,Studio Y,Producer 1,no
1982,Movie C,Studio Z,Producer 2,yes
...
```

3. Build e run usando Gradle:

```bash
./gradlew bootRun
```

4. Acesse o Swagger(Caso necessário ajustar para a porta correspondente):

```
GET http://localhost:8080/golden-raspberry-api/swagger-ui/index.html#
```
ou pela URL diretamente 
```
http://localhost:8080/golden-raspberry-api/api/v1/movie/producers/intervals
```

Exemplo de retorno:

```json
{
  "min": [
    {
      "producer": "Producer 1",
      "interval": 1,
      "previousWin": 1980,
      "followingWin": 1981
    }
  ],
  "max": [
    {
      "producer": "Producer 2",
      "interval": 10,
      "previousWin": 1982,
      "followingWin": 1992
    }
  ]
}
```

---

## 🧪 Testes

- Testes de integração localizados em `src/test/java`.
- Para rodar:

```bash
./gradlew test
```

---

## 📂 Estrutura de Camadas

- **Controller** → define endpoints RESTful da API, expondo recursos como filmes, produtores e intervalos de premiações.
- **Service** → contém a lógica de negócio, incluindo cálculo de intervalos (`MovieService`) e carregamento de dados (`CsvLoaderService`).
- **Repository** → interfaces JPA que abstraem o acesso e persistência de dados no banco H2.
- **Entity** → mapeamento das tabelas do banco, representando filmes, produtores e outras entidades.
- **DTO** → objetos de transferência de dados usados nas requisições e respostas da API, garantindo que apenas os dados necessários sejam expostos.
- **CsvLoaderService** → lê arquivos CSV e popula o banco H2 na inicialização da aplicação.
- **Calculator** → classes que implementam regras de cálculo específicas, como intervalos de premiações (`ProducerIntervalCalculator`).
- **Mapper** → classes MapStruct que convertem entre entidades e DTOs.
- **Parse** → classes responsáveis pelo parse e processamento de arquivos (CSV).
- **Properties** → classes que carregam propriedades externas, como diretórios de CSV, perfis de ambiente, etc.
- **Exception** → exceções customizadas e tratamento de erros da aplicação.
- **Config** → configurações gerais da aplicação, como beans, CORS, segurança e outras integrações.

---

## 📌 Observações

- A API é **autocontida**, não requer instalação de banco externo.
- Segue o **padrão Layered Architecture**.
- Implementa **REST Nível 2 de Richardson** (uso de verbos HTTP corretos e status codes apropriados).

---

## 🔗 Referências
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [H2 Database](https://www.h2database.com/)

