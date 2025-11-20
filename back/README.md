mvn clean spring-boot:run

# Horizontal slice - tech-centric

- **Infrastructure** - technical concerns
    - Controller
    - Service
    - Others I/O concerns (DB, HTTP, messaging…)
- **Application** - use cases fully synchronous and without infra details
    - Use cases (messages + handlers)
- **Domain** - fully isolated

# Vertical slice - business-centric
There are currently 2 top-level modules in the app :
- **shared** : contains cross-cutting concerns, such as authentication or generic interfaces
- **core** : contains feature-based code, usually grouped around business use cases

Combining with the horizontal slice, the core module contains additional internal sub-structures : 
- **In core infrastructure**, infrastructure code is also structured by feature.
    - **Instead of spreading** controllers, DTOs, and mappers across separate global folders, each feature has its own infrastructure area.
    - **The controller defines** its interaction point, and all supporting infrastructure code (DTOs, handlers, mappers, etc.) is grouped beside it.
- **In core application**, most application use cases are implemented there. Each use case requires a message and a handler. 


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