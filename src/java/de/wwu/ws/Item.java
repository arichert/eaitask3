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
 * @author florian
 */
@WebService()
@SOAPBinding(style = Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class Item
{
    @WebMethod(operationName = "createItem")
    //Results of a list are wrapped in xml called <item>
    @WebResult(name = "integer")
    public int ceateItem(@WebParam(name="domainName")String domainName)
    {
        return Item2Database.createItem(domainName);
    }
    @WebMethod(operationName = "deleteItem")
    //Results of a list are wrapped in xml called <item>
    @WebResult(name = "message")
    public MessageDAO deleteItem(@WebParam(name="itemId")String itemId)
    {
        return Item2Database.deleteItem(itemId);
    }
    @WebMethod(operationName = "getItem")
    //Results of a list are wrapped in xml called <item>
    @WebResult(name = "item")
    public ItemDAO getItem(@WebParam(name="itemId")String itemId)
    {
        return Item2Database.getItem(itemId);
    }
    @WebMethod(operationName = "listDomainItems")
    //Results of a list are wrapped in xml called <item>
    @WebResult(name = "item")
    public List<ItemDAO> listDomainItems(
            @WebParam(name="domainName")String domainName)
    {
        return Item2Database.listDomainItems(domainName);
    }
    @WebMethod(operationName = "setItemValue")
    //Results of a list are wrapped in xml called <item>
    @WebResult(name = "message")
    public MessageDAO setItemValue(@WebParam(name="itemId")String itemId,
            @WebParam(name="attributeName")String attributeName,
            @WebParam(name="value")String value)
    {
        return Item2Database.setItemValue(itemId, attributeName, value);
    }
    @WebMethod(operationName = "getItemValue")
    //Results of a list are wrapped in xml called <item>
    @WebResult(name = "string")
    public String getItemValue(@WebParam(name="itemId")String itemId,
            @WebParam(name="attributeName")String attributeName)
    {
        return Item2Database.getItemValue(itemId, attributeName);
    }

}
