package rsf2.android.tarc2day;

import java.util.Date;

/**
 * Created by MingKeat on 26/11/2016.
 */

public class Promotion {

    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private double price;
    private String contactNo;
    private String location;

    public Promotion() {

    }

    public Promotion(String title, String description, Date startDate, Date endDate, double price, String contactNo, String location) {
        this.setTitle(title);
        this.setDescription(description);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setPrice(price);
        this.setContactNo(contactNo);
        this.setLocation(location);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
