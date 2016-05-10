package user;


import javax.annotation.PostConstruct;
import java.util.HashMap;

public class UserRegistry {
    protected HashMap<String, User> usersByUserName;

    @PostConstruct
    private void init() {
        usersByUserName = new HashMap<String,User>();

    }

}