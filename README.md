# Attendance System API
<div style="display: flex; flex-direction: row; align-content: center; align-items: center; text-align: center; justify-content: center;">

![Dependabot](https://img.shields.io/badge/dependabot-025E8C?style=for-the-badge&logo=dependabot&logoColor=white)

![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
</div>

## Overview
This project is an Attendance System API built using Spring Boot, WebSocket, and JWT Authentication. The system provides a robust solution for tracking and managing attendance records, with support for real-time updates through WebSocket communication.

## Features

- **WebSocket Integration:** Real-time communication for instant updates on attendance events.
- **JWT Authentication:** Secure API access with JSON Web Token authentication.
- **Various Controllers:** The API includes the following controllers for managing different aspects of the system:

    - **User Controller:** Manages user-related functionalities such as creating, updating, and retrieving user information. Handles user roles and permissions.

    - **Authentication:** Provides endpoints for user authentication, login, and sign-up. Implements secure authentication using JWT tokens.

    - **Config:** Allows dynamic configuration changes to the server, enabling administrators to modify system settings on-the-fly.

    - **Grade-level:** Facilitates CRUD operations for grade levels. Additionally, provides search functionalities for grade levels based on criteria such as name, academic year, and more.

    - **Guardian:** Manages information about students' guardians. Supports CRUD operations for creating, updating, and retrieving guardian details. Enables searching for guardians based on names, contact information, and relationships.

    - **Student:** Handles students' information comprehensively with CRUD operations. Extensive search functionalities include filtering by name, grade level, section, and other criteria.

    - **Section:** Manages information about sections, including CRUD operations for creating, updating, and retrieving section details. Supports searching for sections based on name, grade level, and capacity.

    - **Strand:** Provides CRUD operations for academic strands, allowing administrators to create, update, and retrieve strand information. Supports searching for strands by name and associated subjects.

    - **Teacher:** Manages teacher information, including full name, contact details, and subject assignments. Implements CRUD operations and provides search capabilities based on teacher name, subject, and other parameters.

    - **Subjects:** Manages subjects such as Mathematics, Science, and more. Enables CRUD operations for creating, updating, and retrieving subject details. Supports searching for subjects based on name, grade level, and teacher assignments.

    - **RFID Credentials:** Manages RFID authentication used by microcontrollers. Provides CRUD operations for registering, updating, and retrieving RFID credentials. Supports searching for RFID credentials based on device ID and associated users.

    - **Attendance:** Manages student attendance with CRUD operations and advanced search and filtering capabilities. Enables tracking attendance by student, date, subject, and other parameters.

    - **Attendance Statistics:** Provides detailed endpoints for obtaining attendance statistics for the entire school, students, and more. Includes CRUD operations and various capabilities like tracking daily, weekly, and monthly attendance, late arrivals, absences, and more.

## Technologies Used

- Spring Boot
- WebSocket
- RabbitMQ
- JWT Authentication

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 21 or later
- Gradle

## Installation
Open INSTALL.md for installation instructions.

### Authentication
Secure your API endpoints using JWT authentication. Include the JWT token in the request header:
```
Authorization: Bearer your_jwt_token
```

### Contributors
- [Vince Angelo Batecan](https://github.com/llTheBlankll/llTheBlankll)