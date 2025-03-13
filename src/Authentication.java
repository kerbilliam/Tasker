import java.util.HashMap;
import DB.*;


public class Authentication {
    public static void main(String[] args) {

    }

    private HashMap<String, String> userDatabase;

    public Authentication() {
        this.userDatabase = Database.getAccounts();
    }

    public boolean isRegisteredUser(String inputUsername, String inputPassword) {
        return userDatabase.containsKey(inputUsername) && userDatabase.get(inputUsername).equals(inputPassword);
    }



}
