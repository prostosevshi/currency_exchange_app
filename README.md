## Currency Exchange Web App

#### Currency Exchange Web App is a simple Java web application that provides CRUD operations and REST endpoints to work with currencies and their exchange rates.
#### The app connects to a relational database and exposes servlets for managing and retrieving currency data.

#### Features
    Manage a list of currencies (Currency) and their exchange rates (ExchangeRate) via the database.
    REST-like servlets:
        /currencies — manage currencies.
        /exchangeRates — manage exchange rates.
        /exchange — calculate exchanged amount between two currencies.
    Database connection pooling for efficiency.
    Separation of concerns:
        Model layer for entities.
        DAO layer for database access.
        Service layer for business logic.
        Servlet layer as the web interface.

#### Tech Stack
  - Language: Java 21
  - Web server: Tomcat
  - JDBC: Database access
  - Database: PostgreSQL
  - Build tool: Gradle
