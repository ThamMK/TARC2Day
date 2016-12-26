package rsf2.android.tarc2day;

import android.app.LocalActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Event event = intent.getParcelableExtra("EVENT");
        getDetails(event);

        tabLayout = (TabLayout) findViewById(R.id.tabLayoutEventInfo);
        tabLayout.addTab(tabLayout.newTab().setText("Details"));
        tabLayout.addTab(tabLayout.newTab().setText("Location"));
        tabLayout.addTab(tabLayout.newTab().setText("Comment"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



    }

    class BackgroundTask extends AsyncTask<String,Void,Bitmap> {


        @Override
        protected Bitmap doInBackground(String... params) {
            //Pass in the event id
            //return getBitmapFromURL(params[0]);
            try {
                return Picasso.with(EventInfo.this).load(params[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {


            imageView = (ImageView) findViewById(R.id.imageViewEventInfo);

            //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1600, 1600);
            //imageView.setLayoutParams(layoutParams);

           // imageView.getLayoutParams().height = 450;

            imageView.setImageBitmap(bitmap);
            //imageView.requestLayout();
        }
    }

    class BackgroundLocationTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            //Param 0 is location ID
            RequestHandler requestHandler = new RequestHandler();
            String response = requestHandler.sendGetRequestParam(Config.URL_GET_LOCATION,params[0]);


            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject JO = jsonArray.getJSONObject(0);
                locationName = JO.getString("name");
                locationLat = JO.getString("latitude");
                locationLong = JO.getString("longitude");

                Toast.makeText(getApplicationContext(),locationName,Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }



            final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerEventInfo);
            final EventInfoAdapter adapter = new EventInfoAdapter
                    (getSupportFragmentManager(), tabLayout.getTabCount(), event,locationName,locationLat,locationLong);
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

    protected void getDetails(Event event) {

        this.event = event;
        BackgroundLocationTask backgroundLocationTask = new BackgroundLocationTask();
        backgroundLocationTask.execute(event.getLocationId());
        textViewTitle = (TextView) findViewById(R.id.textViewEventInfoName);
        textViewTitle.setText(event.getTitle());

        textViewTime = (TextView) findViewById(R.id.textViewEventInfoTime);
        textViewTime.setText(event.getStartTime() + " - " + event.getEndTime());

        textViewDate = (TextView) findViewById(R.id.textViewEventInfoDate);
        textViewDate.setText(event.getStartDate() + " - " + event.getEndDate());

        textViewPrice = (TextView) findViewById(R.id.textViewEventInfoPrice);
        textViewPrice.setText("RM " + event.getPrice());

        textViewContact = (TextView) findViewById(R.id.textViewEventInfoContact);
        textViewContact.setText(event.getContactNo());

        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(event.getImageUrl());


    }



    public static Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void registerEvent(View view){
        final Intent intent = new Intent(this,GenerateQR.class);
        intent.putExtra("registerEvent",(Parcelable) event);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Event Registration");
        builder.setMessage("Do you confirm want to register event : " + event.getTitle());
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(intent);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
