package rsf2.android.tarc2day;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Society implements Parcelable{

    private String name;
    private String personInCharge;
    private String description;
    private String contactNo;
    private String email;
    private Bitmap image;
    private String imageUrl;

    public Society(String name, String personInCharge, String description, String contactNo, String email) {
        this.name = name;
        this.personInCharge = personInCharge;
        this.description = description;
        this.contactNo = contactNo;
        this.email = email;
    }

    public Society(String name, String personInCharge, String description, String contactNo, String email, Bitmap image, String imageUrl) {
        this.name = name;
        this.personInCharge = personInCharge;
        this.description = description;
        this.contactNo = contactNo;
        this.email = email;
        this.image = image;
        this.imageUrl = imageUrl;
    }

    public Society(String name, String personInCharge, String description, String contactNo, String email, Bitmap image) {
        this.setName(name);
        this.setPersonInCharge(personInCharge);
        this.setDescription(description);
        this.setContactNo(contactNo);
        this.setEmail(email);
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(personInCharge);
        dest.writeString(description);
        dest.writeString(contactNo);
        dest.writeString(email);

    }

    public static final Parcelable.Creator<Society> CREATOR = new Parcelable.Creator<Society>(){
        public Society createFromParcel(Parcel in) {
            return new Society(in);
        }

        public Society[] newArray(int size) {
            return new Society[size];
        }
    };

    private Society(Parcel in){
        name = in.readString();
        personInCharge = in.readString();
        description = in.readString();
        contactNo = in.readString();
        email = in.readString();
    }

    public static Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
