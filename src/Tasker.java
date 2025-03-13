import CLI.HelpOutput;
import CLI.Parser;
import Colors.StrColor;
import DB.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Tasker {
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length == 0) {
			HelpOutput.printArguments();
			System.exit(1);
		}
		Parser psr = new Parser();
		psr.parse(args);
		HashMap<String, String> map = psr.getMap();
		String where = map.get("where");
		String isThis = map.get("isThis");
		String field = map.get("field");
		String value = map.get("value");
		
		switch (map.get("command")) {
			case "help":
				HelpOutput.printHelp();
				break;
			case "init":
				Database.init();
				break;
			case "login":
				TaskerMethods.login(map.get(Database.USERNAME), map.get(Database.PASSWORD));
				System.exit(0);
		}

		switch (map.get("command")) {
			case "printTable":
				if (args.length == 3) {
					Database.printTable(map.get("table"));
				} else {
					Database.printTable(map.get("table"), where, isThis);
				}
				break;

			case "addTask":
				Database.addTask(map.get(Database.TASK_NAME), map.get(Database.DUE_DATE), map.get(Database.ASSIGNED_USER), map.get(Database.STATUS), map.get(Database.PRIORITY));
				break;

			case "updateTasks":
				Database.updateTasks(where, isThis, field, value);
				break;

			case "assignUser":
				Database.assignUser(where, isThis, map.get(Database.ASSIGNED_USER));
				break;

			case "deleteTask":
				if (map.get(Database.TASK_NAME) == null) {
					System.out.println(StrColor.RED+ "Error: Must define a task to delete"+StrColor.RESET);
					break;
				}
				Database.deleteTask(map.get(Database.TASK_NAME));
				break;

			case "addUser":
				Database.addUser(map.get(Database.USERNAME), map.get(Database.PASSWORD), map.get(Database.FIRST_NAME), map.get(Database.LAST_NAME));
				break;

			case "updateUser":
				Database.updateUser(map.get(Database.USERNAME), field, value);
				break;

			case "removeUser":
				Database.removeUser(map.get(Database.USERNAME));
				break;

			default:
				HelpOutput.printArguments();
		}
	}
}
