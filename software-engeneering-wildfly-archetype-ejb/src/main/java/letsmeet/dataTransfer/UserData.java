package letsmeet.dataTransfer;


import letsmeet.user.User;


/**
 * User object send to the client (without password)
 * @author Christian, Julian
 *
 */
public class UserData extends DataTransferObject {

	private static final long serialVersionUID = 3875155026759008881L;

	protected String userName;
    protected String description;

    

    public UserData() {

    }
    
    public UserData(User user) {
        this.userName = user.getUserName();
        this.description = user.getDescription();
    }

    // by Julian
    @Override
    public boolean equals(Object o){
    	if(null==o)return false;
    	if(!(o instanceof UserData)) return false;
    	if(o == this) return true;
    	UserData that = (UserData) o;
    	
    	if(!(this.userName.equals(that.userName))) return false;
    	if(!(this.description.equals(that.description))) return false;
    	
    	return true;
    }
    
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
}
