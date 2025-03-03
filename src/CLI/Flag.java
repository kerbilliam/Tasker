package CLI;

public class Flag extends Command {
    private String[] associatedCommands;
    private String value = null;

    public Flag(String name, String[] aliases, String[] associatedCommands, String description) {
        super(name, aliases, description);
        this.associatedCommands = associatedCommands;
    }
    
    public String[] getAssocCommands() {
        return associatedCommands;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}