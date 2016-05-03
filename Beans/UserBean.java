import java.io.Serializable;

/**
 * Created by Christian on 03.05.2016.
 */
public class UserBean extends Serializable {
    protected String userName;
    protected String password;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String newUserName) {
        userName = newUserName;
    }
    public String getPassword(String password) {
        return password;
    }
    public void setPassword(String newPassword) {
        password = newPassword;
    }
}
