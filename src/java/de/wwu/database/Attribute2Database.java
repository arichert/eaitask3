/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.wwu.database;

import de.wwu.DAO.AttributeDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Axel Richert
 */
public class Attribute2Database {

    private static SQLite db;

    public static Boolean createAttribute(String attribute_name, String domain_name){
        try{
            //Connect to SQLite database
            db = new SQLite().connect();

            //Create prepared statement
            PreparedStatement prep = db.conn.prepareStatement("select * from attribute where attribute_name = ? AND domain_name = ?;");
            prep.setString(1, attribute_name);
            prep.setString(2, domain_name);
            ResultSet rs = prep.executeQuery();

            //if empty ResultSet the attribute can be created
            if (!rs.next()) {

                //close ResultSet
                rs.close();

                //Create new attribute
                prep = db.conn.prepareStatement("insert into attribute (attribute_name, domain_name) values (?, ?);");
                //Set the attribute name
                prep.setString(1, attribute_name);
                //Set the domainname
                prep.setString(2, domain_name);
                //Execute the sql statement
                prep.execute();

                return true;
            }else
            {
               //Close the ResultSet
               rs.close();
                //The combination of attribute_name and domain_name already exists
                return false;
            }
        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    
    public static Boolean deleteAttribute(String attribute_name, String domain_name){
        
        try {
            
            //Connect to SQLite database
            db = new SQLite().connect();
            
            //First check if the attribute and the corresponding domain exist
            PreparedStatement prep = db.conn.prepareStatement("SELECT * from attribute where attribute_name = ? AND domain_name = ?;");
            prep.setString(1, attribute_name);
            prep.setString(2, domain_name);
            ResultSet rs = prep.executeQuery();
            
            //if ResultSet is not empty the attribute be deleted because it exists
            if(rs.next()){
                //close ResultSet
                rs.close();

                //Delete attribute in table "attribute"
                prep = db.conn.prepareStatement("DELETE FROM attribute WHERE attribute_name = ? AND domain_name = ?;");
                //Set the attribute name
                prep.setString(1, attribute_name);
                //Set the domainname
                prep.setString(2, domain_name);
                //Execute the sql statement
                prep.executeUpdate();
                
                //Delete all occurances in table "value"
                prep = db.conn.prepareStatement("DELETE FROM value WHERE attribute_name = ? AND domain_name = ?;");
                prep.setString(1, attribute_name);
                prep.setString(2, domain_name);
                prep.executeUpdate();

                return true;
            } else
            {
               //Close the ResultSet
               rs.close();
                //The combination of attribute_name and domain_name does not exist
                return false;
            }
        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
                
        
    }


    /**
     * Returns a list of all domain specific attributes
     *
     * @return Returns a list of all domain specific attributes
     */
    public static List<AttributeDAO> listDomainAttributes(String domain_name) {

        //List of domains (initialy empty)
        List<AttributeDAO> attributes = new ArrayList<AttributeDAO>();


        try {
            //Connect to sqlite db
            db = new SQLite().connect();

            //Create statement
             //Create prepared statement
            PreparedStatement prep = db.conn.prepareStatement("select * from attribute where domain_name = ?;");
            prep.setString(1, domain_name);
            ResultSet rs = prep.executeQuery();

            //For each result row (domain)
            while (rs.next()) {

                //Create attribute object
                AttributeDAO attribute = new AttributeDAO();
                //Fill the data
                attribute.attribute_name = rs.getString("attribute_name");
                attribute.domain_name = rs.getString("domain_name");

                //...and add to the list
                attributes.add(attribute);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        //Return the list of domains
        return attributes;
    }
 
}
