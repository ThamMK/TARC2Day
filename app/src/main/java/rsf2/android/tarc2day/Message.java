package rsf2.android.tarc2day;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MingKeat on 26/12/2016.
 */

public class Message implements Parcelable {

    private String messageId;
    private String username;



    private String eventId;
    private String message;
    private String  messageDate;
    private String name;

    public Message(String messageId, String username, String eventId, String message, String messageDate, String name) {
        this.messageId = messageId;
        this.username = username;
        this.eventId = eventId;
        this.message = message;
        this.messageDate = messageDate;
        this.name = name;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(messageId);
        dest.writeString(eventId);
        dest.writeString(username);
        dest.writeString(message);
        dest.writeString(messageDate);
    }

    protected Message(Parcel in) {
        messageId = in.readString();
        eventId = in.readString();
        username = in.readString();
        message = in.readString();
        messageDate = in.readString();
    }


}
