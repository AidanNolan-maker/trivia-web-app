# Trivia Web App

A full-stack trivia application built with **Java Spring Boot** and **React** that allows users to register, log in, play trivia games powered by the Open Trivia Database API, track their statistics, and compete on a global leaderboard.

This project is being developed as a portfolio project to demonstrate full-stack software engineering skills including REST API development, authentication, database design, external API integration, and modern frontend development.

---

## Features

### User Authentication

- User registration
- Secure password hashing with BCrypt
- JWT authentication (coming soon)
- User login/logout
- Protected API endpoints

### Trivia Gameplay

- Questions retrieved from the Open Trivia Database API
- Select category
- Select difficulty
- Multiple Choice and True/False modes
- Score tracking
- Game history

### User Statistics

- Games played
- Highest score
- Average score
- Correct answer percentage
- Favorite category

### Leaderboard

- Top players
- Highest scores
- Ranking system

### Additional Features

- Responsive React frontend
- RESTful Spring Boot backend
- Persistent H2 database
- Global exception handling
- Validation with informative error responses
- MapStruct entity mapping

---

## Technologies Used

### Backend

- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate
- H2 Database
- Maven
- MapStruct
- Lombok
- BCrypt Password Encoder
- JWT Authentication (coming soon)

### Frontend

- React
- TypeScript
- React Router
- Axios
- TanStack Query
- React Hook Form
- Bootstrap

---

## Project Structure

```
trivia-web-app
│
├── backend
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   ├── dto
│   ├── mapper
│   ├── security
│   ├── config
│   ├── exception
│   └── enums
│
├── frontend
│   ├── components
│   ├── pages
│   ├── services
│   ├── hooks
│   ├── types
│   └── utils
│
└── README.md
```

---

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/trivia-web-app.git
```

### Backend

```bash
cd backend
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

---

## API

Current endpoints:

```
POST /api/auth/register
```

Upcoming endpoints:

```
POST /api/auth/login

GET /api/users/me

GET /api/trivia/categories

POST /api/trivia/start

POST /api/trivia/answer

GET /api/stats/me

GET /api/leaderboard
```

---

## Future Improvements

- JWT Authentication
- Timed Trivia Mode
- Daily Challenge
- Achievements
- User Profiles
- Admin Dashboard
- Dark Mode
- Question Review
- Infinite Trivia Mode
- Avatar Support

---

## License

This project is licensed under the MIT License.