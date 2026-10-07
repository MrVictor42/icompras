# 🚀 Sistema de Compras — Microserviços com Spring Boot

Projeto desenvolvido ao longo do curso, com o objetivo de construir uma **plataforma de compras distribuída baseada em arquitetura de microsserviços**, utilizando tecnologias modernas do ecossistema Java.

Durante o desenvolvimento, serão abordados conceitos de **Spring Boot, Microsserviços, Apache Kafka, Docker, JasperReports, APIs REST, comunicação assíncrona e geração de relatórios**.

---

## 📚 Sobre o projeto

Neste projeto construiremos, passo a passo, uma aplicação de compras utilizando uma arquitetura baseada em microsserviços.

O sistema será responsável por gerenciar diferentes etapas do processo de compra, desde o cadastro e gerenciamento de informações até o processamento dos pedidos e geração de relatórios.

A comunicação entre os serviços será realizada utilizando **APIs REST** e **eventos assíncronos através do Apache Kafka**.

Ao final do curso, teremos uma aplicação distribuída executando em containers Docker.

---

## 🏗️ Arquitetura

A aplicação será dividida em diferentes microsserviços, cada um responsável por uma parte específica do domínio.

Uma visão simplificada da arquitetura:

```text
                         ┌──────────────────┐
                         │      Client      │
                         └────────┬─────────┘
                                  │
                                  ▼
                         ┌──────────────────┐
                         │    API / Gateway │
                         └────────┬─────────┘
                                  │
                  ┌───────────────┼───────────────┐
                  │               │               │
                  ▼               ▼               ▼
          ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
          │    Product   │ │    Orders    │ │    Customer  │
          │   Service    │ │   Service    │ │   Service    │
          └──────────────┘ └──────┬───────┘ └──────────────┘
                                  │
                                  │ Events
                                  ▼
                         ┌──────────────────┐
                         │      Kafka       │
                         └────────┬─────────┘
                                  │
                                  ▼
                         ┌──────────────────┐
                         │ Reporting Service│
                         └────────┬─────────┘
                                  │
                                  ▼
                         ┌──────────────────┐
                         │ JasperReports    │
                         └──────────────────┘
```

> A arquitetura poderá evoluir ao longo do curso conforme novos conceitos e componentes forem apresentados.

---

## 🛠️ Tecnologias utilizadas

### Backend

* ☕ **Java**
* 🌱 **Spring Boot**
* 🌐 **Spring Web**
* 🗄️ **Spring Data JPA**
* 🔐 **Spring Security** *(quando aplicável)*
* 📡 **REST APIs**
* 📨 **Apache Kafka**

### Mensageria

* **Apache Kafka**
* Kafka Producers
* Kafka Consumers
* Topics
* Eventos assíncronos
* Comunicação entre microsserviços

### Relatórios

* **JasperReports**
* JasperSoft Studio
* Templates `.jrxml`
* Geração de relatórios
* Exportação para PDF

### Infraestrutura

* 🐳 **Docker**
* Docker Compose
* Containers
* Redes entre serviços
* Configuração de ambientes

### Banco de dados

* Relational Database
* JPA / Hibernate
* Migrations *(quando aplicável)*

---

## 🎯 Objetivos do projeto

Ao longo do curso, você aprenderá a:

* Criar aplicações utilizando **Java e Spring Boot**
* Desenvolver APIs REST
* Estruturar uma aplicação utilizando **microsserviços**
* Separar responsabilidades entre diferentes serviços
* Trabalhar com comunicação síncrona e assíncrona
* Utilizar **Apache Kafka** para comunicação baseada em eventos
* Criar Producers e Consumers
* Trabalhar com persistência utilizando Spring Data JPA
* Gerar relatórios utilizando **JasperReports**
* Criar templates utilizando **JasperSoft Studio**
* Containerizar aplicações utilizando **Docker**
* Orquestrar múltiplos serviços utilizando **Docker Compose**
* Entender os desafios de uma arquitetura distribuída

---

## 📦 Estrutura do projeto

A estrutura do projeto será organizada de maneira semelhante a:

```text
shopping-microservices/
│
├── product-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
├── customer-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
├── order-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
├── reporting-service/
│   ├── src/
│   ├── reports/
│   ├── pom.xml
│   └── Dockerfile
│
├── docker-compose.yml
│
└── README.md
```

> A estrutura poderá sofrer alterações durante o desenvolvimento do projeto.

---

## 🔄 Comunicação entre os serviços

Um dos principais objetivos do projeto é demonstrar como diferentes microsserviços podem trabalhar em conjunto.

Para operações que exigem uma resposta imediata, poderemos utilizar comunicação síncrona através de APIs REST.

Para processos assíncronos, utilizaremos o **Apache Kafka**.

Exemplo:

```text
Order Service
      │
      │ OrderCreatedEvent
      ▼
   Kafka
      │
      ├───────────────┐
      ▼               ▼
Reporting Service   Other Service
```

Dessa maneira, os serviços podem reagir aos eventos sem depender diretamente uns dos outros.

---

## 📨 Exemplo de evento

Um evento publicado pelo serviço de pedidos poderá seguir uma estrutura semelhante a:

```json
{
  "event": "ORDER_CREATED",
  "orderId": 12345,
  "customerId": 100,
  "total": 299.90
}
```

O Kafka será responsável por transportar esse evento entre os diferentes serviços interessados.

---

## 📊 Geração de relatórios

O projeto também contará com um serviço dedicado à geração de relatórios.

Utilizaremos o **JasperReports** juntamente com o **JasperSoft Studio** para criação dos templates.

Exemplo de fluxo:

```text
Pedido realizado
       │
       ▼
Order Service
       │
       ▼
     Kafka
       │
       ▼
Reporting Service
       │
       ▼
JasperReports
       │
       ▼
     PDF
```

Os relatórios poderão utilizar informações provenientes dos eventos e dados persistidos pela aplicação.

---

## 🐳 Docker

Todos os principais componentes da aplicação serão executados utilizando containers.

Exemplo:

```text
┌──────────────────────────────────────────────┐
│                Docker                        │
│                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐   │
│  │ Product  │  │  Order   │  │ Customer │   │
│  │ Service  │  │ Service  │  │ Service  │   │
│  └──────────┘  └──────────┘  └──────────┘   │
│                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐   │
│  │  Kafka   │  │ Database │  │ Reporting│   │
│  │          │  │          │  │ Service  │   │
│  └──────────┘  └──────────┘  └──────────┘   │
│                                              │
└──────────────────────────────────────────────┘
```

O `docker-compose.yml` será utilizado para facilitar a inicialização de todo o ambiente.

---

## 🚀 Como executar o projeto

### Pré-requisitos

Antes de iniciar, certifique-se de possuir:

* Java
* Maven
* Docker
* Docker Compose
* Git

Verifique as versões instaladas:

```bash
java -version
mvn -version
docker --version
docker compose version
```

### Clone o projeto

```bash
git clone <URL_DO_REPOSITORIO>
```

Entre no diretório:

```bash
cd shopping-microservices
```

### Subindo a infraestrutura

```bash
docker compose up -d
```

Para verificar os containers:

```bash
docker compose ps
```

---

## 🧪 Testando a aplicação

As APIs poderão ser testadas utilizando ferramentas como:

* Postman
* Insomnia
* curl

Exemplo:

```bash
curl http://localhost:8080/api/orders
```

---

## 🗺️ Conteúdo do curso

O projeto será desenvolvido progressivamente, acompanhando a evolução do curso.

### Módulo 1 — Fundamentos

* Introdução ao projeto
* Arquitetura
* Modelagem do domínio
* Configuração do ambiente

### Módulo 2 — Spring Boot

* Criação das aplicações
* Controllers
* Services
* Repositories
* DTOs
* Persistência
* APIs REST

### Módulo 3 — Microsserviços

* Separação dos serviços
* Responsabilidades
* Comunicação entre serviços
* Configuração dos ambientes

### Módulo 4 — Apache Kafka

* Introdução ao Kafka
* Topics
* Producers
* Consumers
* Eventos
* Comunicação assíncrona
* Integração com Spring Boot

### Módulo 5 — JasperReports

* Introdução ao JasperReports
* JasperSoft Studio
* Criação de templates
* Arquivos `.jrxml`
* Parâmetros
* Geração de PDF
* Integração com Spring Boot

### Módulo 6 — Docker

* Conceitos de containers
* Dockerfile
* Docker Compose
* Networks
* Volumes
* Configuração dos serviços

### Módulo 7 — Integração

* Integração dos microsserviços
* Eventos Kafka
* Processamento de pedidos
* Geração de relatórios
* Execução completa da aplicação

---

## 📈 O que você terá ao final

Ao finalizar o projeto, você terá desenvolvido uma aplicação baseada em uma arquitetura moderna de microsserviços, utilizando:

```text
Java
  │
  └── Spring Boot
        │
        ├── REST APIs
        │
        ├── JPA / Hibernate
        │
        ├── Microservices
        │
        └── Apache Kafka
                │
                ▼
        Event-driven architecture
                │
                ▼
        Reporting Service
                │
                ▼
          JasperReports
                │
                ▼
               PDF

        Tudo executando
             com
           Docker
```

---

## 💡 Conceitos abordados

Além das tecnologias, o projeto busca apresentar conceitos importantes encontrados no desenvolvimento de sistemas modernos:

* Microservices Architecture
* RESTful APIs
* Event-Driven Architecture
* Asynchronous Communication
* Message Brokers
* Data Persistence
* Separation of Concerns
* Service Independence
* Containerization
* Report Generation
* Distributed Systems

---

## 📌 Status do projeto

🚧 **Em desenvolvimento**

Este projeto está sendo desenvolvido como parte do curso e será evoluído progressivamente durante as aulas.

---

## 👨‍💻 Sobre o curso

Este projeto foi desenvolvido com o objetivo de proporcionar uma experiência prática na construção de uma aplicação backend utilizando tecnologias amplamente utilizadas no mercado.

A proposta é sair de uma aplicação Spring Boot tradicional e evoluir gradualmente para uma **arquitetura distribuída baseada em microsserviços e eventos**.

---

## ⭐ Contribuição

Este projeto possui finalidade educacional.

Sugestões, melhorias e contribuições são bem-vindas.

---

## 📄 Licença

Este projeto é destinado para fins educacionais.
