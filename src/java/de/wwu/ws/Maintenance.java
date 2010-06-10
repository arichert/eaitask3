package de.wwu.ws;

import de.wwu.DAO.MessageDAO;
import de.wwu.database.SQLite;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


@WebService()
@SOAPBinding(style = Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class Maintenance {

    /**
     * Set up the database (current stored data gets deleted!!!)
     *
     */
    @WebMethod(operationName = "setUpDB")
    //Results of a list are wrapped in xml called <domain>
    @WebResult(name = "message")
    public MessageDAO setUpDB() {
       
         //Connect to SQLite database
         SQLite db = new SQLite().connect();
         db.initialSetup();

         //Return success message
         return new MessageDAO("success","Database was set up!");
         
    }

}
