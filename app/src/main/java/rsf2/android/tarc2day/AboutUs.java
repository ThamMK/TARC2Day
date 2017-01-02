package rsf2.android.tarc2day;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;

import com.google.gson.Gson;

public class AboutUs extends AppCompatActivity {

    private TextView aboutUsTextView;
    private String name;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        getUserData();

        aboutUsTextView = (TextView) findViewById(R.id.textViewAboutUs);
        name = user.getName();

        aboutUsTextView.setText("Welcome to TARC2DAY, " + name +
                                "\n\nTARC2DAY is an android app that allows you to experience many activities in TAR College." +
                                " You can register to join any events and society in TAR College by using this app. All you need to" +
                                " do is just browse through and click buttons! " +
                                "\n\nWe also have a list of promotion which is held around the area near TAR College, " +
                                "varies from food vouchers, gift vouchers, discount sales. Browse through and get those promotion immediately!" +
                                "\n\nWe hope you have fun using our app. Sincerely," +
                                "\nTARC2DAY team.");
    }
    protected void getUserData() {

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences",MODE_PRIVATE);
        String json = sharedPreferences.getString(Config.TAG_USER, "");

        user = gson.fromJson(json,User.class);
    }
}
