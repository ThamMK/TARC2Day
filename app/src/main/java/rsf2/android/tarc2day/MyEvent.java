package rsf2.android.tarc2day;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

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
import java.util.ArrayList;

public class MyEvent extends AppCompatActivity {

    private ArrayList<RegisterdEvent> eventList = new ArrayList<>();
    private ListView listViewMyEvent;
    TextView textViewNoEvent;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event);
        listViewMyEvent = (ListView) findViewById(R.id.listViewMyEvent);
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String json = sharedPreferences.getString(Config.TAG_USER, "");
        user = gson.fromJson(json, User.class);

        textViewNoEvent = (TextView) findViewById(R.id.textShowNoRegisterEvent);

        new BackgroundTask().execute();

    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String json_url;
        String JSON_STRING;
        JSONArray jsonArray;
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            json_url = "http://thammingkeat.esy.es/GetEventDetails.php?username=" + user.getUsername(); //th php url
            loading = ProgressDialog.show(MyEvent.this, "Loading Events", "Please wait...", true, true);
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    jsonArray = new JSONArray(result);
                    String eventId, name, description, startDate, endDate, startTime, endTime, email, contactNumber, societyId, societyName, locationName, encodedImage;
                    String uri, qrCode,locationId;
                    Double price;
                    if (jsonArray.length() <= 0) {
                        textViewNoEvent.setVisibility(View.VISIBLE);
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {
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
                            locationName = JO.getString("locationName");
                            //encodedImage = JO.getString("image");
                            locationId = JO.getString("locationId");
                            uri = JO.getString("imageUrl");
                            qrCode = JO.getString("qrCode");

                            RegisterdEvent event = new RegisterdEvent(eventId, name, description, startDate, endDate, startTime, endTime, societyName, price, contactNumber, email, locationId,locationName, uri, qrCode);
                            eventList.add(event);
                        }
                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();

            }

            final CustomEventUserListAdapter adapter = new CustomEventUserListAdapter(getApplicationContext(), R.layout.custom_event_list_layout, eventList);
            listViewMyEvent.setAdapter(adapter);

            listViewMyEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Object listItem = listViewMyEvent.getItemAtPosition(position);

                    RegisterdEvent registerdEvent = eventList.get(position);
                    Toast.makeText(MyEvent.this, registerdEvent.getTitle(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MyEvent.this, ShowEventDetail.class);
                    intent.putExtra("myRegisterEvent", (Parcelable) registerdEvent);
                    startActivity(intent);
                }
            });

            loading.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
