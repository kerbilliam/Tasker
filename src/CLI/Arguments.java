package CLI;
public interface Arguments {
    public String getDescription();
    public String[] getAlias();
    public boolean isAlias(String string);
    public String getName();
}
