package web;

import user.UserRegistry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.io.Serializable;

/**
 * Created by Christian on 10.05.2016.
 */
@WebService
@Stateless
public class OnlineIntegration  {
    @EJB(beanName = "UserRegistry", beanInterface = user.UserRegistry.class)
    private UserRegistry userRegistry;

}
