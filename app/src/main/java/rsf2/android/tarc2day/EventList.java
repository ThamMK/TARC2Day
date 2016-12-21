package rsf2.android.tarc2day;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class EventList extends AppCompatActivity {

    private List<Event> eventList = new ArrayList<Event>();
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewEventList);
        eventData();
        eventAdapter = new EventAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eventAdapter);
        new BackgroundTask().execute();
        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("0");
            int count = 0;
            String eventId, name, description, startDate, endDate, startTime, endTime,email, contactNumber, societyId, societyName;
            Double price;
            while(count<jsonObject.length())
            {
                JSONObject JO = jsonArray.getJSONObject(count);
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

                Event event = new Event(name,description,startDate,endDate,startTime,endTime,societyName,price,contactNumber,email);
                eventList.add(event);

                count++;
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void getJSON(View view)
    {
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void,Void,String>{

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {
            json_url = "http://thammingkeat.esy.es/GetEvent.php"; //th php url
        }

        @Override
        protected String doInBackground(Void... params) {
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
                return  stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            json_string = result;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private void eventData() {

        //Get event data from database

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");
        //simpleDateFormat.applyPattern("d MMM yyyy");
        Date date = new Date();

        String startDate = simpleDateFormat.format(date);
        String endDate = simpleDateFormat.format(date);


        //Society society = new Society("Computer Science Society","Ming Keat","testing","test","test2");
        //Event event = new Event("Computer Science Night", "Code", startDate,endDate,society.getName(),0,"012123","tmk@gmail.com");
        //eventList.add(event);

        //event = new Event("Hackathon", "Coding", startDate,endDate,society.getName(),0,"4","tmk@gmail.com");
        //eventList.add(event);

        //event = new Event("Idea", "Hacking", startDate,endDate,society.getName(),0,"123","tmk@gmail.com");
        //eventList.add(event);

    }
}
