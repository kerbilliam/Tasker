#   Database Methods **(Subject to Change)**

## Tables and Column Lables for the Database

### Users Table

Table name = `users`

| id | username | first_name | last_name | password | created |
|-|-|-|-|-|-|
| for database only | `String` **REQUIRED** | `String` | `String` | `String` **REQUIRED** | `String` created date (**Automatically Created**)

### Tasks Table

Table name = `tasks`

| id | task | due | assigned_users | status | priority | created |
|-|-|-|-|-|-|-|
| for database only | `String` task name | `String` due date of task | `String` assigned users to task | `String` stats of task | `String` priority of task | `String` created date (**Automatically Created**) |
## Displaying Databases
| Method    | Description               |
| --------- | ------------------------- |
| `init()`    | Creates new databse named `database.md` in the root project directory and initializes it with a `users` and `tasks` table.
| `printTable(table_name)` | Prints the entire contents of the specified table, formatted, to standard output.
| `printTable(table_name, field, fieldValue)` | Print contents of table that only contains the specified value for the specified field |

## Task Methods

Note when adding a Date, make sure it is in the ISO 8601 format: 'YYY-MM-DD HH:MM:SS'

| Method    | Description               |
| --------- | ------------------------- |
| `addTask(task_name, due_date, assignedUsers, status, priority)` | Adds a task to the database with given parameters. 
| `updateTasks(where, isThis, field, value)` | Updates an existing task where `where` == `isThis` by updtating a field (ie. `status`) to the specified value `value` |
| `assignUser(task_name, username)` | Replaces the value within the assigned_users column of a task to the given `username` parameter to an existing task with the specified task name |
| `deleteTask(task_name)` | Deletes an existing task within the database with the specified name |

## User Methods

| Method    | Description               |
| --------- | ------------------------- |
| `addUser(username, password)` | Adds a user with the specified username and password to the database |
| `updateUser(username, field, value)` | Replaces the value within the column of the specified `field` with the specified `value` to the user named `username` within the database |
| `removeUser(username)` | Removes an existing user with the name `username` from the database |
| `getAccounts()` | Returns a `HashMap<String, String>` with the keys being the usernames and values being the passwords for that user, all from the users database |

