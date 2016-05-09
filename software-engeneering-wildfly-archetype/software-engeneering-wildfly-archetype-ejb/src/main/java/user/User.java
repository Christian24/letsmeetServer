package user;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Christian on 03.05.2016.
 */
@Entity
public class User implements Serializable {
    @Id
    protected String userName;
    protected String password;
    protected String description;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String newUserName) {
        userName = newUserName;
    }
    public String getDescription() {return description;}
    public void setDescription(String newDescription) {
        description = newDescription;
    }
    public String getPassword(String password) {
        return password;
    }
    public void setPassword(String newPassword) {
        password = newPassword;
    }
}
