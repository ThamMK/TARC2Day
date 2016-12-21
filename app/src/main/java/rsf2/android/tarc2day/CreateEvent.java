package rsf2.android.tarc2day;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class CreateEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {

    public int selectedDateTextView; //Need to know which date to set
    public int selectedTimeTextView;
    public TextView textViewDate;
    Spinner spinnerLocation;
    Spinner spinnerSociety;
    ImageView eventsImage;
    EditText editTextEventName,editTextEmail,editTextContactNum,editTextPrice,editTextDescription;
    TextView textViewStartDate,textViewEndDate,textViewStartTime,textViewEndTime;


    private int PICK_IMAGE_REQUEST = 1;
    private ArrayList<String> location;
    private ArrayList<String> society;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        //Declaration of resources
        spinnerLocation = (Spinner)findViewById(R.id.spinnerLocation);
        spinnerSociety = (Spinner)findViewById(R.id.spinnerSociety);
        eventsImage = (ImageView)findViewById(R.id.createEventImageFile);
        editTextEventName = (EditText)findViewById(R.id.editTextEventTitle);
        editTextEmail = (EditText)findViewById(R.id.editTextCreateEventEmail);
        editTextContactNum = (EditText)findViewById(R.id.editTextCreateEventContact);
        editTextPrice = (EditText)findViewById(R.id.editTextCreateEventTicketPrice);
        editTextDescription = (EditText)findViewById(R.id.editTextCreateEventDetails);
        textViewStartDate = (TextView)findViewById(R.id.textViewCreateEventStartingDate);
        textViewEndDate = (TextView)findViewById(R.id.textViewCreateEventEndDate);
        textViewStartTime = (TextView)findViewById(R.id.textViewCreateEventStartingTime);
        textViewEndTime = (TextView)findViewById(R.id.textViewCreateEventEndTime);

        spinnerSociety.setOnItemSelectedListener(this);
        spinnerLocation.setOnItemSelectedListener(this);

        new BackgroundLocationTask().execute();
        new BackgroundSocietyTask().execute();

    }

    protected void onClickImageView(View view) {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.createEventImageFile);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onClickDateTextView(View view) {
        //Get the id of the selected text view
        selectedDateTextView = view.getId();

        //Create the dialog fragment to be displayed
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getFragmentManager(),"Date Picker");


    }

    protected void onClickTimeTextView(View view) {
        selectedTimeTextView = view.getId();

        DialogFragment timeFragment = new TimePickerFragment();
        timeFragment.show(getFragmentManager(),"Time Picker");
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //Switches between the selected view for the date picker
        switch(selectedDateTextView) {
            case R.id.textViewCreatePromoStartDate :
                textViewDate = (TextView) findViewById(R.id.textViewCreatePromoStartDate);
                textViewDate.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year );

                break;
            case R.id.textViewCreatePromoEndDate :
                textViewDate = (TextView) findViewById(R.id.textViewCreatePromoEndDate);
                textViewDate.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year );
                break;
            case R.id.textViewCreateEventStartingDate :
                textViewDate = (TextView) findViewById(R.id.textViewCreateEventStartingDate);
                textViewDate.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year );
                break;
            case R.id.textViewCreateEventEndDate :
                textViewDate = (TextView) findViewById(R.id.textViewCreateEventEndDate);
                textViewDate.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year );
                break;

            default:
                break;
        }

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        switch(selectedTimeTextView) {
            case R.id.textViewCreatePromoStartTime :
                textViewDate = (TextView) findViewById(R.id.textViewCreatePromoStartTime);
                textViewDate.setText(hourOfDay + ":" + minute);

                break;
            case R.id.textViewCreatePromoEndTime :
                textViewDate = (TextView) findViewById(R.id.textViewCreatePromoEndTime);
                textViewDate.setText(hourOfDay + ":" + minute);
                break;
            case R.id.textViewCreateEventStartingTime :
                textViewDate = (TextView) findViewById(R.id.textViewCreateEventStartingTime);
                textViewDate.setText(hourOfDay + ":" + minute);
                break;

            case R.id.textViewCreateEventEndTime :
                textViewDate = (TextView) findViewById(R.id.textViewCreateEventEndTime);
                textViewDate.setText(hourOfDay + ":" + minute);
                break;


            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class BackgroundLocationTask extends AsyncTask<Void,Void,String>{


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {
            return getLocation();
        }

        @Override
        protected void onPostExecute(String result) {
            location = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(result);

                for(int i = 0; i < jsonArray.length(); i++) {
                    String locationName = jsonArray.getJSONObject(i).getString("locationName");
                    location.add(locationName);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateEvent.this,android.R.layout.simple_spinner_item,location);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerLocation.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    class BackgroundSocietyTask extends AsyncTask<Void,Void,String>{


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {
            return getSociety();
        }

        @Override
        protected void onPostExecute(String result) {
            society = new ArrayList<>();

            try {
                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++) {
                    String societyName = jsonArray.getJSONObject(i).getString("societyName");
                    TextView textView = (TextView)findViewById(R.id.textView);
                    textView.setText(societyName);
                    society.add(societyName);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateEvent.this,android.R.layout.simple_spinner_item,society);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSociety.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    class BackgroundInsertEventTask extends AsyncTask<String,Void,String>{
        Context ctx;

        BackgroundInsertEventTask(Context context){
            this.ctx = context;
        }

        @Override
        protected String doInBackground(String... params) {
            String insert_url = "http://thammingkeat.esy.es/AddEvent.php";

            String eventTitle = params[0];
            String startDate = params[1];
            String endDate = params[2];
            String startTime = params[3];
            String endTime = params[4];
            String email = params[5];
            String contactNum = params[6];
            String society = params[7];
            String location = params[8];
            String price = params[9];
            String description = params[10];
            String encodedEventImage = params[11];

            /*try {
                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                String data = URLEncoder.encode("name","UTF-8") + "=" + URLEncoder.encode(eventTitle,"UTF-8") + "&" +
                        URLEncoder.encode("location","UTF-8") + "=" + URLEncoder.encode(location,"UTF-8") + "&" +
                        URLEncoder.encode("startDate","UTF-8") + "=" + URLEncoder.encode(startDate,"UTF-8") + "&" +
                        URLEncoder.encode("endDate","UTF-8") + "=" + URLEncoder.encode(endDate,"UTF-8") + "&" +
                        URLEncoder.encode("startTime","UTF-8") + "=" + URLEncoder.encode(startTime,"UTF-8") + "&" +
                        URLEncoder.encode("endTime","UTF-8") + "=" + URLEncoder.encode(endTime,"UTF-8") + "&" +
                        URLEncoder.encode("email","UTF-8") + "=" + URLEncoder.encode(email,"UTF-8") + "&" +
                        URLEncoder.encode("contactnumber","UTF-8") + "=" + URLEncoder.encode(contactNum,"UTF-8") + "&" +
                        URLEncoder.encode("price","UTF-8") + "=" + URLEncoder.encode(price,"UTF-8") + "&" +
                        URLEncoder.encode("description","UTF-8") + "=" + URLEncoder.encode(description,"UTF-8") + "&" +
                        URLEncoder.encode("society","UTF-8") + "=" + URLEncoder.encode(society,"UTF-8") + "&" +
                        URLEncoder.encode("image","UTF-8") + "=" + URLEncoder.encode(encodedEventImage,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                return "Registration Success";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
`           */
            return null;

        }


        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(ctx,s,Toast.LENGTH_SHORT).show();
        }

    }

    protected String getLocation() {

            //Send a get request
            RequestHandler requestHandler = new RequestHandler();
            String response = requestHandler.sendGetRequest(Config.URL_GET_LOCATION_NAME);



        return response;
    }

    protected String getSociety() {

        RequestHandler requestHandler = new RequestHandler();
        String response = requestHandler.sendGetRequest(Config.URL_GET_SOCIETY_NAME);

        return response;
    }

    public void submitEvent(View view){
        String eventTitle,startDate,endDate,startTime,endTime,email,contactNum,society,location,price,description;

        eventTitle = editTextEventName.getText().toString();
        startDate = textViewStartDate.getText().toString();
        endDate = textViewEndDate.getText().toString();
        startTime = textViewStartTime.getText().toString();
        endTime = textViewEndTime.getText().toString();
        email = editTextEmail.getText().toString();
        contactNum = editTextContactNum.getText().toString();
        society = spinnerSociety.getSelectedItem().toString();
        location = spinnerLocation.getSelectedItem().toString();
        price = editTextPrice.getText().toString();
        description = editTextDescription.getText().toString();

        Bitmap bitmapEventImage = ((BitmapDrawable)eventsImage.getDrawable()).getBitmap();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmapEventImage.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        byte[] b = bytes.toByteArray();

        String encodedImage = Base64.encodeToString(b , Base64.DEFAULT);

        BackgroundInsertEventTask backgroundInsertEventTask = new BackgroundInsertEventTask(this);
        backgroundInsertEventTask.execute(eventTitle,startDate,endDate,startTime,endTime,email,contactNum,society,location,price,description,encodedImage);
    }

}
