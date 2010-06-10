package de.wwu.DAO;

import java.io.Serializable;

/**
 *
 * @author Gunnar
 */
public class MessageDAO implements Serializable{

    //Type of message error or success
    public String type = "";
    //Message string
    public String string = "";


    public MessageDAO(){}

    public MessageDAO(String type, String string)
    {
        this.type = type;
        this.string = string;
    }


}
