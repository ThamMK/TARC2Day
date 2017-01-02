package rsf2.android.tarc2day;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shiangyoung on 25/12/2016.
 */

public class RegisterdEvent extends Event{
    String qrCode;

    public RegisterdEvent(String id, String title, String eventDescription, String startDate, String endDate, String startTime, String endTime, String society, double price, String contactNo, String email, String locationId, String location, String imageUrl, String qrCode) {
        super(id, title, eventDescription, startDate, endDate, startTime, endTime, society, price, contactNo, email, locationId ,location, imageUrl);
        this.qrCode = qrCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public static final Parcelable.Creator<RegisterdEvent> CREATOR = new Parcelable.Creator<RegisterdEvent>() {
        public RegisterdEvent createFromParcel(Parcel in) {
            return new RegisterdEvent(in);
        }

        public RegisterdEvent[] newArray(int size) {
            return new RegisterdEvent[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(qrCode);
    }

    private RegisterdEvent(Parcel in) {
        super(in);
        qrCode = in.readString();
    }
}
