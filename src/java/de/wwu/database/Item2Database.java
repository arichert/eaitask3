/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.wwu.database;

import de.wwu.DAO.ItemDAO;
import de.wwu.DAO.MessageDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author florian
 */
public class Item2Database {

    private static SQLite db;

    public static int createItem(String domain_name)
    {
        int newItemId = 0;

        try
        {
            //Connect to SQLite database
            db = new SQLite().connect();


            //Create new attribute
            PreparedStatement prep = db.conn.prepareStatement(
                    "INSERT INTO item (domain_name) VALUES (?);");

            //Set the domainname
            prep.setString(2, domain_name);
            //Execute the sql statement
            prep.execute();
            //Get the generated key
            ResultSet autoKey = prep.getGeneratedKeys();

            if (autoKey.next())
            {
                //return the value of the column item_id
                newItemId = autoKey.getInt("item_id");
            }
            autoKey.close();
            // TODO: Richtige Exceptions raussuchen
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return newItemId;
    }

    public static MessageDAO deleteItem(int item_id)
    {
        // TODO: Implementieren

        return null;
    }

    public static ItemDAO getItem(int item_id)
    {
        ItemDAO requestedItem = null;
        try
        {
            //Connect to SQLite database
            db = new SQLite().connect();

            //Create prepared statement
            PreparedStatement prep = db.conn.prepareStatement(
                    "SELECT * " +
                    "FROM item " +
                    "WHERE item_id = ?;");
            prep.setInt(1, item_id);
            ResultSet rs = prep.executeQuery();

            if (rs.next())
            {
                requestedItem = new ItemDAO(
                        rs.getInt("item_id"), rs.getString("domain_name"));
            }

            rs.close();
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return requestedItem;
    }

    public static List<ItemDAO> listDomainItems(String domain_name){
        // TODO: Include all associated values?!
        List<ItemDAO> domainItems = new ArrayList<ItemDAO>();
        try
        {
            //Connect to SQLite database
            db = new SQLite().connect();

            //Create prepared statement
            PreparedStatement prep = db.conn.prepareStatement(
                    "SELECT * " +
                    "FROM item " +
                    "WHERE domain_name = ?;");
            prep.setString(1, domain_name);
            ResultSet rs = prep.executeQuery();

            while(rs.next())
            {
                ItemDAO tempItem = new ItemDAO(
                        rs.getInt("item_id"), rs.getString("domain_name"));

                domainItems.add(tempItem);
            }

            rs.close();
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return domainItems;
    }

    public static MessageDAO setItemValue(int item_id, String attribute_name,
            String value){
        // TODO: Implementieren
        return null;
    }

    public static String getItemValue(int item_id, String attribute_name){
        // TODO: Implementieren
        return null;
    }

}
