package web;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.6
 * 2016-05-18T21:34:03.444+02:00
 * Generated source version: 3.1.6
 * 
 */
@WebServiceClient(name = "OnlineIntegrationService", 
                  wsdlLocation = "http://localhost:8080/software-engeneering-wildfly-archetype-ejb/OnlineIntegration?wsdl",
                  targetNamespace = "http://web/") 
public class OnlineIntegrationService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://web/", "OnlineIntegrationService");
    public final static QName OnlineIntegrationPort = new QName("http://web/", "OnlineIntegrationPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/software-engeneering-wildfly-archetype-ejb/OnlineIntegration?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(OnlineIntegrationService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/software-engeneering-wildfly-archetype-ejb/OnlineIntegration?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public OnlineIntegrationService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public OnlineIntegrationService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OnlineIntegrationService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public OnlineIntegrationService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public OnlineIntegrationService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public OnlineIntegrationService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns OnlineIntegration
     */
    @WebEndpoint(name = "OnlineIntegrationPort")
    public OnlineIntegration getOnlineIntegrationPort() {
        return super.getPort(OnlineIntegrationPort, OnlineIntegration.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OnlineIntegration
     */
    @WebEndpoint(name = "OnlineIntegrationPort")
    public OnlineIntegration getOnlineIntegrationPort(WebServiceFeature... features) {
        return super.getPort(OnlineIntegrationPort, OnlineIntegration.class, features);
    }

}