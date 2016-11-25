package rsf2.android.tarc2day;

/**
 * Created by MingKeat on 25/11/2016.
 */
public class Society {

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
}
