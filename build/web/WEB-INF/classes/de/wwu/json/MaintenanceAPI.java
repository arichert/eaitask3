package de.wwu.json;

import de.wwu.DAO.MessageDAO;
import de.wwu.database.SQLite;
import flexjson.JSONSerializer;
import java.io.PrintWriter;


/**
 * Class for working with domain-data via WebAPI
 *
 *
 * @author guth
 */
public class MaintenanceAPI {

    /**
     * SetUpDB
     *
     * 
     * @param out
     */
    public static void setUpDB(PrintWriter out) {

        //Connect to SQLite database
        SQLite db = new SQLite().connect();
        db.initialSetup();

        //Initialize a JSON serializer
        JSONSerializer serializer = new JSONSerializer();

        //Return success message
        //Serialize the Java objects and exclude the java class name
        out.print(serializer.exclude("class").serialize(new MessageDAO("success", "Database was set up!")));

    }

}
