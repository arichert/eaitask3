/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.wwu.ws;

import de.wwu.DAO.AttributeDAO;
import de.wwu.DAO.MessageDAO;
import de.wwu.database.Attribute2Database;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 *
 * @author Axel Richert
 */
@WebService()
@SOAPBinding(style = Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class Attribute {

    //SOAP response contains the resulting xml nodes in <listAttributesResponse>
    @WebMethod(operationName = "createAttribute")
    //Results of a list are wrapped in xml called <attribute>
    @WebResult(name = "message")
    public MessageDAO createAttribute(@WebParam(name="attributeName")String attributeName,
            @WebParam(name="domainName")String domainName) {

        //Try to create new Attribute
        if(Attribute2Database.createAttribute(attributeName, domainName))
            //Return success message...
            return new MessageDAO("success", "Attribute " + attributeName + " was created in Domain " + domainName + ".");
            //... or an error message
        else return new MessageDAO("error", "Attribute " + attributeName + " could not be created in Domain " + domainName + "!");

    }

    @WebMethod(operationName = "deleteAttribute")
    @WebResult(name = "message")
    public MessageDAO deleteAttribute(@WebParam(name="attributeName")String attributeName,
            @WebParam(name="domainName")String domainName) {

        //Try to delete Attribute
        if(Attribute2Database.deleteAttribute(attributeName, domainName))
            //Return success message...
            return new MessageDAO("success", "Attribute " + attributeName + " was deleted in Domain " + domainName + ".");
            //... or an error message
        else return new MessageDAO("error", "Attribute " + attributeName + " could not be deleted in Domain " + domainName + "!");
    }


    @WebMethod(operationName = "listDomainAttributes")
    @WebResult(name = "attribute")
    public List<AttributeDAO> listDomainAttributes(@WebParam(name="domainName")String domainName) {
        return Attribute2Database.listDomainAttributes(domainName);
    }

}
