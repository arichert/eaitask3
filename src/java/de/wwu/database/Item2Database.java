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

    public static int createItem(String domain_name) {
        int newItemId = 0;

        try {
            //Connect to SQLite database
            db = new SQLite().connect();


            //Create new attribute
            PreparedStatement prep = db.conn.prepareStatement(
                    "INSERT INTO item VALUES (NULL,?);");

            //Set the domainname
            prep.setString(1, domain_name);
            //Execute the sql statement
            prep.execute();
            //Get the generated key
            ResultSet autoKey = prep.getGeneratedKeys();

            if (autoKey.next()) {
                //return the value of the column item_id
                newItemId = autoKey.getInt(1);
            }
            autoKey.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return newItemId;
    }

    public static MessageDAO deleteItem(String item_id) {
        String type = "success";
        String string = "";

        try {
            int itemId = Integer.parseInt(item_id);
            //Connect to SQLite database
            db = new SQLite().connect();

            //Create prepared statement
            PreparedStatement prep = db.conn.prepareStatement(
                    "SELECT * "
                    + "FROM value "
                    + "WHERE item_id = ?;");
            prep.setInt(1, itemId);
            ResultSet rs = prep.executeQuery();

            if (rs.next()) {
                rs.close();
                prep = db.conn.prepareStatement(
                        "DELETE FROM value WHERE item_id = ?;");
                prep.setInt(1, itemId);
                prep.execute();
                string = "Deleting values with item_id " + item_id
                        + "was successfull! ";
            } else {
                rs.close();
                string = "No values with item_id " + item_id + "found! ";
            }

            //Create prepared statement
            prep = db.conn.prepareStatement(
                    "SELECT * "
                    + "FROM item "
                    + "WHERE item_id = ?;");
            prep.setInt(1, itemId);
            rs = prep.executeQuery();

            if (rs.next()) {
                rs.close();
                prep = db.conn.prepareStatement(
                        "DELETE FROM item "
                        + "WHERE item_id = ?;");
                prep.setInt(1, itemId);
                prep.execute();
                string += "Deleting item with item_id " + item_id
                        + "was successfull!";
            } else {
                rs.close();
                string += "No item with item_id " + item_id + "found! ";
            }
        } catch (Exception e) {
            string = e.toString();
            type = "error";
            e.printStackTrace();
        }
//        catch (NumberFormatException e)
//        {
//            msg = new MessageDAO("error",  e.toString());
//        }

        return new MessageDAO(type, string);
    }

    public static ItemDAO getItem(String item_id) {
        ItemDAO requestedItem = null;
        try {
            int itemId = Integer.parseInt(item_id);
            //Connect to SQLite database
            db = new SQLite().connect();

            PreparedStatement prep = db.conn.prepareStatement(
                    "SELECT * "
                    + "FROM item "
                    + "WHERE item_id = ?;");
            prep.setInt(1, itemId);
            ResultSet rs = prep.executeQuery();

            if (rs.next()) {
                requestedItem = new ItemDAO();
                requestedItem.item_id = rs.getInt("item_id");
                requestedItem.domain_name = rs.getString("domain_name");
            
                rs.close();

                //Create prepared statement
                prep = db.conn.prepareStatement(
                        "SELECT * "
                        + "FROM value "
                        + "WHERE item_id = ?;");
                prep.setInt(1, itemId);
                rs = prep.executeQuery();


                while (rs.next()) {
                    String attributeName = rs.getString("attribute_name");
                    String value = rs.getString("value");
                    requestedItem.addAttribute(attributeName, value);
                }

                rs.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return requestedItem;
    }

    public static List<ItemDAO> listDomainItems(
            String domain_name) {
        List<ItemDAO> domainItems =
                new ArrayList<ItemDAO>();
        try {
            //Connect to SQLite database
            db = new SQLite().connect();

            //Create prepared statement
            PreparedStatement prep = db.conn.prepareStatement(
                    "SELECT item_id "
                    + "FROM item "
                    + "WHERE domain_name = ?;");
            prep.setString(1, domain_name);
            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                domainItems.add(getItem(rs.getString("item_id")));
            }

            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return domainItems;
    }

    

    public static MessageDAO setItemValue(String item_id, String attribute_name,
            String value) {
        String type = "";
        String string = "";
        try {
            int itemId = Integer.parseInt(item_id);
            //Connect to SQLite database
            db = new SQLite().connect();

            //Create prepared statement
            PreparedStatement prep = db.conn.prepareStatement(
                    "SELECT * "
                    + "FROM value "
                    + "WHERE item_id = ? "
                    + "AND attribute_name = ?;");
            prep.setInt(1, itemId);
            prep.setString(2, attribute_name);
            ResultSet rs = prep.executeQuery();

            if (rs.next()) {
                rs.close();
                prep = db.conn.prepareStatement(
                        "UPDATE value "
                        + "SET value = ? "
                        + "WHERE item_id = ? "
                        + "AND attribute_name = ?;");
                prep.setString(1, value);
                prep.setInt(2, itemId);
                prep.setString(3, attribute_name);
                prep.executeUpdate();

                type = "success";
                string = "Item value was changed!";
            } else {
                rs.close();

                prep = db.conn.prepareStatement(
                        "SELECT * "
                        + "FROM item "
                        + "WHERE item_id = ?;");
                prep.setInt(1, itemId);
                rs = prep.executeQuery();

                if (rs.next()) {
                    String domainOfItem = rs.getString("domain_name");
                    rs.close();

                    prep = db.conn.prepareStatement(
                            "SELECT * "
                            + "FROM attribute "
                            + "WHERE attribute_name = ?"
                            + "AND domain_name = ?;");
                    prep.setString(1, attribute_name);
                    prep.setString(2, domainOfItem);
                    rs = prep.executeQuery();

                    if (rs.next()) {

                        prep = db.conn.prepareStatement(
                                "INSERT INTO value VALUES (?,?,?,?);");
                        prep.setInt(1, itemId);
                        prep.setString(2, attribute_name);
                        prep.setString(3, rs.getString("domain_name"));
                        prep.setString(4, value);
                        prep.execute();
                        rs.close();
                        type = "success";
                        string = "A new item value was created.";

                    } else {
                        rs.close();
                        type = "error";
                        string = "There is no attribute with the name "
                                + attribute_name + " that has the same domain "
                                + "name as the given item with id " + item_id;
                    }
                } else {
                    rs.close();
                    type = "error";
                    string = "There is no item with the id " + item_id + ".";
                }

            }
        } catch (Exception e) {
            System.out.println(e);
            type = "error";
            string = e.toString();
        }
        return new MessageDAO(type, string);
    }

    public static String getItemValue(String item_id, String attribute_name) {
        
        String value = null;
        try {
            int itemId = Integer.parseInt(item_id);
            //Connect to SQLite database
            db = new SQLite().connect();

            //Create prepared statement
            PreparedStatement prep = db.conn.prepareStatement(
                    "SELECT value "
                    + "FROM value "
                    + "WHERE item_id = ? "
                    + "AND attribute_name = ?;");
            prep.setInt(1, itemId);
            prep.setString(2, attribute_name);
            ResultSet rs = prep.executeQuery();

            if (rs.next()) {
                value = rs.getString("value");
            }

            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return value;
    }
}
