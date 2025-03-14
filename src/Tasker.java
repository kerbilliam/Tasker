import CLI.HelpOutput;
import CLI.Parser;
import Cipher.Ciphers;
import Colors.StrColor;
import DB.Database;
import java.util.HashMap;

public class Tasker {
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			HelpOutput.printArguments();
			System.exit(1);
		}
		Parser psr = new Parser();
		psr.parse(args);
		HashMap<String, String> map = psr.getMap();
		String where = Ciphers.decrypt(map.get("where"), Ciphers.getKey());
		String isThis = map.get("isThis");
		String field = Ciphers.decrypt(map.get("field"), Ciphers.getKey());
		String value = map.get("value");
		
		switch (map.get("command")) {
			case "help":
				HelpOutput.printHelp();
				System.exit(1);
			case "init":
				Database.init();
				System.exit(1);
			case "login":
				// code to login
				System.exit(1);
		}

		switch (map.get("command")) {
			case "printTable":
				if (map.get("table") == null) {
					System.out.println(StrColor.red("Must define either users or tasks table. ")+"ex) -T tasks");
					break;
				}
				if (args.length == 3) {
					Database.printTable(Ciphers.decrypt(map.get("table"), Ciphers.getKey()));
				} else {
					Database.printTable(Ciphers.decrypt(map.get("table"), Ciphers.getKey()), where, isThis);
				}
				break;

			case "addTask":
				if (map.get(Database.TASK_NAME) == null) {
					System.out.println(StrColor.red("Must define a name for the new task. ")+"ex) --name \"task name\"");
					break;
				}
				Database.addTask(map.get(Database.TASK_NAME), map.get(Database.DUE_DATE), map.get(Database.USERNAME), map.get(Database.STATUS), map.get(Database.PRIORITY));
				break;

			case "updateTasks":
				if (where == null || isThis == null || field == null || value == null) {
					System.out.println(StrColor.red("Must define --where --is --field --value. ")+"Ex) --field priority --value Urgent --where task --is \"task name\"");
					break;
				}
				if (field.equals("due") || field.equals("created")) {
					value = Ciphers.decrypt(value, Ciphers.getKey());
				}
				Database.updateTasks(where, isThis, field, value);
				break;

			case "assignUser":
				if (where == null || isThis == null) {
					System.out.println(StrColor.red("Must use --where and --is. ")+"ex) --where task --is \"task name\"");
					break;
				}
				Database.assignUser(where, isThis, map.get(Database.USERNAME));
				break;

			case "deleteTask":
				if (map.get(Database.TASK_NAME) == null) {
					System.out.println(StrColor.red("Must define a task to delete. ")+"ex) --name task name");
					break;
				}
				Database.deleteTask(map.get(Database.TASK_NAME));
				break;

			case "addUser":
				if (map.get(Database.USERNAME) == null || map.get(Database.PASSWORD) == null) {
					System.out.println(StrColor.red("Must define a username and password. "+"ex) -U username --pass password"));
					break;
				}
				Database.addUser(map.get(Database.USERNAME), map.get(Database.PASSWORD), map.get(Database.FIRST_NAME), map.get(Database.LAST_NAME));
				break;

			case "updateUser":
				if (field == null || value == null || map.get(Database.USERNAME) == null) {
					System.out.println(StrColor.red("Must define a Username, field, and value. ")+"ex) -U username --field first_name --value John");
					break;
				}
				Database.updateUser(map.get(Database.USERNAME), field, value);
				break;

			case "removeUser":
				Database.removeUser(map.get(Database.USERNAME));
				break;
			
			case "logout":
				// logout current user
				break;

			default:
				HelpOutput.printArguments();
		}
	}
}
