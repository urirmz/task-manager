# Task Manager - Task Management & Approval System

A form-based web application for creating, managing, and approving tasks or bookings with role-based access control. Built with Java Spring Boot backend and vanilla HTML/CSS/JavaScript frontend.

## Features

### Core Features

- ✅ **User Authentication** - Secure login/logout with session management
- ✅ **Dashboard** - View pending, approved, and rejected tasks at a glance
- ✅ **Task Creation** - Create tasks with title, description, date/time, priority, and assignment
- ✅ **Task List** - Filter by status and sort by date or priority
- ✅ **Task Calendar** - Calendar view for tasks
- ✅ **Approval Workflow** - Manager/Admin role-based approval system
- ✅ **Notifications** - Console log notifications for task status changes
- ✅ **Export CSV** - Export task lists to CSV format for external analysis

### Role-Based Access Control

- **USER** - Can create and view tasks
- **MANAGER** - Can approve/reject tasks + all USER permissions
- **ADMIN** - Full system access, except approve/reject tasks

## Technology Stack

### Backend

- **Java 21**
- **Spring Boot 4.0.2**
- **Spring Data JPA** - Database access
- **Spring Security** - Authentication & authorization
- **H2 Database** - In-memory database
- **Maven** - Package manager

### Frontend

- **HTML5** - Semantic structure
- **CSS3** - Styling
- **Vanilla JavaScript** - No frameworks, pure JS
- **Fetch API** - RESTful API communication

## Prerequisites

- **Java 21 or higher**

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd task-manager
```

### 2. Build the Project

```bash
./mvnw clean install
```

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

### 4. Access the Application

Open your browser and navigate to:

```
http://localhost:8080
```

### 5. Access H2 Database Console (Optional)

For database inspection:

```
http://localhost:8080/h2-console
```

- **JDBC URL**: `jdbc:h2:mem:task_manager`
- **Username**: `sa`
- **Password**: (leave empty)

## Default User Credentials

The application comes preloaded with sample users for testing:

| Username | Password | Role    |
| -------- | -------- | ------- |
| admin    | password | ADMIN   |
| manager  | password | MANAGER |
| user1    | password | USER    |
| user2    | password | USER    |

## API Documentation

### Authentication Endpoints

#### Login

```http
POST /api/authorization/login
Content-Type: application/json

{
  "username": "manager",
  "password": "password"
}
```

**Response:**

```json
{
  "success": true,
  "user": {
    "id": 2,
    "username": "manager",
    "name": "Manager User",
    "role": "MANAGER"
  }
}
```

#### Logout

```http
POST /api/authorization/logout
```

#### Get Current User

```http
GET /api/authorization/current
```

### Task Endpoints

#### Create Task

```http
POST /api/tasks
Content-Type: application/json

{
  "title": "Review Code",
  "description": "Review pull requests",
  "scheduledDateTime": "2026-02-15T10:00:00",
  "priority": "HIGH",
  "assignedUserId": 3
}
```

#### Get All Tasks

```http
GET /api/tasks
```

#### Get Tasks by Status

```http
GET /api/tasks?status=PENDING
```

#### Get Task by ID

```http
GET /api/tasks/{id}
```

#### Approve Task (Manager/Admin only)

```http
PUT /api/tasks/{id}/approve
```

#### Reject Task (Manager/Admin only)

```http
PUT /api/tasks/{id}/reject
```

#### Export Tasks to CSV

```http
GET /api/tasks/export
```

**Response:** Returns a `.csv` file attachment containing all tasks.

### User Endpoints

#### Get All Users

```http
GET /api/users
```

**Response:**

```json
{
  "apiStatus": "SUCCESS",
  "users": [
    {
      "id": 1,
      "username": "admin",
      "name": "Admin User",
      "role": "ADMIN"
    }
  ]
}
```

## Workflow Logic

### Task Lifecycle

```
┌─────────────┐
│   PENDING   │ ← Initial state when task is created
└──────┬──────┘
       │
       ├──────────┐
       │          │
       ▼          ▼
┌──────────┐  ┌──────────┐
│ APPROVED │  │ REJECTED │ ← Final states (set by Manager/Admin)
└──────────┘  └──────────┘
```

### Approval Workflow Rules

1. **Task Creation**
   - Any authenticated user can create a task
   - New tasks start with `PENDING` status

2. **Task Approval/Rejection**
   - Only users with `MANAGER` role can approve/reject
   - Tasks must be in `PENDING` status to be approved/rejected
   - Once approved/rejected, status cannot be changed (in current implementation)

3. **Notifications**
   - When a task is approved/rejected, a notification is logged to the console
   - Format: `✓ NOTIFICATION: Task 'Title' has been APPROVED`

### Role-Based Authorization

| Action       | USER | MANAGER | ADMIN |
| ------------ | ---- | ------- | ----- |
| Create Task  | ✓    | ✓       | ✓     |
| View Tasks   | ✓    | ✓       | ✓     |
| Approve Task | ✗    | ✓       | ✓     |
| Reject Task  | ✗    | ✓       | ✓     |

## Testing

### Run Unit Tests

```bash
./mvnw test
```

### Manual Testing Workflow

1. **Login as User**
   - Login with `user / password`
   - Create a new task
   - Verify you cannot see approve/reject buttons

2. **Login as Manager**
   - Login with `manager / password`
   - View the pending task
   - Approve or reject the task
   - Check console for notification

3. **Verify Filters**
   - Use status filter to view only approved/rejected tasks
   - Use sort options to order by date or priority

## Troubleshooting

### Application won't start

- Ensure Java 21+ is installed: `java -version`
- Check if port 8080 is available
- Review console logs for specific errors

### Login fails

- Verify you're using correct credentials
- Check browser console for network errors
- Ensure cookies are enabled

### Tasks not loading

- Check browser console for JavaScript errors
- Verify you're logged in
- Check network tab for failed API calls
