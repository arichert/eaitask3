package de.wwu.json;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class/servlet gets called by any WebAPI call
 * 
 * The GET-calls are dispatched to any of the other API classes
 *
 * @author guth
 */
@WebServlet(name = "WebAPI", urlPatterns = {"/WebAPI"})
public class Dispatcher extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Set the content type:
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //Get the action parameter
        String action = request.getParameter("action");

        //If no param:
        if(action == null)
        { //Forward to the usage and example page
            RequestDispatcher dispatcher = request.getRequestDispatcher("apiUsage.jsp");
            dispatcher.forward(request, response);
        }


        /******HANDLING OF MAINTENANCE********/
        if (action.equals("setUpDB")) {
            MaintenanceAPI.setUpDB(out);
        }

        /******HANDLING OF DOMAINS********/
        //List domains
        else if (action.equals("listDomains")) {
            DomainAPI.listDomains(out);
        } //Get a domain by name
        else if (action.equals("createDomain")) {
            DomainAPI.createDomain(request, response, out);
        }

        /******HANDLING OF ATTRIBUTES********/
        /**TODO**/
        else if (action.equals("listDomainAttributes")) {
            AttributeAPI.listDomainAttributes(request, response, out);
        }
        
        else if (action.equals("createAttribute")) {
            AttributeAPI.createAttribute(request, response, out);
        }

        else if (action.equals("deleteAttribute")) {
            AttributeAPI.deleteAttribute(request, response, out);
        }

        /******HANDLING OF ITEMS********/
        /**TODO**/


        /******HANDLING OF SEARCH********/
        /**TODO**/

        
        //If nothing else fitted the parameters, print out the correct usage of the WebAPI
        else {
            //Forward to the usage and example page
            RequestDispatcher dispatcher = request.getRequestDispatcher("apiUsage.jsp");
            dispatcher.forward(request, response);
        }

        //Close output stream:
        out.close();

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
