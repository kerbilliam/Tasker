package CLI;

/**
 * Interface for {@code Command} and {@code Flag}. Specifies methods for getting and checking attributes of {@code Command} and {@code Flag} objects.
 */
public interface Arguments {
    /**
     * Retrives the description for the argument.
     * @return {@code String}
     */
    public String getDescription();
    
    /**
     * Returns a list of aliases for the argument.
     * @return {@code String[]} of aliases.
     */
    public String[] getAlias();
    
    /**
     * Checks if the string is one of the aliases for the Argument.
     * @param string String to check
     * @return {@code boolean} {@code true} if is one of the aliases, {@code false} if not;
     */
    public boolean isAlias(String string);
    
    /**
     * Returns the name of the Argument as a {@code String}.
     * @return Name of Argument
     */
    public String getName();
}
