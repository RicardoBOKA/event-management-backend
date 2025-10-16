### README

# Event Management Backend

## Description
This project is a backend application for managing events, registrations, feedback, and users. It provides a RESTful API to perform CRUD operations and other functionalities related to event management. The application is built using Spring Boot.

## Features
- User Management: Create, update, delete, and retrieve users.
- Event Management: Create, update, delete, and retrieve events.
- Registration Management: Register users for events, cancel registrations, and check registrations.
- Feedback Management: Add, update, delete, and retrieve feedback for events.

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- Database Supabase
- Maven

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6.0 or higher

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/RicardoBOKA/event-management-backend.git
   cd event-management-backend
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Running Tests
To run the tests, use the following command:
```bash
mvn test
```

## API Documentation

### User Management

- **Create User**
  - **URL:** `/v1/users`
  - **Method:** `POST`
  - **Request Body:**
    ```json
    {
      "userName": "john_doe",
      "email": "john.doe@example.com",
      "password": "password123"
    }
    ```
  - **Response:**
    ```json
    {
      "userId": "uuid",
      "userName": "john_doe",
      "email": "john.doe@example.com"
    }
    ```

- **Update User**
  - **URL:** `/v1/users/{userId}`
  - **Method:** `PUT`
  - **Request Body:**
    ```json
    {
      "userName": "john_doe_updated"
    }
    ```

- **Delete User**
  - **URL:** `/v1/users/{userId}`
  - **Method:** `DELETE`

- **Get User by ID**
  - **URL:** `/v1/users/{userId}`
  - **Method:** `GET`
  - **Response:**
    ```json
    {
      "userId": "uuid",
      "userName": "john_doe",
      "email": "john.doe@example.com"
    }
    ```

- **Get All Users**
  - **URL:** `/v1/users`
  - **Method:** `GET`

- **Search Users by Username**
  - **URL:** `/v1/users/search/by-username`
  - **Method:** `GET`
  - **Parameters:** `userName`

- **Search User by Email**
  - **URL:** `/v1/users/search/by-email`
  - **Method:** `GET`
  - **Parameters:** `email`

- **Authenticate User**
  - **URL:** `/v1/users/authenticate`
  - **Method:** `POST`
  - **Request Body:**
    ```json
    {
      "email": "john.doe@example.com",
      "password": "password123"
    }
    ```

### Event Management

- **Create Event**
  - **URL:** `/v1/events`
  - **Method:** `POST`
  - **Request Body:**
    ```json
    {
      "eventName": "Sample Event",
      "createdDate": "2024-07-04T10:00:00",
      "startEvent": "2024-08-01T10:00:00",
      "endEvent": "2024-08-01T12:00:00",
      "location": "Paris",
      "description": "This is a sample event.",
      "organizerId": "uuid"
    }
    ```

- **Update Event**
  - **URL:** `/v1/events/{eventId}`
  - **Method:** `PUT`
  - **Request Body:**
    ```json
    {
      "eventName": "Updated Event",
      "createdDate": "2024-07-04T10:00:00",
      "startEvent": "2024-08-01T10:00:00",
      "endEvent": "2024-08-01T12:00:00",
      "location": "Paris",
      "description": "This is an updated event.",
      "organizerId": "uuid"
    }
    ```

- **Delete Event**
  - **URL:** `/v1/events/{eventId}`
  - **Method:** `DELETE`

- **Get Event by ID**
  - **URL:** `/v1/events/{eventId}`
  - **Method:** `GET`
  - **Response:**
    ```json
    {
      "eventId": "uuid",
      "eventName": "Sample Event",
      "createdDate": "2024-07-04T10:00:00",
      "startEvent": "2024-08-01T10:00:00",
      "endEvent": "2024-08-01T12:00:00",
      "location": "Paris",
      "description": "This is a sample event.",
      "organizer": {
        "userId": "uuid",
        "userName": "john_doe",
        "email": "john.doe@example.com"
      }
    }
    ```

- **Get All Events**
  - **URL:** `/v1/events`
  - **Method:** `GET`

- **Get Events by Organizer**
  - **URL:** `/v1/events/organizer/{organizerId}`
  - **Method:** `GET`

- **Search Events**
  - **URL:** `/v1/events/search`
  - **Method:** `GET`
  - **Parameters:** `startDate`, `endDate`

### Registration Management

- **Create Registration**
  - **URL:** `/v1/registrations`
  - **Method:** `POST`
  - **Request Body:**
    ```json
    {
      "registrationUserId": "uuid",
      "registrationEventId": "uuid"
    }
    ```

- **Cancel Registration**
  - **URL:** `/v1/registrations/{registrationId}`
  - **Method:** `DELETE`

- **Get Registration by ID**
  - **URL:** `/v1/registrations/{registrationId}`
  - **Method:** `GET`
  - **Response:**
    ```json
    {
      "registrationId": "uuid",
      "userId": "uuid",
      "eventId": "uuid",
      "registrationDate": "2024-07-04T10:00:00"
    }
    ```

- **Get Registrations by Event ID**
  - **URL:** `/v1/registrations/event/{eventId}`
  - **Method:** `GET`

- **Get Registrations by User ID**
  - **URL:** `/v1/registrations/user/{userId}`
  - **Method:** `GET`

- **Check if User is Registered**
  - **URL:** `/v1/registrations/check`
  - **Method:** `GET`
  - **Parameters:** `userId`, `eventId`

### Feedback Management

- **Add Feedback**
  - **URL:** `/v1/feedback`
  - **Method:** `POST`
  - **Request Body:**
    ```json
    {
      "eventId": "uuid",
      "userId": "uuid",
      "comment": "This is a feedback comment.",
      "rating": 5
    }
    ```

- **Get Feedback by ID**
  - **URL:** `/v1/feedback/{feedbackId}`
  - **Method:** `GET`
  - **Response:**
    ```json
    {
      "feedbackId": "uuid",
      "eventId": "uuid",
      "userId": "uuid",
      "comment": "This is a feedback comment.",
      "rating": 5
    }
    ```

- **Get Feedbacks by Event ID**
  - **URL:** `/v1/feedback/event/{eventId}`
  - **Method:** `GET`

- **Get Feedbacks by User ID**
  - **URL:** `/v1/feedback/user/{userId}`
  - **Method:** `GET`

- **Delete Feedback**
  - **URL:** `/v1/feedback/{feedbackId}`
  - **Method:** `DELETE`

## Contributing
Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

---

This README provides a comprehensive overview of the Event Management Backend project, detailing its features, installation instructions, API documentation, and other relevant information.
