import java.util.HashMap;
import DB.*;


public class Authentication {

    public static boolean checkAdmin(){
        return Database.isAdmin(TaskerMethods.whoIsLogged(), TaskerMethods.getCurrentUser().get(TaskerMethods.whoIsLogged()));
    }
    
    public static boolean uniqueUsername(String username) {
        HashMap<String, String> map = Database.getAccounts();
        for (String registered_user : map.keySet()) {
            if (username.equals(registered_user)) {
                return false;
            }
        }
        return true;
    }

}
