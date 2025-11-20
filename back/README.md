mvn clean spring-boot:run

# Layered architecture

- Infrastructure - technical concerns
    - Controller
    - Service
    - Others I/O concerns
- Application - business use cases + tech setup with flux
    - Service
- Domain - fully isolated

TODO : 
- clear dependencies (I -> A -> D)
- default entity proxy throwing error
- example of Appli service self wrapping in a flux

Backlog : 
- dependency tool

# Architecture flow example

### Basic flow example :
API --> Controller --> InfraHandler --> UseCaseHandler --> Domain ↓
API <-- Controller <-- Persistence <--                       

### Detailed flow example

[Desc flow]
API --> Controller --> IMessageHandler<Msg, Response> (Infra: SetupHandler) 
--> UseCaseHandler --> IUoW 
[Asc flow]
UseCaseHandler --> UoW --> Repository --> Mapper
[Desc flow]
--> UseCaseHandler --> Domain
[Asc flow]
UseCaseHandler --> SetupHandler / UoW --> Controller --> API 