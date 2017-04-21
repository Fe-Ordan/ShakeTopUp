package xyz.enableit.shaketopup.model;

import java.io.Serializable;

/**
 * Created by dinislam on 2/9/17.
 * email : milon@strativ.se
 */

public class UssdCode implements Serializable{
    private String description, shortCode;
    private int type, operatorID;

    public UssdCode(String description, String shortCode, int type, int operatorID) {
        this.description = description;
        this.shortCode = shortCode;
    }

    public UssdCode() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public int getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(int operatorID) {
        this.operatorID = operatorID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}