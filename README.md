## ERP PARA PEQUENAS E MÉDIAS EMPRESAS

<br>

---

<br>

### BIBLIOTECAS UTILIZADAS

<br>

* WEB FRAMEWORK - **Ktor**
* SECURITY -  **Argon2** -- **Bouncy Castle**
* DATABASE - **HikariCP** - **Exposed** -- **Postgres**
* DEPENDENCY INJECTION - **Koin**
* LOG - **Klogging**
* TEST - **Mockk** -- **Kotest** -- **Detekt**
* DOCUMENTATION - **Swagger** -- **OpenAPI**
* ARTIFACTORY - **Space Jetbrains**
* GIT REPOSITORY - **Space Jetbrains**

<br>

---

<br>

### ESTRUTURA DO PROJETO

<p style="text-align:justify">
A estrutura de pastas do projeto segue algo semelhente no que o .Net faz com *ARQUITETURA LIMPA*. Onde existe a separação externa de configurações,
e a divisão de camadas entre INFRAESTRUTURA, DOMÍNIO E APRESENTAÇÃO (podendo haver pequenas mudanças). Esse tipo de abordagem facilita a separação do
código de acordo com a camada correspondente. Mas adiciona complexidade ao projeto.
</p>

<br>

* CONFIGURATIONS

      Local onde se encontra as configurações de todo o projeto.

  * COMMONS

        Configurações comuns aos domínios da aplicação, nas camadas de INFRAESTRUTURA (infa) DOMÍNIO (core) e APRESENTAÇÃO (app).

  * DATABASE

        Configuração inicial do banco de dados, como criação dos schemas e tabelas e configuração do PostgreSQL

  * HOCON

        Singleton para ler o arquivo application.conf

  * SECURITY

        Configurações de seguraça, usando BouncyCaste e Argon2

  * SERIALIZERS

        Singletons para seriliação de tipos especiais para o Kotlinx

  * VALIDATIONS

        Validações de input da request, usado nos models

<br>

* DOMAINS

      Encontra-se os domínios da aplicação, de acordo com o modelo de negócio. Dentro de cada dominío:

  * APP

        Parte mais externa do arquitetura. Aqui encontram-se os controllers, que recebem as requisições, os models (ou DTOs) que são representações dos
        objetos para cada requisisção e os services, que contém as regras para cada requisição

  * CORE

        Parte mais interna da arquitetura, que contém as entidades que representas os modelos de cada domínio, e as interfaces dos repositórios

  * INFRA

        A infra divide-se em duas partes. Data, onde encontra-se as configurações das tabelas do banco de dados, os SQL e as implementações do
        repositório. E IoC, onde está as configurações da Inversão de Controle (usando Koin)

<br>

* PLUGINS

      Configurações do framework

<br>

----

<br>

### CONFIGURAÇÃO DO BUILD

<p style="text-align:justify">
O projeto possui algumas tasks que é aconselhável fazer para garantir uma melhor qualidade do código. O projeto está como target a versão 21 da JVM (
projeto usando o MS OpenJDK https://www.microsoft.com/openjdk)
</p>

* gradle test

      executa os testes do projeto

* gradle detekt

      executa a varredura do projeto procurando por erros de acordo com as convenções pré estabelecidas sobre o kotlin. A task usa as configurações padrões. Caso você deseja criar suas próprias configurações, execute o comando gralde detektGenerateConfig onde irá gerar o arquivo **detekt.yml**. Coloque onde achar melhor (no projeto está em _src/main/resources/verification/detekt.yml_) e faça as alterações que julgar necessária (esse arquivo do projeto já contém algumas alterações)


* gradle spotBugsMain

      executa varredura de segurança baseado no antigo SpyBugs. Tem funcionamento melhor com o Java que o Kotlin. Ao terminar o scanner, gera um arquivo XML ou HTML (esse está baseado em XML) que pode ser lido por alguma plataforma de CDCI


* gradle publish

      publica o artefato em um repositório designado, no caso dessa aplicação no SPACE ARTIFACTOTRY da Jetbrains, mas pode ser no Github, JFrog, etc

<br>

----

<br>

### CONFIGURAÇÃO DE CHAVES E CERTIFICADOS

Para o correto funcionamento da bibliote Bouncy Castle e Argon2 necessita que seja geradas chaves e certificados. Os nomes gerados podem ser alterados
de acordo com as necessidades do projeto.

<br>

```bash
openssl req -newkey rsa:4096 -x509 -sha512 -days 3650 -out crypto.pem -keyout privatekey.pem
```

<br>

P12 KEY

```bash
openssl pkcs12 -export -out crypto.p12 -inkey privatekey.pem -in crypto.pem -name crypto
```

<br>

PRIVATE KEY

```bash
openssl genpkey -algorithm RSA -pkeyopt rsa_keygen_bits:2048 -aes-256-cbc -out development_erp_private.key
```

<br>

PUBLIC KEY

```bash
openssl pkey -in development_erp_private.key -pubout -out development_erp_public.key 
```

<br>

-----

<br>

### DOCKER

<p style="text-align:justify">
O projeto depende de banco de dados. De forma facultativa (porém fortemente aconselhável), pode-se ter ferramentas de CD/CI (o projeto usa o TeamCity 
da Jetbrains)
</p>

* Infrastructure
  * Docker
    * ERP &nbsp;&nbsp;&nbsp;  `Banco de dados PostgreSQL`
    * CDCI &nbsp;&nbsp; `TeamCity`
  

<br>

-----

<br>

### FLUXO DA APLICAÇÃO

TODO

<br>

-----

<br>

### SWAGGER E OPENAPI

TODO

<br>

----

<br>

### INICIAR A APLICAÇÃO

Crie um variável de ambient chamada KTOR_ENV e dê o valor development
altere os TODO dos arquivos de docker, application.conf e gradle.properties
