import java.util.HashMap;
import DB.*;


public class Authentication {
    public static void main(String[] args) {

    }

    private HashMap<String, String> userDatabase;

    public static boolean isRegisteredUser(String inputUsername, String inputPassword) {
        return Database.getAccounts().containsKey(inputUsername) && Database.getAccounts().get(inputUsername).equals(inputPassword);
    }



}
