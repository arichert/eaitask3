package de.wwu.database;

import de.wwu.DAO.DomainDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Object is used to get and store data of "domains" in the database
 *  
 *
 * @author guth
 */
public class Domain2Database {

    private static SQLite db;

    /**
     * Search the database for the domain with name
     *
     * @param domainName
     * @return DomainDAO
     */
    public static Boolean createDomain(String domainname) {

        try {

            //Connect to SQLite database
            db = new SQLite().connect();

            //Create prepared statement
            PreparedStatement prep = db.conn.prepareStatement("select * from domain where name = ?;");
            prep.setString(1, domainname);
            ResultSet rs = prep.executeQuery();

            //if empty ResultSet the domain can be created
            if (!rs.next()) {

                //Close the ResultSet!!!!
                //very important --> SQLite is a file based db, therefore there are
                //locks on the files, that should be closed by rs.close
                rs.close();
                
                //Create a new domain:
                //Create prepared statement
                prep = db.conn.prepareStatement("insert into domain (name) values (?);");
                //Set the domain name
                prep.setString(1, domainname);
                //Execute the sql statement
                prep.execute();
                
                return true;
            }else
            {
               //Close the ResultSet!!!!
               //very important --> SQLite is a file based db, therefore there are
               //locks on the files, that should be closed by rs.close
                rs.close();
                //The domain already exists...
                return false;
            }

            

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    /**
     * List all domains
     *
     * @return List of Domains
     */
    public static List<DomainDAO> listDomains() {

        //List of domains (initialy empty)
        List<DomainDAO> domains = new ArrayList<DomainDAO>();


        try {
            //Connect to sqlite db
            db = new SQLite().connect();

            //Create statement
            Statement stat = db.conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from domain;");

            //For each result row (domain)
            while (rs.next()) {

                //Create domain object
                DomainDAO domain = new DomainDAO();
                //Fill the data
                domain.name = rs.getString("name");

                //...and add to the list
                domains.add(domain);
            }
            //Very important (see above)
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        //Return the list of domains
        return domains;
    }
}
