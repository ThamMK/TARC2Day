package rsf2.android.tarc2day;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PromotionList extends AppCompatActivity {

    private List<Promotion> promotionList = new ArrayList<Promotion>();
    private RecyclerView recyclerView;
    private PromotionAdapter promotionAdapter;
    JSONArray jsonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPromotionList);



        new PromotionList.BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void,Void,String> {

        String json_url;
        String JSON_STRING;
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            json_url = "http://thammingkeat.esy.es/GetPromotion.php"; //th php url
            loading = ProgressDialog.show(PromotionList.this, "Loading Promotion", "Please wait...",true,true);

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
                jsonArray = new JSONArray(result);
                String title, description,startDate, endDate, startTime, endTime, contactNo, email, location, encodedImage;
                double price;
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject JO = jsonArray.getJSONObject(i);
                    title = JO.getString("name");
                    description = JO.getString("description");
                    startDate = JO.getString("startDate");
                    endDate = JO.getString("endDate");
                    startTime = JO.getString("startTime");
                    endTime = JO.getString("endTime");
                    price = Double.parseDouble(JO.getString("price"));
                    contactNo = JO.getString("contactNumber");
                    email = JO.getString("email");
                    location = JO.getString("location");
                    encodedImage = JO.getString("image");

                    Promotion promotion = new Promotion(title, description,startDate, endDate, startTime, endTime,price, contactNo, email, location,encodedImage);
                    promotionList.add(promotion);
                }
                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                int width = displaymetrics.widthPixels;
                int height = dpToPx(200);
                promotionAdapter = new PromotionAdapter(promotionList,width, height);
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
            recyclerView.setAdapter(promotionAdapter);
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
