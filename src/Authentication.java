import java.util.HashMap;
import DB.*;


public class Authentication {
    public static void main(String[] args) {

    }

    private HashMap<String, String> userDatabase;

    public static boolean checkAdmin(){
        return Database.isAdmin(TaskerMethods.whoIsLogged(), TaskerMethods.getCurrentUser().get(TaskerMethods.whoIsLogged()));
    }

}
