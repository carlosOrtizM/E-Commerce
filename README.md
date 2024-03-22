# E-Commerce
YES. ANOTHER E-COMMERCE CRUD :)

Spring Boot,
PostgreSQL,
2024

3 Entities (&& corresponding DTOs) 
-Users
-Products
-Orders

Attempting a Hexagonal architecture.

For each Entity we´ve got:
JPA Repositories,
Controllers (RESTful!?),
Services

We´ve also got a Global Exception Handler.

Generic CRUD Interface that is implementented by all services.
Users has a dependency injection (instead of longer service) that validates username & password.

Also contains:
DB Model,
Data.sql (Tables Initializer)

Have a good day.

