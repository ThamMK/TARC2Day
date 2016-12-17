package rsf2.android.tarc2day;

/**
 * Created by MingKeat on 17/12/2016.
 */
//Config file is used to manage the database connection configuration
public class Config {

    public static final String URL_ADD_USER = "http://thammingkeat.esy.es/AddUser.php";
    public static final String URL_LOGIN = "http://thammingkeat.esy.es/login.php";

    //Keys that will be used to send the request to php scripts
    //VALUE MUST BE SAME WITH THE PHP SCRIPT VALUES
    public static final String KEY_USER_USERNAME = "username";
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_PASSWORD = "password";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_DATE = "date";
    public static final String KEY_USER_CONTACT = "contactnumber";

    public static final String KEY_LOGIN_USERNAME = "username";
    public static final String KEY_LOGIN_PASSWORD = "password";



    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_DESG = "desg";
    public static final String TAG_SAL = "salary";

    //employee id to pass with intent
    public static final String USER_USERNAME = "username";
}
