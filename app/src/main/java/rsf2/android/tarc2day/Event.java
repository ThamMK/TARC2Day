package rsf2.android.tarc2day;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by MingKeat on 25/11/2016.
 */

public class Event implements Parcelable{

    private String title;
    private String eventDescription;
    private String startDate;
    private String endDate;
    private String society;
    private double price;
    private String contactNo;
    private String email;

    public Event() {

    }
    public Event(String title, String eventDescription, String startDate, String endDate, String society, double price, String contactNo, String email) {
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

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(eventDescription);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(society);
        dest.writeDouble(price);
        dest.writeString(contactNo);
        dest.writeString(email);

    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>(){
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    private Event(Parcel in){
        title = in.readString();
        eventDescription = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        society = in.readString();
        price = in.readDouble();
        contactNo = in.readString();
        email = in.readString();
    }


}
