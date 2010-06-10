/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.wwu.json;

import de.wwu.DAO.*;
import de.wwu.database.Attribute2Database;
import flexjson.JSONSerializer;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Axel Richert
 */
public class AttributeAPI {

    public static void createAttribute(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
        String attribute_name = request.getParameter("attributeName");
        String domain_name = request.getParameter("domainName");

        //Name not set
        if (attribute_name == null || domain_name == null) {
            //Forward to the example and usage page of the Web api
            RequestDispatcher dispatcher = request.getRequestDispatcher("apiUsage.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        MessageDAO msg;
        //Fetch the domain data (if there is any)
        if (Attribute2Database.createAttribute(attribute_name, domain_name)) {
            msg = new MessageDAO("success", "Attribute " + attribute_name + " was created in Domain " + domain_name + ".");
        } else {
            msg = new MessageDAO("error", "Attribute " + attribute_name + " could not be created in Domain " + domain_name + "!");
        }
        //Initialize a JSON serializer
        JSONSerializer serializer = new JSONSerializer();
        //Serialize the Java objects and exclude the java class name
        out.print(serializer.exclude("class").serialize(msg));
    }

    public static void listDomainAttributes(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
        //get the domain name parameter
        String domain_name = request.getParameter("domainName");

        //Name not set
        if (domain_name == null) {
            //Forward to the example and usage page of the Web api
            RequestDispatcher dispatcher = request.getRequestDispatcher("apiUsage.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        //Fetch data from database
        List<AttributeDAO> attributes = Attribute2Database.listDomainAttributes(domain_name);
        //Initialize a JSON serializer
        JSONSerializer serializer = new JSONSerializer();
        //Serialize the Java objects and exclude the java class name
        out.print(serializer.exclude("class").serialize(attributes));

        }

}

