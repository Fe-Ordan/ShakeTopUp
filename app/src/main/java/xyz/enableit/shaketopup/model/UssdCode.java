package xyz.enableit.shaketopup.model;

/**
 * Created by dinislam on 2/9/17.
 * email : milon@strativ.se
 */

public class UssdCode {
    private String description, shortCode;

    public UssdCode(String description, String shortCode) {
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
}