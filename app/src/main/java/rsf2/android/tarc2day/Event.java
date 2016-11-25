package rsf2.android.tarc2day;

import java.util.Date;

/**
 * Created by MingKeat on 25/11/2016.
 */

public class Event {

    private String title;
    private String eventDescription;
    private Date startDate;
    private Date endDate;
    private Society society;
    private double price;
    private String contactNo;
    private String email;

    public Event() {

    }
    public Event(String title, String eventDescription, Date startDate, Date endDate, Society society, double price, String contactNo, String email) {
        this.title = title;
        this.eventDescription = eventDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.society = society;
        this.price = price;
        this.contactNo = contactNo;
        this.email = email;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
