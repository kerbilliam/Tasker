package CLI;

public class Command implements Arguments{
    private String description;
    private String[] aliases;
    private String name;
    
    public Command(String name, String[] aliases, String description) {
        this.name = name;
        this.aliases = aliases;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String[] getAlias() {
        return aliases;
    }
    
    public boolean isAlias(String str) {
        for (int i = 0; i < this.aliases.length; i++) {
            if (str.equals(aliases[i])) {
                return true;
            }
        }
        return false;
    }
}