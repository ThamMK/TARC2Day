package rsf2.android.tarc2day;

/**
 * Created by MingKeat on 17/12/2016.
 */
//Config file is used to manage the database connection configuration
public class Config {

    public static final String URL_ADD_USER = "http://thammingkeat.esy.es/AddUser.php";
    public static final String URL_LOGIN = "http://thammingkeat.esy.es/login.php";
    public static final String URL_ADD_EVENT = "http://thammingkeat.esy.es/AddEvent.php";
    public static final String URL_ADD_SOCIETY = "http://thammingkeat.esy.es/AddSociety.php";
    public static final String URL_ADD_PROMO = "http://thammingkeat.esy.es/AddPromotion.php";
    public static final String URL_GET_LOCATION_NAME = "http://thammingkeat.esy.es/getLocationName.php";
    public static final String URL_GET_SOCIETY_NAME = "http://thammingkeat.esy.es/getSocietyName.php";
    public static final String URL_GET_EVENT_IMAGE = "http://thammingkeat.esy.es/getImageEvent.php";
    public static final String URL_GET_LOCATION = "http://thammingkeat.esy.es/GetLocation.php?locationId=";
    public static final String URL_CHECK_ADMIN = "http://thammingkeat.esy.es/checkAdmin.php?username=";
    public static final String URL_ADD_EVENTDETAILS = "http://thammingkeat.esy.es/AddEventDetails.php";
    public static final String URL_FORGET_PASSWORD = "http://thammingkeat.esy.es/ForgetPassword.php";
    public static final String URL_GET_MESSAGE = "http://thammingkeat.esy.es/GetMessage.php?eventId=";
    public static final String URL_POST_MESSAGE = "http://thammingkeat.esy.es/PostMessage.php";
    public static final String URL_GET_EVENT_MAIN = "http://thammingkeat.esy.es/GetEventLimitDate.php";
    public static final String URL_GET_USER = "http://thammingkeat.esy.es/GetUser.php";
    public static final String URL_UPDATE_USER = "http://thammingkeat.esy.es/UpdateUser.php";
    public static final String URL_UPDATE_USER_PASSWORD= "http://thammingkeat.esy.es/UpdateUserPassword.php";
    //Keys that will be used to send the request to php scripts
    //VALUE MUST BE SAME WITH THE PHP SCRIPT VALUES
    public static final String KEY_USER_USERNAME = "username";
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_PASSWORD = "password";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_DATE = "date";
    public static final String KEY_USER_CONTACT = "contactnumber";
    public static final String KEY_USER_PROFILE = "profilePicture";

    // Event entity key
    public static final String KEY_EVENT_NAME = "name";
    public static final String KEY_EVENT_DESCRIPTION = "description";
    public static final String KEY_EVENT_START_DATE = "startDate";
    public static final String KEY_EVENT_END_DATE = "endDate";
    public static final String KEY_EVENT_START_TIME = "startTime";
    public static final String KEY_EVENT_END_TIME= "endTime";
    public static final String KEY_EVENT_EMAIL = "email";
    public static final String KEY_EVENT_CONTACT_NUMBER = "contactNumber";
    public static final String KEY_EVENT_LOCATION = "location";
    public static final String KEY_EVENT_PRICE = "price";
    public static final String KEY_EVENT_IMAGE = "image";
    public static final String KEY_EVENT_SOCIETY = "society";

    // Society entity key
    public static final String KEY_SOCIETY_NAME = "name";
    public static final String KEY_SOCIETY_DESCRIPTION = "description";
    public static final String KEY_SOCIETY_PERSON_IN_CHARGE = "personInCharge";
    public static final String KEY_SOCIETY_CONTACT_NUMBER = "contactNumber";
    public static final String KEY_SOCIETY_EMAIL = "email";
    public static final String KEY_SOCIETY_IMAGE = "image";

    // Promotion entity key
    public static final String KEY_PROMO_NAME = "name";
    public static final String KEY_PROMO_DESCRIPTION = "description";
    public static final String KEY_PROMO_START_DATE = "startDate";
    public static final String KEY_PROMO_END_DATE = "endDate";
    public static final String KEY_PROMO_START_TIME = "startTime";
    public static final String KEY_PROMO_END_TIME= "endTime";
    public static final String KEY_PROMO_EMAIL = "email";
    public static final String KEY_PROMO_CONTACT_NUMBER = "contactNumber";
    public static final String KEY_PROMO_LOCATION = "location";
    public static final String KEY_PROMO_PRICE = "price";
    public static final String KEY_PROMO_IMAGE = "image";

    public static final String KEY_LOGIN_USERNAME = "username";
    public static final String KEY_LOGIN_PASSWORD = "password";

    public static  final String KEY_REGISTEREVENT_ID = "eventId";
    public  static final String KEY_REGISTEREVENT_USERNAME = "username";

    public static final  String KEY_FORGETPASS_EMAIL = "email";
    public static final  String KEY_FORGETPASS_PASSWORD = "password";

    public static final  String KEY_REGISTEREVENT_QRCODE = "qrCode";

    //JSON Tags
    public static final String TAG_USER = "USER"; //Store the user data
    public static final String TAG_USERNAME = "Username";
    public static final String TAG_NAME = "Name";
    public static final String TAG_EMAIL = "Email";
    public static final String TAG_DOB = "DateofBirth";
    public static final String TAG_CONTACT = "ContactNumber";
    public static final String TAG_PASSWORD = "Password";

    //Check if user is an admin
    public static final String TAG_ADMIN = "admin";

}
