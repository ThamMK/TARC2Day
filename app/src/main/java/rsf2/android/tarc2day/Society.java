package rsf2.android.tarc2day;

import android.os.Parcel;
import android.os.Parcelable;

public class Society implements Parcelable{

    private String name;
    private String personInCharge;
    private String description;
    private String contactNo;
    private String email;

    public Society(String name, String personInCharge, String description, String contactNo, String email) {
        this.setName(name);
        this.setPersonInCharge(personInCharge);
        this.setDescription(description);
        this.setContactNo(contactNo);
        this.setEmail(email);
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

}
