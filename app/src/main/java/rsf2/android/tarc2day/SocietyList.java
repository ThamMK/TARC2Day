package rsf2.android.tarc2day;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSocietyList);



        new SocietyList.BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void,Void,String> {

        String json_url;
        String JSON_STRING;
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            json_url = "http://thammingkeat.esy.es/GetSociety.php"; //th php url
            loading = ProgressDialog.show(SocietyList.this, "Loading Society", "Please wait...",true,true);
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
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
                result =  stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

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

                    Society society = new Society(societyName,societyPersonInCharge,societyDescription,societyContactNo,societyEmail,encodedImage);
                    societyList.add(society);
                }

                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                int width = displaymetrics.widthPixels;
                int height = dpToPx(200);
                societyAdapter = new SocietyAdapter(societyList,width,height);

            }
            catch(JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(societyAdapter);
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
