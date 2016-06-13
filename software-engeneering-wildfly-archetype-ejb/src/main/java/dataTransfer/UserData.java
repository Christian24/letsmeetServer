package dataTransfer;


import user.User;


import java.io.Serializable;


/**
 * Created by Christian on 19.05.2016.
 * User object send to the client (without password)
 */
public class UserData extends DataTransferObject {


    protected String userName;

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
    public UserData() {

    }
    public UserData(User user) {
        this.userName = user.getUserName();
        this.description = user.getDescription();
    }

}
