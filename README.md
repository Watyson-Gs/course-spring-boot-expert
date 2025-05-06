# Curso - Spring Boot Expert

Este projeto foi desenvolvido como parte do curso **"Spring Boot Expert"**, com foco em práticas avançadas de desenvolvimento com **Spring Boot**.
A aplicação é uma **API REST para gerenciamento de produtos**, servindo como estudo de caso completo.

---

## 📑 Índice

* [Sobre o Projeto](#-sobre-o-projeto)
* [Funcionalidades Principais](#-funcionalidades-principais)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Pré-requisitos](#-pré-requisitos)
* [Como Começar](#-como-começar)
    * [Configuração do Ambiente](#configuração-do-ambiente)
    * [Executando a Aplicação](#executando-a-aplicação)
* [Endpoints da API e Documentação](#-endpoints-da-api-e-documentação)
* [Estrutura do Projeto](#-estrutura-do-projeto)
* [Contribuições](#-contribuições)
* [Licença](#-licença)

---

## 📘 Sobre o Projeto

Este projeto é uma **API RESTful para gerenciamento de um catálogo de produtos**. Foi desenvolvido com base nos ensinamentos do curso *Spring Boot Expert*, abordando:

* CRUD completo com Spring Data JPA
* Filtros dinâmicos com Specifications
* Validação de dados com Bean Validation
* Tratamento centralizado de exceções
* Logging configurável via `.env`
* Documentação com Swagger UI

---

## 🚀 Funcionalidades Principais

* Operações **CRUD** para produtos
* **Filtros dinâmicos**, ordenação e paginação
* Mapeamento entre DTOs e entidades com **MapStruct**
* **Validação de entrada** com Hibernate Validator
* Tratamento global de exceções
* Documentação interativa com Swagger UI
* **DevTools** para hot reload
* Uso de **Lombok** para redução de boilerplate

---

## 🔧 Tecnologias Utilizadas

* Java 25
* Spring Boot
* Spring Data JPA
* Hibernate
* H2 Database
* MapStruct
* Springdoc OpenAPI (Swagger)
* Lombok
* Maven
* Logback

---

## ⚙️ Pré-requisitos

Certifique-se de ter:

* **JDK 25** ou superior
* **Maven** instalado

---

## 🛠️ Como Começar

### Configuração do Ambiente

Clone o repositório:

```bash
git clone https://github.com/Watyson-Gs/course-spring-boot-expert.git
cd course-spring-boot-expert
```

Crie o arquivo `.env` na raiz:

<details>
<summary><code>.env</code> (clique para visualizar)</summary>

```env
# ===============================
# Configurações da Aplicação
# ===============================

SPRING_APPLICATION_NAME="Curso - Spring Boot Expert"

# ===============================
# Banco de Dados (Dev)
# ===============================

DATABASE_URL=jdbc:h2:mem:db
DATABASE_USERNAME=sa
DATABASE_PASSWORD=
DATABASE_DRIVER=org.h2.Driver
HIBERNATE_DIALECT=org.hibernate.dialect.H2Dialect

# ===============================
# Documentação da API
# ===============================

API_DOC_ENABLED=true
API_UI_ENABLED=true

# ===============================
# Logging
# ===============================

ROOT_LOG_LEVEL=INFO
PROJECT_LOG_LEVEL=DEBUG
SPRING_WEB_LOG_LEVEL=WARN
```

</details>

> ⚠️ Adicione `.env` ao `.gitignore` em ambientes de produção.

---

### Executando a Aplicação

<details>
<summary><strong>Via Maven (recomendado)</strong></summary>

```bash
mvn clean install
mvn spring-boot:run
```

</details>

<details>
<summary><strong>Via IDE</strong></summary>

* Importe como projeto Maven
* Execute a classe `org.project.Main`

</details>

A aplicação estará disponível em `http://localhost:8080`.

---

## 📚 Endpoints da API e Documentação

Acesse a documentação interativa:
👉 [`http://localhost:8080/swagger`](http://localhost:8080/swagger)

### Principais Endpoints

```
POST   /produtos         # Criar produto
GET    /produtos/{id}    # Buscar por ID
GET    /produtos         # Listar com filtros
PATCH  /produtos/{id}    # Atualizar parcialmente
DELETE /produtos/{id}    # Remover produto
```

<details>
<summary>Exemplo de requisição com filtros</summary>

```
GET /produtos?nome=camiseta&minPreco=50&size=5&sort=preco,desc
```

</details>

---

## 🗂️ Estrutura do Projeto

<details>
<summary>Clique para expandir</summary>

```
src/main/java/org/project/
├── controller/        # Camada REST
├── domain/
│   ├── entity/        # Entidades JPA
│   ├── mapper/        # MapStruct
│   ├── request/       # DTOs de entrada
│   └── response/      # DTOs de saída
├── handler/           # Tratamento de exceções
├── repository/
│   └── specification/ # Filtros dinâmicos
├── service/           # Regras de negócio
└── Main.java          # Entrada da aplicação

src/main/resources/
├── application.yml
├── data.sql
├── logback-spring.xml
├── messages.properties

.env
.gitignore
pom.xml
README.md
LICENSE
```

</details>

---

## 🧪 Testes

> ⚠️ O projeto ainda **não possui testes implementados**.

---

## 🤝 Contribuições

Este projeto foi criado como parte do curso *Spring Boot Expert*.
Sinta-se à vontade para fazer fork e adaptá-lo aos seus estudos.

---

## 📄 Licença

Distribuído sob a **Licença MIT**.
Consulte o arquivo `LICENSE` para mais detalhes.