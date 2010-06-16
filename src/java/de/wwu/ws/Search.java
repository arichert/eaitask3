/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.wwu.ws;

import de.wwu.DAO.ItemDAO;
import de.wwu.DAO.MessageDAO;
import de.wwu.database.Item2Database;
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
    @WebMethod(operationName = "searchValue")
    @WebResult(name = "item")
    public List<ItemDAO> searchValue(@WebParam(name="valueName")String searchName) {
        return Item2Database.searchItems(searchName);
    }
    
}
