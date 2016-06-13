package user;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * A User entity that persists even if the user is deleted, we still want to keep its comments
 * Created by Christian on 12.06.2016.
 */
@Entity
public class UserPersist {
    private static final long serialVersionUID = 1L;
    @Id
    protected String name;
    @OneToOne(cascade = {CascadeType.ALL})
    protected User user;

    /**
     * Gets the user
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets the name of this user entity
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user and the name
     * @param user
     */
    public void setUser(User user) {

        this.user = user;
        if(this.user  != null){
            name = this.user.getUserName();
        }

    }

}
