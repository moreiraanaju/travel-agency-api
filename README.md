🚀 Travel API RESTful com Spring Boot

Este projeto é uma API RESTful desenvolvida em Java + Spring Boot, permitindo criar, visualizar, atualizar e deletar informações de destinos de viagem.

📘 Tecnologias usadas

Java 17

Spring Boot (Web, Data JPA)

Maven

H2 Database 

Postman (para testes)


📦 Como rodar o projeto
1. Clonar ou extrair o projeto

Se recebeu um .zip:

Extraia em qualquer pasta do seu computador.


2. Abrir no VS Code ou IntelliJ

No IntelliJ:

File → Open → selecione a pasta do projeto


No VS Code:

File → Open Folder → selecione o projeto


3. Rodar o projeto

No terminal:

mvn spring-boot:run


ou pela IDE usando o botão Run.

A API estará em:

http://localhost:8080


🛠️ Endpoints principais:

GET /travels
Lista todas as viagens.

GET /travels/{id}
Busca uma viagem pelo ID.

POST /travels
Cria uma nova viagem.
Exemplo de JSON:

{
  "destinationName": "Florianópolis",
  "date": "2026-08-20",
  "location": "Brasil",
  "score": 9.1,
  "ratingCount": 12,
  "hotels": true,
  "touristActivities": ["surf", "walking"]
}

PUT /travels/{id}
Atualiza uma viagem inteira.
→ Envie todos os campos da entidade.

PATCH /travels/{id}/rating
Atualiza apenas o rating do destino.
Exemplo:
{
  "rating": 8.5
}

DELETE /travels/{id}
Remove uma viagem.


📄 Licença
Este projeto é de uso acadêmico/livre.