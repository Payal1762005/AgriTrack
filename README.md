# AgriTrack

AgriTrack is a Java 21 console-based agricultural inventory and order management system.

## Features

- Product management
    - Add products
    - View all products
    - Search products
    - Add stock
    - Remove stock
    - Low-stock reporting
- Farmer management
    - Add farmers
    - View farmers
    - Search farmers by name
    - Find farmers by village
- Order management
    - Create orders
    - View orders
    - Find orders by ID
    - View orders by farmer
    - View orders by product
- Reports
    - Low-stock products
    - Total inventory value
    - Total order count
    - Total order value
    - Order count by product

## Technologies

- Java 21
- Maven
- JUnit 5
- IntelliJ IDEA
- Git

## Architecture

The application follows a layered architecture:

```text
Main
 │
 ▼
CLI / Menus
 │
 ▼
Service Layer
 │
 ▼
Repository Interfaces
 │
 ▼
In-Memory Repository Implementations
 │
 ▼
HashMap
## How to Run

### Requirements
- Java 21
- Maven 3.8+

### Run the application

Clone the repository and navigate to the project directory:

```bash
mvn compile