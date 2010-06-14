/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.wwu.json;

import de.wwu.DAO.ItemDAO;
import de.wwu.DAO.MessageDAO;
import de.wwu.database.Item2Database;
import flexjson.JSONSerializer;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author florian
 */
public class ItemAPI
{
    public static void createItem(HttpServletRequest request,
            HttpServletResponse response, PrintWriter out)
    {
        String domain_name = request.getParameter("domainName");

        //Name not set
        if (domain_name == null)
        {
            //Forward to the example and usage page of the Web api
            RequestDispatcher dispatcher = request.getRequestDispatcher(
                    "apiUsage.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        MessageDAO msg;
        //Fetch the item data (if there is any)
        int newItemId = Item2Database.createItem(domain_name);
        if (newItemId != 0)
        {
            msg = new MessageDAO("success", "Item " + newItemId +
                    " was created in Domain " + domain_name + ".");
        } else
        {
            msg = new MessageDAO("error", "Item could not be created in Domain "
                    + domain_name + "!");
        }
        //Initialize a JSON serializer
        JSONSerializer serializer = new JSONSerializer();
        //Serialize the Java objects and exclude the java class name
        out.print(serializer.exclude("class").serialize(msg));
    }

    public static void deleteItem(HttpServletRequest request,
            HttpServletResponse response, PrintWriter out)
    {
        String item_id = request.getParameter("itemId");

        //Name not set
        if (item_id == null)
        {
            //Forward to the example and usage page of the Web api
            RequestDispatcher dispatcher = request.getRequestDispatcher(
                    "apiUsage.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        // TODO: Parsen hier oder in Item2DB?
        MessageDAO msg = Item2Database.deleteItem(item_id);
            
        //Initialize a JSON serializer
        JSONSerializer serializer = new JSONSerializer();
        //Serialize the Java objects and exclude the java class name
        out.print(serializer.exclude("class").serialize(msg));
    }
    
    public static void getItem(HttpServletRequest request, 
            HttpServletResponse response, PrintWriter out)
    {
        //get the item id parameter
        String item_id = request.getParameter("itemId");
        
        if (item_id == null) {
            //Forward to the example and usage page of the Web api
            RequestDispatcher dispatcher = request.getRequestDispatcher(
                    "apiUsage.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        //Fetch data from database
        ItemDAO item = Item2Database.getItem(item_id);
        //Initialize a JSON serializer
        JSONSerializer serializer = new JSONSerializer();
        //Serialize the Java objects and exclude the java class name
        out.print(serializer.exclude("class").serialize(item));
    }

    public static void listDomainItems(HttpServletRequest request,
            HttpServletResponse response, PrintWriter out)
    {
        //get the domain name parameter
        String domain_name = request.getParameter("domainName");

        //Name not set
        if (domain_name == null) {
            //Forward to the example and usage page of the Web api
            RequestDispatcher dispatcher = request.getRequestDispatcher(
                    "apiUsage.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        //Fetch data from database
        List<ItemDAO> items = Item2Database.listDomainItems(domain_name);
        //Initialize a JSON serializer
        JSONSerializer serializer = new JSONSerializer();
        //Serialize the Java objects and exclude the java class name
        out.print(serializer.exclude("class").serialize(items));
    }

    public static void setItemValue(HttpServletRequest request,
            HttpServletResponse response, PrintWriter out)
    {
        //get the item id parameter
        String item_id = request.getParameter("itemId");

        //get the attribute name parameter
        String attribute_name = request.getParameter("attributeName");

        //get the value parameter
        String value = request.getParameter("value");

        //One of the parameters is not set
        if (item_id == null || attribute_name == null || value == null) {
            //Forward to the example and usage page of the Web api
            RequestDispatcher dispatcher = request.getRequestDispatcher(
                    "apiUsage.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        MessageDAO msg = Item2Database.setItemValue(
                item_id, attribute_name, value);

        //Initialize a JSON serializer
        JSONSerializer serializer = new JSONSerializer();
        //Serialize the Java objects and exclude the java class name
        out.print(serializer.exclude("class").serialize(msg));
    }

    public static void getItemValue(HttpServletRequest request,
            HttpServletResponse response, PrintWriter out)
    {
        //get the item id parameter
        String item_id = request.getParameter("itemId");

        //get the attribute name parameter
        String attribute_name = request.getParameter("attributeName");

        //One of the parameters is not set
        if (item_id == null || attribute_name == null) {
            //Forward to the example and usage page of the Web api
            RequestDispatcher dispatcher = request.getRequestDispatcher(
                    "apiUsage.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        String ret = Item2Database.getItemValue(
                item_id, attribute_name);

        //Initialize a JSON serializer
        JSONSerializer serializer = new JSONSerializer();
        //Serialize the Java objects and exclude the java class name
        out.print(serializer.exclude("class").serialize(ret));
    }


}
