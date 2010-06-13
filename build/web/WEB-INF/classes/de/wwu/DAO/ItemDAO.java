/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.wwu.DAO;

import java.io.Serializable;

/**
 *
 * @author florian
 */
public class ItemDAO implements Serializable{
    public int item_id;
    public String domain_name = "";

    public ItemDAO(int item_id, String domain_name){
        this.item_id = item_id;
        this.domain_name = domain_name;
    }
}
