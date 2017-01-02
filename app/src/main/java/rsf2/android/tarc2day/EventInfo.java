package rsf2.android.tarc2day;

import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class EventInfo extends AppCompatActivity implements EventDetailFragment.OnFragmentInteractionListener,
        EventLocationFragment.OnFragmentInteractionListener, EventQuestionFragment.OnFragmentInteractionListener{
    private ImageView imageView;
    private TextView textViewTitle;
    private TextView textViewTime;
    private TextView textViewDate;
    private TextView textViewPrice;
    private TextView textViewContact;
    private TabHost tabHost;
    private TabLayout tabLayout;
    private static Event event;
    private String locationLat;
    private String locationLong;
    private String locationName;
    ArrayList<String> eventIdArray = new ArrayList<>();
    static Boolean checkRegisterEvent = false;
    User user;
    private Message[] messageArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences",MODE_PRIVATE);
        String json = sharedPreferences.getString(Config.TAG_USER, "");
        user = gson.fromJson(json,User.class);

        setContentView(R.layout.activity_event_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        Event event = intent.getParcelableExtra("EVENT");
        getDetails(event);

        if(event.getPrice() != 0.0){
            View b = findViewById(R.id.buttonRegisterEvent);
            b.setVisibility(View.GONE);
        }

        tabLayout = (TabLayout) findViewById(R.id.tabLayoutEventInfo);
        tabLayout.addTab(tabLayout.newTab().setText("Details"));
        tabLayout.addTab(tabLayout.newTab().setText("Location"));
        tabLayout.addTab(tabLayout.newTab().setText("Comment"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        super.onCreate(savedInstanceState);
    }

    class BackgroundLocationTask extends AsyncTask<String,Void,String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            // response at 0 is for the location
            // response at 1 is for messages
            String[] response = new String[2];
            //Param 0 is location ID
            RequestHandler requestHandler = new RequestHandler();
            response[0] = requestHandler.sendGetRequestParam(Config.URL_GET_LOCATION,params[0]);

            //Param 1 is event ID
            response[1] = requestHandler.sendGetRequestParam(Config.URL_GET_MESSAGE,params[1]);
            //Need to run both at the same time to create the page adapater after both completed

            return response;
        }

        @Override
        protected void onPostExecute(String[] response) {
            super.onPostExecute(response);

            try {
                JSONArray jsonArray = new JSONArray(response[0]);
                JSONObject JO = jsonArray.getJSONObject(0);
                locationName = JO.getString("name");
                locationLat = JO.getString("latitude");
                locationLong = JO.getString("longitude");

                JSONArray jsonMessageArray = new JSONArray(response[1]);
                Log.d("JSON MESSAGE",response[1]);
                messageArray = new Message[jsonMessageArray.length()];
                for(int i = 0; i < jsonMessageArray.length(); i++) {

                    JSONObject jsonMessage = jsonMessageArray.getJSONObject(i);
                    Log.d("JSON MESSAGE",jsonMessage.toString());
                    String messageId = jsonMessage.getString("messageId");
                    String eventId = jsonMessage.getString("eventId");
                    String username = jsonMessage.getString("username");
                    String messageText = jsonMessage.getString("message");
                    String messageDate = jsonMessage.getString("messageDate");
                    String name = jsonMessage.getString("name");
                    Message message = new Message(messageId,username,eventId,messageText,messageDate,name);
                    messageArray[i] = message;
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }



            final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerEventInfo);
            final EventInfoAdapter adapter = new EventInfoAdapter
                    (getSupportFragmentManager(), tabLayout.getTabCount(), event,locationName,locationLat,locationLong, messageArray);
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });


        }
    }

    class BackgroundMessageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //Param is event ID
            RequestHandler requestHandler = new RequestHandler();
            String response = requestHandler.sendGetRequestParam(Config.URL_GET_LOCATION,params[0]);


            return response;


        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray jsonArray = new JSONArray(s);
                Message[] messageArray = new Message[jsonArray.length()];
                for(int i = 0; i < jsonArray.length(); i++) {

                    JSONObject JO = jsonArray.getJSONObject(i);
                    messageArray[i].setUsername(JO.getString("username"));
                    messageArray[i].setEventId(JO.getString("eventId"));
                    messageArray[i].setMessage(JO.getString("message"));
                    messageArray[i].setMessageId(JO.getString("messageId"));
                    messageArray[i].setMessageDate(JO.getString("messageDate"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    protected void getDetails(Event event) {
        this.event = event;
        BackgroundLocationTask backgroundLocationTask = new BackgroundLocationTask();
        backgroundLocationTask.execute(event.getLocationId(),event.getId());
        textViewTitle = (TextView) findViewById(R.id.textViewEventInfoName);
        textViewTitle.setText(event.getTitle());

        textViewTime = (TextView) findViewById(R.id.textViewEventInfoTime);
        textViewTime.setText(event.getStartTime() + " - " + event.getEndTime());

        textViewDate = (TextView) findViewById(R.id.textViewEventInfoDate);
        textViewDate.setText(event.getStartDate() + " - " + event.getEndDate());

        textViewPrice = (TextView) findViewById(R.id.textViewEventInfoPrice);
        if(event.getPrice() == 0.0)
            textViewPrice.setText("FREE");
        else
            textViewPrice.setText("RM " + event.getPrice());

        textViewContact = (TextView) findViewById(R.id.textViewEventInfoContact);
        textViewContact.setText(event.getContactNo());

        int width = dpToPx(365);
        int height = dpToPx(200);

        imageView = (ImageView) findViewById(R.id.imageViewEventInfo) ;
        Picasso.with(this).load(event.getImageUrl()).placeholder(R.drawable.progress_animation ).resize(width,height).into(imageView);

        new BackgroundCheckEventTask().execute();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void registerEvent(View view){
        if(checkRegisterEvent) {
            Toast.makeText(EventInfo.this, "Fuck you off,dont register twice", Toast.LENGTH_SHORT).show();
        }
        else{
            checkRegisterEvent = true;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Event Registration");
            builder.setMessage("Do you confirm want to register event : " + event.getTitle());
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(EventInfo.this, GenerateQR.class);
                            intent.putExtra("registerEvent", (Parcelable) event);
                            startActivity(intent);
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    class BackgroundCheckEventTask extends AsyncTask<Void,Void,String> {

        String json_url;
        String JSON_STRING;
        JSONArray jsonArray;
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            json_url = "http://thammingkeat.esy.es/GetEventDetails.php?username=" + user.getUsername(); //th php url
            loading = ProgressDialog.show(EventInfo.this, "Loading Events", "Please wait...",true,true);
            loading.setCancelable(false);
            loading.setCanceledOnTouchOutside(false);
            checkRegisterEvent = false;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result="";
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_STRING = bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(JSON_STRING +"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                result = stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(result != null) {
                    jsonArray = new JSONArray(result);
                    String eventId;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject JO = jsonArray.getJSONObject(i);
                        eventId = JO.getString("eventId");
                        eventIdArray.add(eventId);
                    }
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            for(int i=0;i<eventIdArray.size();i++){
                if(eventIdArray.get(i).equals(event.getId())){
                    checkRegisterEvent = true;
                }
            }
            loading.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    public int dpToPx(int dp) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return Math.round(px);
    }
}
