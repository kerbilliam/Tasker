#   Database Methods

## Displaying Databases
| Method    | Description               |
| --------- | ------------------------- |
| `init()`    | Creates new databse named `database.md` in the root project directory and initializes it with a `users` and `tasks` table.
| `printTable(table_name)` | Prints the entire contents of the specified table, formatted, to standard output.
| `printTable(table_name, field, fieldValue)` | Print contents of table that only contains the specified value for the specified field |

## Task Methods
| Method    | Description               |
| --------- | ------------------------- |
| `addTask(task_name, assignedUsers, status, priority)` | Adds a task to the database 