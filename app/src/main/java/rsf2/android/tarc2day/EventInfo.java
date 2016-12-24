package rsf2.android.tarc2day;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Event event = intent.getParcelableExtra("EVENT");
        getDetails(event);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutEventInfo);
        tabLayout.addTab(tabLayout.newTab().setText("Details"));
        tabLayout.addTab(tabLayout.newTab().setText("Location"));
        tabLayout.addTab(tabLayout.newTab().setText("Comment"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerEventInfo);
        final EventInfoAdapter adapter = new EventInfoAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), event);
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

    protected void getDetails(Event event) {



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
}
