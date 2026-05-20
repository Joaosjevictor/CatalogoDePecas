# ⚙️ AutoCore ERP - Catálogo de Autopeças de Alta Performance

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0+-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Riak KV](https://img.shields.io/badge/Riak_KV-NoSQL-darkred?style=for-the-badge)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap_5-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)

Um sistema de consulta de aplicações de autopeças projetado para resolver gargalos reais de lentidão no balcão de vendas, substituindo bancos de dados relacionais tradicionais por uma arquitetura NoSQL de altíssima disponibilidade.

Projeto desenvolvido para a disciplina de Banco de Dados do curso de Análise e Desenvolvimento de Sistemas.

---

## 🎯 O Problema (O Cenário Real)

No dia a dia de uma oficina mecânica ou loja de autopeças, o tempo de resposta no balcão é crítico. Sistemas tradicionais (SQL) sofrem com lentidão ao cruzar milhares de linhas em múltiplas tabelas (`Pecas`, `Montadoras`, `Veiculos`, `Anos`, etc.) apenas para descobrir se uma pastilha de freio serve em um veículo específico. 

Além disso, servidores centralizados representam um ponto único de falha: se o banco principal cair, as vendas da loja inteira param.

## 💡 A Solução (A Arquitetura)

Este projeto implementa uma mudança de paradigma utilizando o banco de dados NoSQL **Riak KV (Chave-Valor)** rodando em infraestrutura **Docker**. 

* **Performance Sub-milissegundo:** Ao invés de pesados `JOINs` em tabelas, a busca é feita diretamente pelo código da peça (SKU). O Riak KV extrai e devolve o documento JSON completo com todas as aplicações instantaneamente.
* **Alta Disponibilidade (Masterless):** Configurado em um cluster local via Docker, o sistema garante que o banco de dados não tenha um "chefe central". Se um nó falhar, o outro assume imediatamente, garantindo que o atendimento não pare.
* **Tratamento de Dados:** Implementação de regras no Back-end que convertem todas as pesquisas para minúsculo, eliminando erros operacionais (*Case Sensitivity*) comuns na digitação com o *Caps Lock* ligado.

---

## 🛠️ Tecnologias Utilizadas

**Back-end:**
* **Java 17:** Linguagem principal do projeto.
* **Spring Boot (Spring Web):** Criação da API RESTful.
* **Lombok:** Otimização de código (Getters/Setters e Construtores).
* **Riak Client Java:** Driver oficial de comunicação com o banco NoSQL.

**Banco de Dados & Infraestrutura:**
* **Riak KV:** Banco de dados NoSQL orientado a Chave-Valor.
* **Docker & Docker Compose:** Containerização do cluster de banco de dados em 2 nós simultâneos.

**Front-end:**
* **HTML5, CSS3 & JavaScript Vanilla:** Consumo assíncrono da API (Fetch API) sem recarregamento da página.
* **Bootstrap 5 & Bootstrap Icons:** Criação de uma interface de balcão de loja limpa, responsiva e profissional.

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
* Ter o **Docker** e o **Docker Desktop** instalados e rodando.
* Ter o **Java 17** (JDK) instalado.
* Conexão com a internet para baixar as dependências do Maven.

### Passo 1: Subir o Banco de Dados (Riak KV)
Navegue até a raiz do projeto onde está o arquivo `docker-compose.yml` e execute:

    docker-compose up -d

*Aguarde cerca de 30 segundos para os nós do banco inicializarem completamente.*

### Passo 2: Executar a API Java
No terminal, execute o Maven Wrapper para iniciar o Spring Boot:

    # No Windows:
    .\mvnw spring-boot:run

    # No Linux/Mac:
    ./mvnw spring-boot:run

*(Nota: Ao iniciar, a classe `CargaInicialDados` injetará automaticamente um catálogo de peças reais no banco de dados).*

### Passo 3: Acessar a Interface
Com a API rodando na porta `8080`, basta abrir o arquivo `index.html` em qualquer navegador moderno. 
Busque por SKUs pré-cadastrados, como: `sp500`, `n1020` ou `ph3569`.

---

## 📡 Endpoints da API

A API foi construída seguindo o padrão REST.

| Método | Rota | Descrição | Retorno |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/pecas/{sku}` | Retorna o documento JSON da peça consultada. | `200 OK` ou `404 Not Found` |

**Exemplo de Resposta (GET `/api/pecas/sp500`):**

    {
      "sku": "sp500",
      "nomePeca": "Vela de Ignição Iridium",
      "marca": "Bosch",
      "categoria": "Ignição",
      "aplicacoes": [
        {
          "montadora": "Fiat",
          "veiculo": "Palio",
          "motor": "1.0 Fire",
          "ano": "2005-2010"
        }
      ]
    }

---

## 👨‍💻 Autor

**João Victor Q. de Barros** *Estudante de Análise e Desenvolvimento de Sistemas | Desenvolvedor Java | Experiência real em Gestão e Operações no Setor Automotivo.*
