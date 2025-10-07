# Arquitetura do Projeto

Este projeto utiliza a **Arquitetura Hexagonal** (também conhecida como Arquitetura de Portas e Adaptadores). Esta arquitetura promove um baixo acoplamento entre a lógica de negócio e as tecnologias e frameworks utilizados, facilitando a manutenção, testabilidade e evolução do sistema.

A estrutura do projeto é dividida em três pacotes principais:

## 1. `domain`

O pacote `domain` é o coração da aplicação. Ele contém a lógica de negócio pura e as entidades do domínio.

*   **Responsabilidades:**
    *   Definir os modelos de negócio (entidades, agregados, objetos de valor).
    *   Implementar as regras de negócio e a lógica central da aplicação.
    *   Não possuir dependências de outros pacotes da aplicação (como `application` ou `infrastructure`).

### Pacotes Internos:

*   `models`: Contém as entidades de domínio, objetos de valor e agregados. É aqui que a lógica de negócio e as regras de domínio são definidas.
*   `enums`: Contém as enumerações utilizadas no domínio da aplicação.
*   `exception`: Contém as exceções personalizadas do domínio, que representam erros de negócio.

## 2. `application`

O pacote `application` atua como um orquestrador. Ele define os casos de uso da aplicação e coordena a interação entre o mundo exterior e o `domain`.

*   **Responsabilidades:**
    *   Definir as "portas" de entrada da aplicação (use cases, serviços de aplicação).
    *   Orquestrar a execução dos casos de uso, utilizando o `domain` para executar a lógica de negócio.
    *   Definir as "portas" de saída (interfaces) que serão implementadas pelo pacote `infrastructure` para interagir com tecnologias externas (banco de dados, serviços de mensageria, etc.).
    *   Depende do `domain`, mas não do `infrastructure`.

### Pacotes Internos:

*   `ports`: Contém as interfaces (portas) para interagir com a aplicação.
    *   `in`: Define as portas de entrada, que são a API pública da aplicação para os casos de uso.
        *   `service`: Contém as interfaces para os serviços da aplicação (casos de uso).
    *   `out`: Define as portas de saída, que são as interfaces para serviços externos que a aplicação necessita.
        *   `repository`: Contém as interfaces para a persistência de dados, abstraindo a implementação do banco de dados.

## 3. `infrastructure`

O pacote `infrastructure` é a camada mais externa da arquitetura. Ele contém as implementações concretas das tecnologias e frameworks utilizados no projeto.

*   **Responsabilidades:**
    *   Implementar os "adaptadores" que conectam a aplicação ao mundo exterior.
    *   Implementar as interfaces (portas de saída) definidas no pacote `application` para acesso a banco de dados, serviços externos, etc.
    *   Conter a configuração de frameworks (como o Spring), controllers de API, repositórios de banco de dados, etc.
    *   Depende do `application` para implementar suas interfaces.

### Pacotes Internos:

*   `controller`: Implementa os adaptadores de entrada, como os controllers REST, que lidam com as requisições dos usuários e chamam os serviços da aplicação.
*   `repository`: Implementa os adaptadores de saída para a persistência de dados, fornecendo implementações concretas das interfaces de repositório definidas na camada de `application`.
*   `entity`: Contém as entidades JPA para o mapeamento do banco de dados.
*   `service`: Contém implementações de serviços específicos da camada de infraestrutura, como a interação com APIs externas.
*   `config`: Contém as classes de configuração da aplicação, como a configuração do Spring.
*   `security`: Contém classes relacionadas à segurança, como autenticação e autorização.
*   `dto`: Contém os Data Transfer Objects (DTOs) utilizados para a comunicação entre as camadas, especialmente para a transferência de dados de e para os controllers.
*   `mapper`: Contém classes responsáveis por mapear objetos de uma camada para outra (ex: DTO para Entidade de Domínio, ou Entidade de Domínio para Entidade JPA).
*   `handler`: Contém handlers globais, como manipuladores de exceção (`@ControllerAdvice`), para tratar erros de forma centralizada.
