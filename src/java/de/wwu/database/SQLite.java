package de.wwu.database;

import java.io.File;
import java.sql.*;

/**
 * Create and connect to a SQLite database
 *
 *
 * @author guth
 */
public class SQLite {

    public Connection conn;
    public String pathToDbFile = System.getProperty("user.dir") + "/test.db";

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
            Statement stat = this.conn.createStatement();

            //enable foreign key restraints (works if compiled that way, precompiled binaries should be allright)
            stat.executeUpdate("PRAGMA foreign_keys = ON;");

            //If the table "domain" exists, then it gets deleted first!
            stat.executeUpdate("drop table if exists domain;");
            //Create the table for "domain"
            stat.executeUpdate(
                    "CREATE TABLE domain (" +
                    "name VARCHAR(200) PRIMARY KEY NOT NULL);");

            PreparedStatement prep = this.conn.prepareStatement(
                    "insert into domain values (?);");

            prep.setString(1, "cars");
            prep.addBatch();
            prep.setString(1, "products");
            prep.addBatch();
            prep.setString(1, "bikes");
            prep.addBatch();

            this.conn.setAutoCommit(false);
            prep.executeBatch();
            this.conn.setAutoCommit(true);


           //Attribute
            stat = this.conn.createStatement();
            stat.executeUpdate("drop table if exists attribute;");
            //Create the table for "attribute"
            stat.executeUpdate("CREATE TABLE attribute (attribute_name VARCHAR(200),"+
                               "domain_name VARCHAR(200)," +
                               "FOREIGN KEY (domain_name)   REFERENCES domain (name)," +
                               "PRIMARY KEY (attribute_name, domain_name) );");

            prep = this.conn.prepareStatement("insert into attribute (attribute_name, domain_name) values (?, ?);");

            prep.setString(1, "brand");
            prep.setString(2, "cars");
            prep.addBatch();


            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);

            //Item
            stat = this.conn.createStatement();
            stat.executeUpdate("DROP TABLE if exists item;");
            //Create the table for "item"
            stat.executeUpdate(
                    "CREATE TABLE item (" +
                    "item_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "domain_name VARCHAR(200) NOT NULL," +
                    "FOREIGN KEY (domain_name)   REFERENCES domain (name) );");


            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);

            //Value
            stat = this.conn.createStatement();
            stat.executeUpdate("DROP TABLE if exists value;");
            //Create the table for "value"
            stat.executeUpdate(
                    "CREATE TABLE value (" +
                    "item_id INTEGER, " +
                    "domain_name VARCHAR(200)," +
                    "attribute_name VARCHAR(200)," +
                    "FOREIGN KEY (domain_name)   REFERENCES domain (name),"+
                    "FOREIGN KEY (attribute_name)   REFERENCES attribute(attribute_name)," +
                    "FOREIGN KEY (item_id)   REFERENCES item(item_id)," +
                    "PRIMARY KEY (item_id, domain_name, attribute_name));");


            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
