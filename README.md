# Golden Raspberry API

API RESTful para leitura da lista de indicados e vencedores da categoria **Pior Filme** do Golden Raspberry Awards (Razzies).\
A API permite consultar os produtores que tiveram o menor e maior intervalo entre dois prêmios consecutivos.

---

## 📦 Estrutura do Projeto

O projeto segue a **arquitetura em camadas (Layered Architecture)**:

```
com.example.razzieapi/
│
├── controller/    # REST Controllers
├── service/       # Lógica de negócio
├── repository/    # Repositório JPA
├── entity/        # Entidades JPA
├── dto/           # Objetos de transferência de dados (DTOs)
├── config/        # Configurações da aplicação
├── exception/     # Tratamento de erros customizados
└── util/          # Helpers e utilitários
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
- **OpenCSV** (leitura do CSV)
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

2. Coloque o arquivo `movies.csv` na raiz do projeto:

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

4. Acesse o endpoint:

```
GET http://localhost:8080/producers/intervals
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

- **Controller** → define endpoints RESTful.
- **Service** → lógica de negócio e análise de intervalos.
- **Repository** → interface JPA para persistência.
- **Entity** → mapeamento das tabelas do H2.
- **DTO** → objetos de saída da API.
- **CsvLoaderService** → lê CSV e popula banco H2 na inicialização.

---

## 📌 Observações

- A API é **autocontida**, não requer instalação de banco externo.
- Segue o **padrão Layered Architecture**.
- Implementa **REST Nível 2 de Richardson** (uso de verbos HTTP corretos e status codes apropriados).

---

## 🔗 Referências

- [Golden Raspberry Awards](https://www.razzies.com/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [OpenCSV](http://opencsv.sourceforge.net/)
- [H2 Database](https://www.h2database.com/)

