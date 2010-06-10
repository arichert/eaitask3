/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.tests.ws;

import de.wwu.DAO.DomainDAO;
import de.wwu.DAO.MessageDAO;
import de.wwu.ws.Domain;
import de.wwu.ws.Maintenance;
import java.util.Iterator;
import java.util.List;
import javax.xml.ws.Endpoint;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author guth
 */
public class TestWebService {

    

    /**
     * Test the maintenance class
     */
    @Test
    public void maintenanceTest(){

        //Test for setUpDatabase:
        Maintenance server = new Maintenance();
        MessageDAO message = (MessageDAO) server.setUpDB();
        

        assertEquals(message.string,"Database was set up!");
        
    }


    /**
     * Check the createDomain method
     *
     */
    @Test
    public void createDomainTest(){

        //Create a new domain:
        Domain domain = new Domain();
        MessageDAO message = (MessageDAO)domain.createDomain("testdomain");

        //Check return value
        assertEquals(message.string,"Domain testdomain was created.");

        //Get List of Domains:
        List<DomainDAO> domains = domain.listDomains();
        
        //Check the name of the fourth domain!
        assertEquals(domains.get(3).name,"testdomain");
        
    }
}