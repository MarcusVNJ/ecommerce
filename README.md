# E-commerce API - Desafio Técnico

Este projeto é uma API RESTful para um sistema de e-commerce, se tratando de um desafio técnico. A API gerencia produtos, pedidos e usuários, com autenticação baseada em JWT e diferentes perfis de acesso.

##  Arquitetura do Projeto

Para uma visão detalhada da arquitetura do projeto, consulte o documento [ARCHITECTURE.md](ARCHITECTURE.md).


## 🚀 Como Executar o Projeto

Siga os passos abaixo para configurar e executar a aplicação em seu ambiente local.

### Pré-requisitos

- **Java 21** instalado.
- **Docker** e **Docker Compose** instalados e em execução.

### 1. Clone o Repositório

```bash
git clone https://github.com/MarcusVNJ/ecommerce.git
cd ecommerce
```

### 2. Inicie o Banco de Dados com Docker

O projeto inclui um arquivo `docker-compose.yml` para subir uma instância do MySQL de forma rápida. Execute o comando a partir da raiz do projeto:

```bash
docker-compose -f src/main/resources/docker-compose.yml up -d
```

> **Alternativa:** Caso prefira usar uma instância própria do MySQL, certifique-se de criar um banco de dados chamado `ecommerce_db` e atualizar as credenciais (usuário e senha) no arquivo `src/main/resources/application.yml`.

### 3. Execute a Aplicação

Você pode executar a aplicação usando o Gradle Wrapper incluído:

```bash
./gradlew bootRun
```

A API estará disponível em `http://localhost:8080`.

## DUMP

Não é necessário executar nenhum script de dump do banco de dados. O projeto utiliza a classe `DatabaseSeeder.java` para popular o banco com dados iniciais sempre que a aplicação é iniciada. A aplicação apaga o banco toda vez que inicia o projeto `(drop-first: true)` devido ao fato de usar o algoritmo HMAC256, impedindo assim usar uma migração do liquibase para inserir estes dados. Caso queira pode apagar esta classe que o projeto iniciara normalmente sem ele, porem o banco ficara vazio ao iniciar.

Os seguintes usuários são criados por padrão:

- **Admin:**
  - **Email:** `admin@email.com`
  - **Perfil:** `ADMIN`
- **Usuários Comuns:**
  - `ana.silva@email.com`
  - `bruno.costa@email.com`
  - `carla.dias@email.com`
  - `daniel.faria@email.com`
  - `elisa.gomes@email.com`

**A senha para todos os usuários é `123456`**.

Para mais detalhes sobre os dados populados (produtos, pedidos, etc.), consulte o arquivo `DatabaseSeeder.java`.

## Endpoints da API e Autenticação

### Autenticação

Os únicos endpoints que **não** exigem um token de autenticação são:

- `POST /api/auth/login`
- `POST /api/auth/register`

Para todos os outros endpoints, é necessário enviar um token JWT no cabeçalho da requisição:

```
Authorization: <seu-token-jwt>
```

### Coleção do Postman

Para facilitar os testes, você pode usar a coleção do Postman que já contém todas as chamadas aos endpoints da API. Acesse e importe a coleção pelo link abaixo:

[**Acessar Coleção do Postman**](https://web.postman.co/workspace/My-Workspace~a7ede61a-c433-4ffb-8554-0b001f3da6c5/collection/14591018-d21aa2e5-5e83-4cdd-8048-a1ad768c5608?action=share&source=copy-link&creator=14591018)


### Melhorias

Adição dos testes. (Não fiz por falta de tempo mesmo 🥲).

Adicionar documentação via swagger.

Melhorar o tratamento de erros.