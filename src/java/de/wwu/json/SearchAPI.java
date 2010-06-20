/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.wwu.json;

import de.wwu.DAO.ItemDAO;
import de.wwu.database.Search2Database;
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
public class SearchAPI {
    public static void search(HttpServletRequest request,
            HttpServletResponse response, PrintWriter out)
    {
        //get the domain name parameter
        String value = request.getParameter("value");

        String domainName = request.getParameter("domainName");

        String attributeName = request.getParameter("attributeName");

        //Name not set
        if (value == null || (domainName == null && attributeName != null)) {
            //Forward to the example and usage page of the Web api
            RequestDispatcher dispatcher = request.getRequestDispatcher(
                    "apiUsage.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        List<ItemDAO> items = null;
        if (domainName != null) {
            if (attributeName != null){
                items = Search2Database.search(value, domainName, attributeName);
            } else {
                items = Search2Database.search(value, domainName);
            }
        } else {
            //Fetch data from database
            items = Search2Database.search(value);
        }
        //Initialize a JSON serializer
        JSONSerializer serializer = new JSONSerializer();
        //Serialize the Java objects and exclude the java class name
        out.print(serializer.exclude("class").serialize(items));
    }



}
