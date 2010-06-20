/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.wwu.database;

import de.wwu.DAO.ItemDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author florian
 */
public class Search2Database
{
    private static SQLite db;

    public static List<ItemDAO> search(String value) {
        List<ItemDAO> searchItems = new ArrayList<ItemDAO>();
        try {
            //Connect to SQLite database
            db = new SQLite().connect();

            //Create prepared statement
            PreparedStatement prep = db.conn.prepareStatement(
                    "SELECT item_id "
                    + "FROM value "
                    + "WHERE value LIKE '%' || ? || '%';");
            prep.setString(1, value);
            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                searchItems.add(Item2Database.getItem(rs.getString("item_id")));
            }

            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return searchItems;
    }

    public static List<ItemDAO> search(String value, String domain_name) {
        List<ItemDAO> searchItems = new ArrayList<ItemDAO>();
        try {
            //Connect to SQLite database
            db = new SQLite().connect();

            //Create prepared statement
            PreparedStatement prep = db.conn.prepareStatement(
                    "SELECT item_id "
                    + "FROM value "
                    + "WHERE value LIKE '%' || ? || '%'"
                    + "AND domain_name = ?;");
            prep.setString(1, value);
            prep.setString(2, domain_name);
            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                searchItems.add(Item2Database.getItem(rs.getString("item_id")));
            }

            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return searchItems;
    }

    public static List<ItemDAO> search(String value, String domain_name,
            String attribute_name) {
        List<ItemDAO> searchItems = new ArrayList<ItemDAO>();
        try {
            //Connect to SQLite database
            db = new SQLite().connect();

            //Create prepared statement
            PreparedStatement prep = db.conn.prepareStatement(
                    "SELECT item_id "
                    + "FROM value "
                    + "WHERE value LIKE '%' || ? || '%'"
                    + "AND domain_name = ?"
                    + "AND attribute_name = ?;");
            prep.setString(1, value);
            prep.setString(2, domain_name);
            prep.setString(3, attribute_name);
            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                searchItems.add(Item2Database.getItem(rs.getString("item_id")));
            }

            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return searchItems;
    }

}
