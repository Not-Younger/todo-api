
# Todo API

This is a solution for the Blog API project on 
[https://roadmap.sh/projects/todo-list-api](https://roadmap.sh/projects/todo-list-api)

### Requirements:
- Java 17

### How to use:
This is a Spring Boot Application with a maven wrapper
so you can easily run it with **./mvnw spring-boot:run** 

#### Endpoints (Assuming localhost:8080):
- **POST localhost:8080/register** send a JSON object with a name, email, and password and get a JWT response
- **POST localhost:8080/login** send a JSON object with a email and password and get a JWT response
- **POST localhost:8080/todos** send a JSON object with a title and description with a JWT auth header to create a todo
- **PUT localhost:8080/todos/{id}** send a JSON object with a title and description with a JWT auth header to update a todo
- **GET localhost:8080/todos?page={pageCount}&limit={todosPerPage}** to get a list of todos affiliated with your account
- **GET localhost:8080/todos/{id}** to get a specific todo affiliated with your account
- **DELETE localhost:8080/todos/{id}** to delete a todo affiliated with your account
