/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.wwu.ws;

import de.wwu.DAO.ItemDAO;
import de.wwu.database.Search2Database;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
 
/**
 *
 * @author Ron
 */
@WebService()
@SOAPBinding(style = Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class Search {
    @WebMethod(operationName = "search")
    @WebResult(name = "item")
    public List<ItemDAO> search(@WebParam(name="value")String value) {
        return Search2Database.search(value);
    }

    @WebMethod(operationName = "search2")
    @WebResult(name = "item")
    public List<ItemDAO> search2(@WebParam(name="value")String value,
            @WebParam(name="domainName")String domainName) {
        return Search2Database.search(value, domainName);
    }
    @WebMethod(operationName = "search3")
    @WebResult(name = "item")
    public List<ItemDAO> search3(@WebParam(name="value")String value,
            @WebParam(name="domainName")String domainName,
            @WebParam(name="attributeName")String attributeName) {
        return Search2Database.search(value, domainName, attributeName);
    }
}
