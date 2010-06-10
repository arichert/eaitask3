package de.wwu.json;

import de.wwu.DAO.DomainDAO;
import de.wwu.DAO.MessageDAO;
import de.wwu.database.Domain2Database;
import flexjson.JSONSerializer;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for working with domain-data via WebAPI
 *
 *
 * @author guth
 */
public class DomainAPI {

    /**
     * List all domains
     *
     * 
     * @param out
     */
    public static void listDomains(PrintWriter out) {
        //Fetch the domains from the database
        List<DomainDAO> domains = Domain2Database.listDomains();
        //Initialize a JSON serializer
        JSONSerializer serializer = new JSONSerializer();
        //Serialize the Java objects and exclude the java class name
        out.print(serializer.exclude("class").serialize(domains));
    }

    /**
     * Get a domain by name
     *
     *
     * @param request
     * @param response
     * @param out
     */
    public static void createDomain(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        //Get the name parameter
        String name = request.getParameter("domainName");

        //Name not set
        if (name == null) {
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
        if (Domain2Database.createDomain(name)) {
            msg = new MessageDAO("success", "Domain " + name + " was created.");
        } else {
            msg = new MessageDAO("error", "Domain " + name + " was not created (it is already existing).");
        }
        //Initialize a JSON serializer
        JSONSerializer serializer = new JSONSerializer();
        //Serialize the Java objects and exclude the java class name
        out.print(serializer.exclude("class").serialize(msg));

    }
}
