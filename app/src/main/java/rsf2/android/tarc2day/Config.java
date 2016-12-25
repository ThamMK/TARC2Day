package rsf2.android.tarc2day;

/**
 * Created by MingKeat on 17/12/2016.
 */
//Config file is used to manage the database connection configuration
public class Config {

    public static final String URL_ADD_USER = "http://thammingkeat.esy.es/AddUser.php";
    public static final String URL_LOGIN = "http://thammingkeat.esy.es/login.php";
    public static final String URL_ADD_EVENT = "http://thammingkeat.esy.es/AddEvent.php";
    public static final String URL_GET_LOCATION_NAME = "http://thammingkeat.esy.es/getLocationName.php";
    public static final String URL_GET_SOCIETY_NAME = "http://thammingkeat.esy.es/getSocietyName.php";
    public static final String URL_GET_EVENT_IMAGE = "http://thammingkeat.esy.es/getImageEvent.php";
    public static final String URL_GET_LOCATION = "http://thammingkeat.esy.es/GetLocation.php?locationId=";
    public static final String URL_ADD_EVENTDETAILS = "http://thammingkeat.esy.es/AddEventDetails.php";

    //Keys that will be used to send the request to php scripts
    //VALUE MUST BE SAME WITH THE PHP SCRIPT VALUES
    public static final String KEY_USER_USERNAME = "username";
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_PASSWORD = "password";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_DATE = "date";
    public static final String KEY_USER_CONTACT = "contactnumber";


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


    public static final String KEY_LOGIN_USERNAME = "username";
    public static final String KEY_LOGIN_PASSWORD = "password";

    public static  final String KEY_REGISTEREVENT_ID = "eventId";
    public  static final String KEY_REGISTEREVENT_USERNAME = "username";
    public static final  String KEY_REGISTEREVENT_QRCODE = "qrCode";

    //JSON Tags
    public static final String TAG_USER = "USER"; //Store the user data
    public static final String TAG_USERNAME = "Username";
    public static final String TAG_NAME = "Name";
    public static final String TAG_EMAIL = "Email";
    public static final String TAG_DOB = "DateofBirth";
    public static final String TAG_CONTACT = "ContactNumber";

    //employee id to pass with intent
    public static final String USER_USERNAME = "username";
}
