package xyz.enableit.shaketopup.model;

/**
 * Created by dinislam on 1/25/17.
 * email : milon@strativ.se
 */

public class Offer {
    private String title;
    private String detail;
    private String start;
    private String end;
    private String operatorID;

    public String getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
