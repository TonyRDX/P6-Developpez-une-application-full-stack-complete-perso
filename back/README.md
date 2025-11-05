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