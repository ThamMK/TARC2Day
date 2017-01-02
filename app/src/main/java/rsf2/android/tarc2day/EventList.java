package rsf2.android.tarc2day;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventList extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private List<Event> eventList = new ArrayList<Event>();
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    private int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewEventList);

        new BackgroundTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);

        MenuItem searchItem = menu.findItem(R.id.search);


        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                eventAdapter.getFilter();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        });

        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if ( TextUtils.isEmpty ( newText ) ) {
            eventAdapter.getFilter().filter("");
        } else {
            eventAdapter.getFilter().filter(newText.toString());
        }
        return true;

    }

    class BackgroundTask extends AsyncTask<Void,Void,String>{
        String json_url;
        String JSON_STRING;
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            json_url = "http://thammingkeat.esy.es/GetEvent.php"; //th php url
            loading = ProgressDialog.show(EventList.this, "Loading Events", "Please wait...",true,true);
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
                jsonArray = new JSONArray (result);
                String eventId, name, description, startDate, endDate, startTime, endTime, email, contactNumber, societyId, societyName,locationName,encodedImage,locationId;
                String uri;
                Double price;
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject JO = jsonArray.getJSONObject(i);
                    eventId = JO.getString("eventId");
                    name = JO.getString("name");
                    description = JO.getString("description");
                    startDate = JO.getString("startDate");
                    endDate = JO.getString("endDate");
                    startTime = JO.getString("startTime");
                    endTime = JO.getString("endTime");
                    price = Double.parseDouble(JO.getString("price"));
                    email = JO.getString("email");
                    contactNumber = JO.getString("contactNumber");
                    societyId = JO.getString("societyId");
                    societyName = JO.getString("societyName");
                    locationId = JO.getString("locationId");
                    locationName = JO.getString("locationName");

                    //encodedImage = JO.getString("image");
                    uri = JO.getString("imageUrl");

                    Event event = new Event(eventId,name,description,startDate,endDate,startTime,endTime,societyName,price,contactNumber,email,locationId,locationName,uri);
                    eventList.add(event);

                }
                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                width = displaymetrics.widthPixels;
                height = dpToPx(200);

            }
            catch(JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            eventAdapter = new EventAdapter(eventList,width,height);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(eventAdapter);
           loading.dismiss();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



    private Bitmap scale(Bitmap b) {
        return Bitmap.createScaledBitmap(b, recyclerView.getWidth(), dpToPx(120) , false);
    }

    /*public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
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
    }*/

    public int dpToPx(int dp) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return Math.round(px);
    }

}