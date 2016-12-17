package rsf2.android.tarc2day;

/**
 * Created by MingKeat on 18/12/2016.
 */

public class User {

    private String username;
    private String name;
    private String dateOfBirth;
    private String contactNo;
    private String email;

    public User() {

    }

    public User(String username, String name, String dateOfBirth, String contactNo, String email) {
        this.username = username;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.contactNo = contactNo;
        this.email = email;
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
}
