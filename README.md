# JobAggregator

JobAggregator is a Spring Boot application for managing users and (future) job-related data.  
It uses stateless JWT-based authentication and BCrypt-hashed passwords.

## Features

- User registration and login
- Stateless authentication using JSON Web Tokens (JWT)
- Password hashing with `BCryptPasswordEncoder`
- Protected REST endpoints (only `/register` and `/login` are public)
- Spring Security configuration with a custom JWT filter

## Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT (io.jsonwebtoken)
- Maven

## Getting Started

1. **Clone the repository**

   ```bash
   git clone <your-repo-url>
   cd JobAggregator
