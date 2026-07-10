# Gerenciamento de Tarefas

## Descrição

Aplicação Full Stack para gerenciamento de tarefas, desenvolvida com **Java + Spring Boot** no back-end e **Angular** no front-end. A aplicação permite cadastrar, consultar, filtrar, editar e excluir tarefas, proporcionando uma interface simples para organização das atividades.

---

# Pré-requisitos

Antes de executar o projeto, é necessário possuir instalado:

* Java 17
* Maven 3.8+
* Node.js
* npm
* Microsoft SQL Server
* Angular CLI

---

# Como executar o projeto

## 1.Banco de Dados

A aplicação está configurada para criar e atualizar automaticamente as tabelas do banco de dados através do Hibernate.

Para isso, basta configurar a propriedade abaixo no arquivo application-dev.properties:

spring.jpa.hibernate.ddl-auto=update

Caso seja necessário recriar completamente o esquema do banco durante o desenvolvimento, pode-se utilizar:

```bash
spring.jpa.hibernate.ddl-auto=create
```

ou

```bash
spring.jpa.hibernate.ddl-auto=create-drop
```
Observação: Essas configurações são recomendadas apenas para ambientes de desenvolvimento. Em ambientes de produção, o ideal é utilizar ferramentas de migração de banco de dados, como Flyway ou Liquibase.

---

## 2. Executar o Back-end

Na raiz do projeto do back-end, execute a aplicação pela sua IDE ou através do Maven:

```bash
mvn spring-boot:run
```

A API será iniciada na porta configurada no projeto.

---

## 3. Executar o Front-end

Abra um terminal na pasta **webapp**:

```bash
cd webapp
```

Instale as dependências (caso seja a primeira execução):

```bash
npm install
```

Execute a aplicação:

```bash
npm run start
```

Após a inicialização, a aplicação estará disponível em:

```text
http://localhost:4201/app
```

---

# Tecnologias utilizadas

## Back-end

* Java 17
* Spring Boot
* Spring Data JPA (Hibernate)
* Maven
* Junit
* Mockito

## Front-end

* Angular
* TypeScript
* PrimeNG
* HTML5
* CSS3

## Banco de Dados

* Microsoft SQL Server

---

# Decisões arquiteturais

O projeto foi desenvolvido utilizando uma arquitetura em camadas (**Layered Architecture**), promovendo a separação de responsabilidades e facilitando a manutenção e evolução da aplicação.

### Controller

Responsável por receber as requisições HTTP e expor os endpoints da API.

### Service

Contém as regras de negócio e realiza o processamento das operações antes de acessar os dados.

### Repository

Responsável pelo acesso ao banco de dados utilizando Spring Data JPA.

### Entity

Representa as entidades persistidas no banco de dados.

### Front-end

O Angular foi organizado utilizando:

* Componentes para construção das telas;
* Services para comunicação com a API REST;
* Data Binding para atualização automática da interface;
* Componentes do PrimeNG para padronização visual;
* Separação entre lógica de negócio e apresentação.

Essa estrutura proporciona maior organização do código, facilidade de manutenção e escalabilidade.

---

# Funcionalidades

* Cadastro de tarefas
* Consulta paginada de tarefas
* Pesquisa utilizando filtros
* Edição de tarefas
* Exclusão de tarefas
* Feedback visual através de diálogos de sucesso e erro
* Indicador de carregamento durante chamadas à API

---

# Banco de Dados

Não é necessário executar scripts SQL para criação das tabelas.

Após criar o banco de dados **gerenciamento**, basta configurar corretamente a conexão com o SQL Server no arquivo `application-dev.properties` e definir:

```properties
spring.jpa.hibernate.ddl-auto=update
```

O Hibernate será responsável por criar e atualizar automaticamente todas as tabelas da aplicação durante a inicialização.

> **Observação:** A configuração `ddl-auto=update` é recomendada para ambiente de desenvolvimento. Em ambientes de produção, recomenda-se a utilização de ferramentas de migração de banco de dados, como Flyway ou Liquibase.
