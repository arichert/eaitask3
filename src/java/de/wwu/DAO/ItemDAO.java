/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.wwu.DAO;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author florian
 */
public class ItemDAO implements Serializable
{
    public int item_id = 0;
    public String domain_name = "";

    public HashMap<String,String> attributes;

    public ItemDAO()
    {
        attributes = new HashMap<String,String>();
    }

    public ItemDAO(int item_id, String domain_name, 
            HashMap<String,String> attributes)
    {
        this.item_id = item_id;
        this.domain_name = domain_name;
        this.attributes = attributes;
    }

    public void addAttribute(String attribute, String value)
    {
        attributes.put(attribute, value);
    }
}
