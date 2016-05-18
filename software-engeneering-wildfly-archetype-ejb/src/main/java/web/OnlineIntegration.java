package web;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 * Created by Christian on 10.05.2016.
 */
@WebService
@Stateless
public class OnlineIntegration  {
    public void register(String name, String password) {

    }
    public String helloWorld() {
        return "Hello World!";
    }

}
