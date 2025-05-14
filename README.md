# Todolist

A simple RESTful API for managing personal tasks, built with Spring Boot.

## Features

- User registration with unique username and secure password hashing (BCrypt)
- Basic authentication for all task endpoints
- CRUD operations for tasks (create, list, update)
- Task validation (date checks, ownership)
- In-memory H2 database for development/testing

## Project Structure

```
src/
  main/
    java/
      br/com/caiquebrener/todolist/
        controller/
          task/TaskController.java      # Task endpoints
          user/UserController.java      # User registration endpoint
        model/
          task/TaskModel.java           # Task entity
          user/UserModel.java           # User entity
        repository/
          task/ITaskRepository.java     # Task JPA repository
          user/IUserRepository.java     # User JPA repository
        service/
          task/TaskService.java         # Task service interface
          task/TaskServiceImpl.java     # Task service implementation
          user/IUserService.java        # User service interface
          user/UserServiceImpl.java     # User service implementation
        utils/
          BeanUtilsHelper.java          # Utility for copying properties
          ErrorResponse.java            # Error response record
    resources/
      application.properties            # Spring Boot and DB configuration
```

## API Endpoints

### User

- `POST /users`
  - Registers a new user.
  - Body: `{ "username": "...", "name": "...", "password": "..." }`
  - Returns 201 Created or 400 if username exists.

### Tasks (Requires Basic Auth)

- `POST /tasks`
  - Creates a new task for the authenticated user.
- `GET /tasks`
  - Lists all tasks for the authenticated user.
- `PUT /tasks/{id}`
  - Updates a task (only if owned by the user).

## Authentication

- All `/tasks/*` endpoints require HTTP Basic Auth.
- Passwords are stored hashed with BCrypt.

## Database

- Uses H2 in-memory database.
- Console available at `/h2-console` (if enabled).

## Contribution

Pull requests are welcome. For major changes, please open an issue first.

## License

This project is for educational purposes.