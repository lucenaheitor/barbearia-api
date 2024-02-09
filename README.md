<h2>Barebearia Api - RestApi</h2>

<a href="https://www.oracle.com/java/" target="_blank"> <img align="left" alt="Java" height ="42px" src="https://raw.githubusercontent.com/rahul-jha98/github_readme_icons/main/language_and_tools/square/java/java.svg"> </a>
<a href="https://spring.io/" target="_blank"> <img align="left" alt="Spring Boot" height ="42px" src="https://raw.githubusercontent.com/rahul-jha98/github_readme_icons/main/language_and_tools/square/spring/spring.svg"> </a>
<a href="https://www.mysql.com/" target="_blank"> <img align="left" alt="MySQL" height ="42px" src="https://raw.githubusercontent.com/rahul-jha98/github_readme_icons/main/language_and_tools/square/mysql/mysql.svg"> </a>
<br><br>

## Installation

1. Clone the repository:

```bash
git@github.com:lucenaheitor/barbearia-api.git
```

2. Install dependencies with Maven

3. Install [MySQL](https://www.mysql.com)

## Usage

1. Start the application with Maven
2. The API will be accessible at http://localhost:8080


## API Endpoints
The API provides the following endpoints:

```markdown


GET /clientes -  Retrieve a list of all clients. (all authenticated users)

GET /barbeiro - Retrieve a list of all products. (all authenticated users)

POST /cliente - Register a new  client (ADMIN access required).

POST /barbeiro - Register a new barber (ADMIN access required).

POST /auth/login - Login into the App

POST /auth/register - Register a new user into the App
```

## Authentication
The API uses Spring Security for authentication control. The following roles are available:

```
USER -> Standard user role for logged-in users.
ADMIN -> Admin role for managing partners (registering new partners).
```
To access protected endpoints as an ADMIN user, provide the appropriate authentication credentials in the request header.

## Database
The project utilizes [MySQL](https://www.mysql.com) as the database. The necessary database migrations are managed using Flyway.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request to the repository.

When contributing to this project, please follow the existing code style, [commit conventions](https://www.conventionalcommits.org/en/v1.0.0/), and submit your changes in a separate branch.

