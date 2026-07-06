# Employee Database

A Spring Boot application for managing employees with a REST API and a web UI. You can add, view, update, and delete employees through the browser or by calling the API directly.

**Repository:** [https://github.com/Taslimtaj/EmplyeeDatabase.git](https://github.com/Taslimtaj/EmplyeeDatabase.git)

---

## UI Preview

The web UI is a single-page dashboard at `http://localhost:8080`. Below is a sample of what you see after adding a few employees.

```
┌─────────────────────────────────────────────────────────────────────┐
│  👥  Employee Management                        [ + Add Employee ]    │
│      Manage your team in one place                                  │
├─────────────────────────────────────────────────────────────────────┤
│  Total Employees                                                    │
│  3                                                                  │
├─────────────────────────────────────────────────────────────────────┤
│  All Employees                                      [ Refresh ]     │
│  ┌─────────────┬──────────────────┬─────────────────────────────┐   │
│  │ Employee ID │ Name             │ Actions                     │   │
│  ├─────────────┼──────────────────┼─────────────────────────────┤   │
│  │ E001        │ Jane Doe         │ [ Edit ]  [ Delete ]        │   │
│  │ E002        │ John Smith       │ [ Edit ]  [ Delete ]        │   │
│  │ E003        │ Alice Johnson    │ [ Edit ]  [ Delete ]        │   │
│  └─────────────┴──────────────────┴─────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
```

### Add Employee modal (sample)

When you click **+ Add Employee**, a popup form appears:

```
┌──────────────────────────────────┐
│  Add Employee                 ✕  │
├──────────────────────────────────┤
│  Employee ID                     │
│  [ E004________________ ]        │
│                                  │
│  Full Name                       │
│  [ Bob Wilson__________ ]        │
│                                  │
│         [ Cancel ] [ Save Employee ] │
└──────────────────────────────────┘
```

After saving, the table updates and a toast shows: **"Employee added successfully"**.

---

## Sample Demo (Try It Yourself)

Follow this quick demo to see the full UI in action.

### Step 1 — Start the app

```bash
mvn spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080). You should see an empty table with the message: *"No employees yet. Click Add Employee to get started."*

### Step 2 — Load sample employees (optional)

Run these commands in a new terminal to pre-fill sample data:

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{"employeeId":"E001","employeeName":"Jane Doe"}'

curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{"employeeId":"E002","employeeName":"John Smith"}'

curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{"employeeId":"E003","employeeName":"Alice Johnson"}'
```

Refresh the browser. The UI should now show **3 employees** in the table.

### Step 3 — Add an employee from the UI

| Field | Sample value |
|-------|--------------|
| Employee ID | `E004` |
| Full Name | `Bob Wilson` |

Click **Save Employee**. Total count becomes **4**.

### Step 4 — Edit an employee

1. Click **Edit** on row `E002` (John Smith)
2. Change name to `John Smith Jr.`
3. Click **Save Employee**

The table updates instantly. Employee ID stays `E002`.

### Step 5 — Delete an employee

1. Click **Delete** on row `E003` (Alice Johnson)
2. Confirm the dialog

Row is removed. Total count becomes **3**. Toast shows: **"Employee deleted successfully"**.

### What the UI does (summary)

| Action | What you click | Result |
|--------|----------------|--------|
| View all | Open home page | Table lists every employee |
| Add | **+ Add Employee** → fill form → **Save** | New row appears |
| Edit | **Edit** on a row → change name → **Save** | Name updates in table |
| Delete | **Delete** on a row → confirm | Row removed |
| Refresh | **Refresh** button | Reloads data from server |

---

## Features

- Add new employees
- View all employees in a table
- Edit employee names
- Delete employees
- REST API for integration with other tools (Jenkins, Postman, etc.)
- Input validation (Employee ID and Name are required)
- In-memory storage (data resets when the app restarts)

---

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+**

Check your versions:

```bash
java -version
mvn -version
```

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/Taslimtaj/EmplyeeDatabase.git
cd EmplyeeDatabase
```

### 2. Run the application

```bash
mvn spring-boot:run
```

### 3. Open the web UI

Go to [http://localhost:8080](http://localhost:8080) in your browser.

---

## Web UI Usage

### View employees

When the app starts, the home page shows a table of all employees and a total count at the top. Click **Refresh** to reload the list.

### Add an employee

1. Click **+ Add Employee**
2. Enter **Employee ID** (e.g. `E001`)
3. Enter **Full Name** (e.g. `Jane Doe`)
4. Click **Save Employee**

Both fields are required. If the Employee ID already exists, an error message is shown.

### Edit an employee

1. Find the employee in the table
2. Click **Edit**
3. Update the name (Employee ID cannot be changed)
4. Click **Save Employee**

### Delete an employee

1. Find the employee in the table
2. Click **Delete**
3. Confirm in the popup dialog

A success or error toast appears at the bottom of the screen after each action.

---

## REST API Usage

Base URL: `http://localhost:8080/api/employees`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/employees` | List all employees |
| `GET` | `/api/employees/{employeeId}` | Get one employee |
| `POST` | `/api/employees` | Add a new employee |
| `PUT` | `/api/employees/{employeeId}` | Update employee name |
| `DELETE` | `/api/employees/{employeeId}` | Delete an employee |

### Employee JSON format

```json
{
  "employeeId": "E001",
  "employeeName": "Jane Doe"
}
```

### Add an employee

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{"employeeId":"E001","employeeName":"Jane Doe"}'
```

**Response:** `201 Created`

```json
{
  "employeeId": "E001",
  "employeeName": "Jane Doe"
}
```

### List all employees

```bash
curl http://localhost:8080/api/employees
```

**Response:** `200 OK`

```json
[
  {
    "employeeId": "E001",
    "employeeName": "Jane Doe"
  }
]
```

### Get one employee

```bash
curl http://localhost:8080/api/employees/E001
```

**Response:** `200 OK`

### Update an employee

```bash
curl -X PUT http://localhost:8080/api/employees/E001 \
  -H "Content-Type: application/json" \
  -d '{"employeeId":"E001","employeeName":"Jane Smith"}'
```

**Response:** `200 OK`

### Delete an employee

```bash
curl -X DELETE http://localhost:8080/api/employees/E001
```

**Response:** `204 No Content`

---

## Error Responses

| Status | When |
|--------|------|
| `400 Bad Request` | Required fields are missing or blank |
| `404 Not Found` | Employee ID does not exist |
| `409 Conflict` | Employee ID already exists on create |

Example error body:

```json
{
  "message": "Employee not found with id: E999"
}
```

---

## Run Tests

```bash
mvn test
```

Tests cover create, list, update, delete, and validation scenarios.

---

## Build JAR

```bash
mvn clean package
java -jar target/demo-1.0-SNAPSHOT.jar
```

---

## Project Structure

```
src/main/java/com/example/
├── EmployeeApplication.java      # Application entry point
├── controller/
│   └── EmployeeController.java   # REST endpoints
├── service/
│   └── EmployeeService.java      # Business logic
├── model/
│   └── Employee.java             # Employee entity
├── dto/
│   └── CreateEmployeeRequest.java # Request body for add/update
└── exception/                    # Error handling

src/main/resources/static/
├── index.html                    # Web UI
├── css/style.css                 # UI styling
└── js/app.js                     # UI logic (calls REST API)

src/test/java/com/example/
└── controller/
    └── EmployeeControllerTest.java
```

---

## Jenkins / CI

To build in Jenkins, use:

```bash
mvn clean test package -B
```

Point your Jenkins job at this repository and branch `master`.

---

## Notes

- Data is stored **in memory** only. Restarting the application clears all employees.
- Default server port is **8080**. To change it, add `server.port=9090` in `src/main/resources/application.properties`.

---

## License

This project is for educational and demo purposes.
