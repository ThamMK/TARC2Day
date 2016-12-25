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
import java.util.Date;
import java.util.List;

public class SocietyList extends AppCompatActivity {

    private List<Society> societyList = new ArrayList<Society>();
    private RecyclerView recyclerView;
    private SocietyAdapter societyAdapter;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSocietyList);



        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void,Void,String> {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {
            json_url = "http://thammingkeat.esy.es/GetSociety.php"; //th php url
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
            try {
                jsonArray = new JSONArray(result);
                String societyName,societyDescription,societyPersonInCharge,societyContactNo,societyEmail, encodedImage;
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject JO = jsonArray.getJSONObject(i);
                    societyName = JO.getString("name");
                    societyDescription = JO.getString("description");
                    societyPersonInCharge = JO.getString("personInCharge");
                    societyContactNo = JO.getString("contactNumber");
                    societyEmail = JO.getString("email");
                    encodedImage = JO.getString("image");

                    Society society = new Society(societyName,societyPersonInCharge,societyDescription,societyContactNo,societyEmail,Society.base64ToBitmap(encodedImage));
                    societyList.add(society);
                }

                societyAdapter = new SocietyAdapter(societyList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(societyAdapter);

            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
