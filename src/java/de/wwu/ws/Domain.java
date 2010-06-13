package de.wwu.ws;

import de.wwu.DAO.DomainDAO;
import de.wwu.DAO.MessageDAO;
import de.wwu.database.Domain2Database;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


@WebService()
@SOAPBinding(style = Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class Domain {

    //SOAP response contains the resulting xml nodes in <listDomainsResponse> ??????
    @WebMethod(operationName = "createDomain")
    //Results of a list are wrapped in xml called <domain>
    @WebResult(name = "message")
    public MessageDAO createDomain(@WebParam(name="domainname")String domainname) {
        
        //Try to create new Domain
        if(Domain2Database.createDomain(domainname))
            //Return success message...
            return new MessageDAO("success","Domain " + domainname + " was created.");
            //... or an error message
        else return new MessageDAO("error","Domain " + domainname + " was not created (it is already existing).");

    }

    @WebMethod(operationName = "deleteDomain")
    @WebResult(name = "message")
    public MessageDAO deleteDomain(@WebParam(name="domainname")String domainname) {

        //Try to create new Domain
        if(Domain2Database.deleteDomain(domainname))
            //Return success message...
            return new MessageDAO("success", "Domain " + domainname + " was deleted.");
            //... or an error message
        else return new MessageDAO("error", "Domain " + domainname + " was not deleted(doesn't exist).");

    }

    //SOAP response contains the resulting xml nodes in <listDomainsResponse>
    @WebMethod(operationName = "listDomains")
    //Results of a list are wrapped in xml called <domain>
    @WebResult(name = "domain")
    public List<DomainDAO> listDomains() {
        return Domain2Database.listDomains();
    }
}
