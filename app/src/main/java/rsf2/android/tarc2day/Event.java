package rsf2.android.tarc2day;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * Created by MingKeat on 25/11/2016.
 */

public class Event implements Parcelable{

    private String id;
    private String title;
    private String eventDescription;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String society;
    private double price;
    private String contactNo;
    private String email;
    private String locationId;
    private String location;
    private Bitmap image;
    private String imageUrl;

    public Event() {

    }

    public Event(String title, String eventDescription, String startDate, String endDate, String startTime, String endTime, String society, double price, String contactNo, String email,String location,Bitmap image) {
        this.title = title;
        this.eventDescription = eventDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.society = society;
        this.price = price;
        this.contactNo = contactNo;
        this.email = email;
        this.location = location;
        this.image = image;
    }

    public Event(String id, String title, String eventDescription, String startDate, String endDate, String startTime, String endTime, String society, double price, String contactNo, String email,String location,Bitmap image, String imageUrl) {
        this.id = id;
        this.title = title;
        this.eventDescription = eventDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.society = society;
        this.price = price;
        this.contactNo = contactNo;
        this.email = email;
        this.location = location;
        this.image = image;
        this.imageUrl = imageUrl;
    }

    public Event(String id, String title, String eventDescription, String startDate, String endDate, String startTime, String endTime, String society, double price, String contactNo, String email, String location, String imageUrl) {
        this.id = id;
        this.title = title;
        this.eventDescription = eventDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.society = society;
        this.price = price;
        this.contactNo = contactNo;
        this.email = email;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public Event(String id, String title, String eventDescription, String startDate, String endDate, String startTime, String endTime, String society, double price, String contactNo, String email, String locationId, String location, String imageUrl) {
        this.id = id;
        this.title = title;
        this.eventDescription = eventDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.society = society;
        this.price = price;
        this.contactNo = contactNo;
        this.email = email;
        this.locationId = locationId;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }




    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getId() { return id; }

    public void setId(String id) {this.id = id;}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(eventDescription);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(society);
        dest.writeDouble(price);
        dest.writeString(contactNo);
        dest.writeString(email);
        dest.writeString(imageUrl);
        dest.writeString(location);
        dest.writeString(locationId);
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>(){
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    protected Event(Parcel in){
        id = in.readString();
        title = in.readString();
        eventDescription = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        society = in.readString();
        price = in.readDouble();
        contactNo = in.readString();
        email = in.readString();
        imageUrl = in.readString();
        location = in.readString();
        locationId = in.readString();
    }

    public static Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if(bitmap.getWidth()>1000 && bitmap.getHeight()>1000){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        }
        else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        }
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}
