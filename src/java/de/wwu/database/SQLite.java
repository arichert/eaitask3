package de.wwu.database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.LogManager;

/**
 * Create and connect to a SQLite database
 *
 *
 * @author guth
 */
public class SQLite {

    public Connection conn;
    public String pathToDbFile = System.getProperty("user.dir") + "/test.db";


    public HashMap[] getExamples()
    {
        HashMap car_example  = new HashMap();
        HashMap bike_example = new HashMap();
        HashMap product_example = new HashMap();

        HashMap[] examples = {car_example,bike_example,product_example};

        car_example.put("domain", "cars");
        car_example.put("brand", "VW");
        car_example.put("price", "10 Euro");
        car_example.put("id", null);

        bike_example.put("domain", "bikes");
        bike_example.put("brand", "Holland");
        bike_example.put("price", "5 Euro");
        bike_example.put("id", null);

        product_example.put("domain", "products");
        product_example.put("brand", "Becks");
        product_example.put("price", "2 Euro");
        product_example.put("id", null);

        return examples;
    }


    /**
     * Connect to a database
     *
     * @return
     */
    public SQLite connect() {

        try {
            //SQLite database
            Class.forName("org.sqlite.JDBC");
            //Create connection (creates the db-file if it is not yet there)
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + pathToDbFile);
            //For the first usage.....
            //this.initialSetup();
        } catch (Exception e) {
            System.out.println(e);
        }


        return this;

    }

    //Disconnect from the db
    public void disconnect() {
        try {
            this.conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Initial setup for the SQLite db!
     *
     * Here you can create your complete database model within one function!
     *
     * @throws Exception
     */
    public void initialSetup() {
        try {

            HashMap[] examples = getExamples();
            
            Statement stat = this.conn.createStatement();

            //enable foreign key restraints (works if compiled that way, precompiled binaries should be allright)
            stat.executeUpdate("PRAGMA foreign_keys = ON;");

            //If the table "domain" exists, then it gets deleted first!
            stat.executeUpdate("drop table if exists domain;");
            //Create the table for "domain"
            stat.executeUpdate(
                    "CREATE TABLE domain (" +
                    "name VARCHAR(200) PRIMARY KEY NOT NULL);");

            this.conn.setAutoCommit(false);
            this.conn.setAutoCommit(true);


           //Attribute
            stat = this.conn.createStatement();
            stat.executeUpdate("drop table if exists attribute;");
            //Create the table for "attribute"
            stat.executeUpdate("CREATE TABLE attribute (attribute_name VARCHAR(200),"+
                               "domain_name VARCHAR(200)," +
                               "FOREIGN KEY (domain_name)   REFERENCES domain (name)," +
                               "PRIMARY KEY (attribute_name, domain_name) );");

            //Item
            stat = this.conn.createStatement();
            stat.executeUpdate("DROP TABLE if exists item;");
            //Create the table for "item"
            stat.executeUpdate(
                    "CREATE TABLE item (" +
                    "item_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "domain_name VARCHAR(200) NOT NULL," +
                    "FOREIGN KEY (domain_name)   REFERENCES domain (name) );");

            //Value
            stat = this.conn.createStatement();
            stat.executeUpdate("DROP TABLE if exists value;");
            //Create the table for "value"
            stat.executeUpdate(
                    "CREATE TABLE value (" +
                    "item_id INTEGER, " +
                    "domain_name VARCHAR(200)," +
                    "attribute_name VARCHAR(200)," +
                    "value TEXT," +
                    "FOREIGN KEY (domain_name)   REFERENCES domain (name),"+
                    "FOREIGN KEY (attribute_name)   REFERENCES attribute(attribute_name)," +
                    "FOREIGN KEY (item_id)   REFERENCES item(item_id)," +
                    "PRIMARY KEY (item_id, domain_name, attribute_name));");




            //Add domains
            PreparedStatement prep = this.conn.prepareStatement("insert into domain values (?);");
            for (HashMap item : examples) {
                prep.setString(1, item.get("domain").toString());
                prep.addBatch();
            }
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);

            //Add Attributes
            prep = this.conn.prepareStatement("insert into attribute (attribute_name, domain_name) values (?, ?);");

            Iterator i;
            for (HashMap item : examples) {
                i =  item.keySet().iterator();
                String domain = item.get("domain").toString();
                while( i. hasNext() ){
                    String key = i.next().toString();
                    if ((!key.equals("domain")) && (!key.equals("id")))
                    {
                        prep.setString(1, key);
                        prep.setString(2, domain);
                        prep.addBatch();
                    }
                }
            }
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);

            //Insert Items - retrieve inserted IDs ... NOTE: Is not safe with multiple clients (use transactions?)
            prep = this.conn.prepareStatement("insert into item (domain_name) values (?);");
            for (HashMap item : examples) {
                prep.setString(1, item.get("domain").toString());
                prep.execute();
                ResultSet lastId = stat.executeQuery("SELECT last_insert_rowid();");
                lastId.next();

                //TODO : Remove
                String bla = lastId.getString(1);
                System.out.println("This is bla: " + bla);
                item.put("id", lastId.getString(1));
                lastId.close();
            }

            //Insert Values
            prep = this.conn.prepareStatement("insert into value (item_id, attribute_name, domain_name,value) values (?,?,?,?);");
            for (HashMap item : examples) {
                i =  item.keySet().iterator();
                String id = item.get("id").toString();
                String domain = item.get("domain").toString();
                System.out.println(id + "     " + domain);
                
                while( i.hasNext() ){
                    String key = i.next().toString();
                    if ((!key.equals("domain")) && (!key.equals("id"))) {
                    prep.setString(1, id);
                    prep.setString(2, key);
                    prep.setString(3, domain);
                    prep.setString(4, item.get(key).toString());

                    System.out.println(id + " " + key + " " + domain + " " + item.get(key).toString());
                    prep.addBatch();
                    }
                }
            }
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);



        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
