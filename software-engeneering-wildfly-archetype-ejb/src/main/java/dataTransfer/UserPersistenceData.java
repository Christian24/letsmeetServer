package dataTransfer;

import user.UserPersist;

import java.io.Serializable;

/**
 * A dto for UserPersist
 * Created by Christian on 12.06.2016.
 */
public class UserPersistenceData implements Serializable {
    private static final long serialVersionUID = 1L;
    protected UserData userData;
    protected String userName;
    public UserPersistenceData(String text){
        userName = text;
    }
    public UserPersistenceData() {

    }
    public UserPersistenceData(UserPersist userPersist) {
        userName = userPersist.getName();
        if(userPersist.getUser() != null) {
            setUserData(new UserData(userPersist.getUser()));
        }
    }
    public UserPersistenceData(UserData userData) {
        this.setUserData(userData);
    }
    public String getUserName() {
        if(userData != null) {
            return userData.getUserName();
        }
        return userName;
    }
    public UserData getUserData() {
        return userData;
    }
    public void setUserData(UserData data) {
        userData = data;
    }
}
