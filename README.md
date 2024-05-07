# SE Car Rental Service
FH Campus Wien - SDE - Service Engineering - Final Project Part 2

The SE Car Rental Service is a Spring Boot application designed to handle user registration and authentication processes,
booking requests and car requests accessible via a web frontend.

## Key Features
- **MariaDB Integration:** Utilizes MariaDB for storing and retrieving data regarding users, cars and orders.
- **JWT-Based Authentication:** Implements JWT to ensure secure transmission of authentication credentials.
- **Exception Handling:** Custom exception handling for scenarios like "Email Already in Use."
- **Currency Converter Integration:** Utilizes a SOAP-based currency converter web service to offer currency conversion during and after the booking process
## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them:

- Java JDK 17 or later
- Maven 4.0.0
- MariaDB

### Installing

A step-by-step series of examples that tell you how to get a development environment running:

1. Ensure that MongoDB is running and accessible.
2. Clone the repository:
```bash
git clone https://github.com/Lukasjai/SE_Projekt_Car_Rental_Service.git
```
2. Navigate to the project directory:
```bash
cd SE_Projekt_Car_Rental_Service
```
3. Build the project with Maven:
```bash
mvn clean install
```
4. Run the application:
```bash
mvn spring-boot:run
```
The service will start on port 8080 as specified in the application.properties file.

### Usage
Once the application is running, you can access the Car Rental Service at:
```bash
http://localhost:8080/
```
## Environmental Variables
To run this application, you will need to add the following environment variables to your .env file:

`MARIA_DB_CONNECTION_URI`, `MARIA_DB_USERNAME`, `MARIA_DB_PASSWORD`, `JWT_SECRET_KEY`, `JWT_EXPIRATION_TIME`, `SERVER_PORT`

## Currency Converter Integration
To use this application with the attached currency converter webservice, you will need to run the currency converter on your machine:

```bash
git clone https://git.fh-campuswien.ac.at/c2010475040/se-currency-converter.git
```
Then follow the currency converter's README to run it locally in a docker container (required for 
the Car Rental Service to function properly).