package data;

public class User implements Readable {

    private final String name;
    private final String username;
    private final String password;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Getter
    // /////////////////////////////////////////////////////////////////////////

    @Override
    public String read() {
        return String.format("(Nutzer*in) Name: %s, Nutzername: %s", name, username);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Getter
    // /////////////////////////////////////////////////////////////////////////

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
