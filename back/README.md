# Run the application

## Installation
Use JDK 17 to run the project. Dependencies should be automatically managed when running or building.
Spring Boot 3.3 is used in the project.

## Development server
`mvn clean spring-boot:run`
Automatically apply most of code changes.
/!\ Database may drop at each code change or server start. Please check Flyway config if does not match your needs.

## Postman
Postman ressources may be found at the root project, with test scenarion example.
It has been used to smoke test + seed database, to detect early the common API regression.

## Build
`mvn clean compile`


# Backend architecture

## Horizontal slice - tech-centric

- **Infrastructure** - technical concerns
    - Controller
    - Service
    - Others I/O concerns (DB, HTTP, messaging…)
- **Application** - use cases fully synchronous and infrastructure-agnostic
    - Use cases (messages + handlers)
- **Domain** - fully isolated

## Vertical slice - business-centric
There are currently 2 top-level modules in the app :
- **shared** : contains cross-cutting concerns, such as authentication or generic interfaces
- **core** : contains feature-based code, usually grouped around business use cases

Combining with the horizontal slice, the core module contains additional internal sub-structures : 
- **In core infrastructure layer**, code is also structured by feature.
    - **Instead of scattering** controllers, DTOs, and mappers across separate global folders, each feature has its own infrastructure area.
    - **The controller defines** its interaction point, and all supporting infrastructure code (DTOs, handlers, mappers, etc.) is grouped beside it.
- **In core application layer**, most application use cases are implemented there. Each use case requires a message and a handler. 

## Feature Maturity Level

These levels describe how much architectural structure should be applied when building a feature.

### Level 1 - Minimal (prototype, POC)
Controller --> Service --> Persistence
Purpose: quick implementation, minimal business rules.

### Level 2 - Structured (basic reads and simple flows)
Controller --> Handler --> Persistence, Mapper, and other infrastructure components
Purpose: better isolation of application logic and improved modularity.

### Level 3 - Complete (required for production with critical business rules)
Controller --> Handler / Unit of Work --> Application --> Domain
Purpose: enforce business invariants, improve testability, scalability, and maintainability.


## Architecture diagram

![texte alternatif](docs/back%20diagram%20architecture.png)


# Misc

Tech backlog : 
- A dependency tool to enforce or analyze them
- List all features and enhance implementation on some of them 
    (prefer a living documentation style)
- Consider smoke test and database seed inside project instead of Postman