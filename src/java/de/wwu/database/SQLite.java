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

            //If the table "domain" exists, then it gets deleted first!
            Statement stat = this.conn.createStatement();
            stat.executeUpdate("drop table if exists domain;");
            //Create the table for "domain"
            stat.executeUpdate("create table domain (name);");

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
            //Create the table for "domain"
            stat.executeUpdate("create table attribute (attribute_name, domain_name);");

            prep = this.conn.prepareStatement("insert into attribute (attribute_name, domain_name) values (?, ?);");

            prep.setString(1, "brand");
            prep.setString(2, "cars");
            prep.addBatch();


            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
