package rsf2.android.tarc2day;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
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
    private Bitmap image;
    private String imageUrl;

    public Promotion() {

    }

    public Promotion(String title, String description, String startDate, String endDate, double price, String contactNo, String location, Bitmap image) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.contactNo = contactNo;
        this.location = location;
        this.image = image;
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

    public Promotion(String title, String description, String startDate, String endDate, double price, String contactNo, String location, Bitmap image, String imageUrl) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.contactNo = contactNo;
        this.location = location;
        this.image = image;
        this.imageUrl = imageUrl;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
