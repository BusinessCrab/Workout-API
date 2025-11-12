# WorkoutAPI
## Overview
The Workout Tracker API is a backend application designed to help users manage their fitness routines. It allows users to create and manage workout plans, schedule workouts, track completed exercises, and generate reports on past workouts. The API is built with Spring Boot, uses JWT-based authentication for security, and provides a fully documented OpenAPI (Swagger) interface for easy interaction.
## Features
- <b>User Authentication</b>: Sign up and log in with JWT-based authentication.

- <b>Workout Plans</b>: Create, update, and manage workout plans (accessible only by the creator or admins).

- <b>Workout Schedules</b>: Schedule workouts and mark them as completed.

- <b>Exercises</b>: Manage a list of exercises to include in workout plans.

- <b>Reports</b>: Generate reports on past workouts, with optional date range filtering.

- <b>Role-Based Access</b>: Admins can manage all users; regular users can only manage their own data.

- <b>API Documentation</b>: Fully documented with Swagger UI (accessible at /swagger-ui/index.html).
## Tech Stack
- <b>Java</b>: 25 (or compatible version)

- <b>Spring Boot</b>: 3.2.x

- <b>Spring Security</b>: For authentication and authorization

- <b>JWT</b>: For secure token-based authentication

- <b>Spring Data JPA</b>: For database interactions

- <b>Postgres Database</b>: Popular database for java-projects

- <b>Maven</b>: Dependency management and build tool

- <b>Swagger (Springdoc)</b>: API documentation
## Prerequisites
Before running the project, ensure you have the following installed:
- Java 25 (or a compatible version)

- Maven (for dependency management and building the project)

- A code editor or IDE (e.g., IntelliJ IDEA, Eclipse, VS Code)
## API Endpoints
Here’s a quick overview of the main API endpoints:

### Authentication:

- POST /api/auth/signup: Register a new user.

- POST /api/auth/login: Log in and receive a JWT token.

### Users (Admin only):

- GET /api/users: List all users.

- GET /api/users/{id}: Get a user by ID.

### Exercises:

- GET /api/exercises: List all exercises.

- POST /api/exercises: Create a new exercise (authenticated users only).

### Workout Plans:

- GET /api/workout-plans: List the authenticated user’s workout plans.

- POST /api/workout-plans: Create a new workout plan (authenticated users only).

### Workout Schedules:

- GET /api/workout-schedules: List the authenticated user’s schedules.

- POST /api/workout-schedules: Schedule a workout (authenticated users only).

### Reports:

- GET /api/reports/past-workouts: Generate a report of past workouts (authenticated users only).

### Authentication

- Most endpoints require authentication. To authenticate: Sign up or log in using /api/auth/signup or /api/auth/login.
