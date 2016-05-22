package web;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.6
 * 2016-05-23T00:33:30.803+02:00
 * Generated source version: 3.1.6
 * 
 */
@WebService(targetNamespace = "http://web/", name = "OnlineIntegration")
@XmlSeeAlso({ObjectFactory.class})
public interface OnlineIntegration {

    @WebMethod
    @RequestWrapper(localName = "login", targetNamespace = "http://web/", className = "web.Login")
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://web/", className = "web.LoginResponse")
    @WebResult(name = "return", targetNamespace = "")
    public web.SessionResponse login(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1
    );

    @WebMethod
    @RequestWrapper(localName = "getMeet", targetNamespace = "http://web/", className = "web.GetMeet")
    @ResponseWrapper(localName = "getMeetResponse", targetNamespace = "http://web/", className = "web.GetMeetResponse")
    @WebResult(name = "return", targetNamespace = "")
    public web.MeetResponse getMeet(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1
    );

    @WebMethod
    @RequestWrapper(localName = "leaveMeet", targetNamespace = "http://web/", className = "web.LeaveMeet")
    @ResponseWrapper(localName = "leaveMeetResponse", targetNamespace = "http://web/", className = "web.LeaveMeetResponse")
    @WebResult(name = "return", targetNamespace = "")
    public web.MeetResponse leaveMeet(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1
    );

    @WebMethod
    @RequestWrapper(localName = "register", targetNamespace = "http://web/", className = "web.Register")
    @ResponseWrapper(localName = "registerResponse", targetNamespace = "http://web/", className = "web.RegisterResponse")
    @WebResult(name = "return", targetNamespace = "")
    public web.SessionResponse register(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2
    );

    @WebMethod
    @RequestWrapper(localName = "getMeetsByUser", targetNamespace = "http://web/", className = "web.GetMeetsByUser")
    @ResponseWrapper(localName = "getMeetsByUserResponse", targetNamespace = "http://web/", className = "web.GetMeetsByUserResponse")
    @WebResult(name = "return", targetNamespace = "")
    public web.MeetsResponse getMeetsByUser(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebMethod
    @RequestWrapper(localName = "joinMeet", targetNamespace = "http://web/", className = "web.JoinMeet")
    @ResponseWrapper(localName = "joinMeetResponse", targetNamespace = "http://web/", className = "web.JoinMeetResponse")
    @WebResult(name = "return", targetNamespace = "")
    public web.MeetResponse joinMeet(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1
    );

    @WebMethod
    @RequestWrapper(localName = "getMeets", targetNamespace = "http://web/", className = "web.GetMeets")
    @ResponseWrapper(localName = "getMeetsResponse", targetNamespace = "http://web/", className = "web.GetMeetsResponse")
    @WebResult(name = "return", targetNamespace = "")
    public web.MeetsResponse getMeets(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        javax.xml.datatype.XMLGregorianCalendar arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        javax.xml.datatype.XMLGregorianCalendar arg2
    );

    @WebMethod
    @RequestWrapper(localName = "getCategories", targetNamespace = "http://web/", className = "web.GetCategories")
    @ResponseWrapper(localName = "getCategoriesResponse", targetNamespace = "http://web/", className = "web.GetCategoriesResponse")
    @WebResult(name = "return", targetNamespace = "")
    public web.CategoriesResponse getCategories(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebMethod
    @RequestWrapper(localName = "init", targetNamespace = "http://web/", className = "web.Init")
    @ResponseWrapper(localName = "initResponse", targetNamespace = "http://web/", className = "web.InitResponse")
    public void init();
}
