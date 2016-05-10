package user;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.HashMap;
@Singleton
@Startup
public class UserRegistry {
    protected HashMap<String, User> usersByUserName;

    @PostConstruct
    private void init() {
        usersByUserName = new HashMap<String,User>();

    }

}