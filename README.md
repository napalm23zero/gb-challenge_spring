# Desafio

## Como executar
Clone o Projeto para um diretório qualquer em seu computador

```bash
git clone https://github.com/napalm23zero/gb-challenge_spring.git
```

Depois navegue até o diretório criado
```bash
cd gb-challenge_spring
```

### Com Docker
É necessário instalar em sua maquina:
- [Docker](https://www.docker.com/) 

Execute o comando:
```bash
docker-compose up
```

### Sem Docker
É necessário instalar em sua maquina:
- [Postgres](https://www.postgresql.org/)
- [Apache Maven](https://maven.apache.org/)

Execute o comando:
```bash
mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=local"
```

## Como acessar
Uma vez que o projeo esteja rodando corretamente, abra seu navegador e acesso a url
```
http://localhost:8080/challenge/api/swagger-ui.html
```

Uma inteface do Swagger será exibida para que você possa navegar entre os endpoint criados

## Sobre o Desafio
Questão 1: Pode ser validada através do metodo POST /book em verde no Swagger
Questão 2: Pode ser validada através do metodo GET /book/{id} em azul o Swagger
Questão 3: Pode ser validada através do metodo POST /book/list/kotlin em verde no Swagger

P.S.: Questão 3: As informações dos livros encontradas na página são persistidas em banco. Por isso a escolha do método POST.

## Duvidas
- skype: napalm23zero
- email: rodrigodantas.91@gmail.com