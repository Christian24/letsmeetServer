package letsmeet.dataTransfer;


import letsmeet.user.User;


/**
 * User object send to the client (without password)
 * @author Christian
 *
 */
public class UserData extends DataTransferObject {

	private static final long serialVersionUID = 3875155026759008881L;

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
