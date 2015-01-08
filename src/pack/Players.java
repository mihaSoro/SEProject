package pack;

/**
 * Created by CristiaN1 on 12/30/2014.
 */
public class Players {
    private String user;
    private String pass;
    private String role;

    public Players(String user, String pass, String role) {
        this.user = user;
        this.pass = pass;
        this.role = role;
    }

    public Players() {
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getRole() {
        return role;
    }

    public void setUser(String user) {
        try {
            if(user != null)
                this.user = user;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
