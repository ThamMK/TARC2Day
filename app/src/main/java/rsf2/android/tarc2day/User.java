package rsf2.android.tarc2day;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by MingKeat on 18/12/2016.
 */

public class User {

    private String username;
    private String name;
    private String email;
    private String dateOfBirth;
    private String contactNumber;
    private Bitmap profilePicture;
    private String password;

    public User() {

    }

    public User(String username, String name, String dateOfBirth, String contactNo, String email, String password) {
        this.username = username;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNo;
        this.email = email;
        this.password = password;
    }

    public User(String username, String name, String dateOfBirth, String contactNo, String email, Bitmap profilePicture) {
        this.username = username;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNo;
        this.email = email;
        this.profilePicture = profilePicture;
    }

    public User(String username, String name, String dateOfBirth, String contactNo, String email, Bitmap profilePicture, String password) {
        this.username = username;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNo;
        this.email = email;
        this.profilePicture = profilePicture;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContactNo() {
        return contactNumber;
    }

    public void setContactNo(String contactNo) {
        this.contactNumber = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
