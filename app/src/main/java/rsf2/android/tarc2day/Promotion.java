package rsf2.android.tarc2day;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by MingKeat on 26/11/2016.
 */

public class Promotion implements Parcelable{

    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private double price;
    private String contactNo;
    private String location;

    public Promotion() {

    }

    public Promotion(String title, String description, String startDate, String endDate, double price, String contactNo, String location) {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeDouble(price);
        dest.writeString(contactNo);
        dest.writeString(location);
    }

    public static final Parcelable.Creator<Promotion> CREATOR = new Parcelable.Creator<Promotion>(){
        public Promotion createFromParcel(Parcel in) {
            return new Promotion(in);
        }

        public Promotion[] newArray(int size) {
            return new Promotion[size];
        }
    };

    private Promotion(Parcel in){
        title = in.readString();
        description = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        price = in.readDouble();
        contactNo = in.readString();
        location = in.readString();
    }
}
