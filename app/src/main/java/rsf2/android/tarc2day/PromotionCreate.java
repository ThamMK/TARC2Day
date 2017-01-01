package rsf2.android.tarc2day;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static android.R.attr.data;

public class PromotionCreate extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    public int selectedDateTextView; //Need to know which date to set
    public int selectedTimeTextView;
    public TextView textViewDate;
    Spinner spinnerLocation;
    ImageView imageViewPromotionCreate;
    EditText editTextPromotionTitle, editTextCreatePromoEmail,editTextCreatePromoContact,editTextCreatePromoPrice,editTextCreatePromoDetail, editTextCreatePromoLocation;
    TextView textViewCreatePromoStartDate,textViewCreatePromoEndDate,textViewCreatePromoStartTime,textViewCreatePromoEndTime;
    private final static int RESULT_SELECT_IMAGE = 100;

    private ArrayList<String> location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Declaration of resources
        imageViewPromotionCreate = (ImageView) findViewById(R.id.imageViewPromotionCreate);
        editTextPromotionTitle = (EditText) findViewById(R.id.editTextPromotionTitle);
        editTextCreatePromoEmail = (EditText) findViewById(R.id.editTextCreatePromoEmail);
        editTextCreatePromoContact = (EditText) findViewById(R.id.editTextCreatePromoContact);
        editTextCreatePromoPrice = (EditText) findViewById(R.id.editTextCreatePromoPrice);
        editTextCreatePromoDetail = (EditText) findViewById(R.id.editTextCreatePromoDetail);
        textViewCreatePromoStartDate = (TextView) findViewById(R.id.textViewCreatePromoStartDate);
        textViewCreatePromoEndDate = (TextView) findViewById(R.id.textViewCreatePromoEndDate);
        textViewCreatePromoStartTime = (TextView) findViewById(R.id.textViewCreatePromoStartTime);
        textViewCreatePromoEndTime = (TextView) findViewById(R.id.textViewCreatePromoEndTime);
        editTextCreatePromoLocation = (EditText) findViewById(R.id.editTextCreatePromoLocation);

    }
    protected void onClickImageView(View view) {

        try{
            //Pick Image From Gallery
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_SELECT_IMAGE);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri uri = data.getData();

            doCrop(uri);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            //Bitmap bitmap = CropImageView
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    bitmap = scale(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imageViewPromotionCreate);
                imageView.setImageBitmap(bitmap);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
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

            default:
                break;
        }
    }



    class BackgroundLocationTask extends AsyncTask<Void,Void,String> {

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

                for (int i = 0; i < jsonArray.length(); i++) {
                    String locationName = jsonArray.getJSONObject(i).getString("locationName");
                    location.add(locationName);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(PromotionCreate.this, android.R.layout.simple_spinner_item, location);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerLocation.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    protected String getLocation() {

        //Send a get request
        RequestHandler requestHandler = new RequestHandler();
        String response = requestHandler.sendGetRequest(Config.URL_GET_LOCATION_NAME);



        return response;
    }

    class BackgroundInsertPromoTask extends AsyncTask<String,Void,String>{
        Context ctx;
        String date;
        ProgressDialog loading;

        BackgroundInsertPromoTask(Context context){
            this.ctx = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(PromotionCreate.this, "Uploading Promotion", "Please wait...",true,true);
        }

        @Override
        protected String doInBackground(String... params) {
            String eventTitle = params[0];
            String startDate = params[1];
            String endDate = params[2];
            String startTime = params[3];
            String endTime = params[4];
            String email = params[5];
            String contactNumber = params[6];
            String location = params[7];
            String price = params[8];
            String description = params[9];
            String encodedEventImage = params[10];

            date = startDate;

            //Parse the input date
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            Date inputDate = null;
            Date inputDate2 = null;
            try {
                inputDate = fmt.parse(startDate);
                inputDate2 = fmt.parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Create the MySQL datetime string
            fmt = new SimpleDateFormat("yyyy-MM-dd");
            String startDateFormat = fmt.format(inputDate);
            String endDateFormat = fmt.format(inputDate2);

            HashMap<String,String> parameter = new HashMap<>();
            parameter.put(Config.KEY_PROMO_NAME,eventTitle);
            parameter.put(Config.KEY_PROMO_DESCRIPTION,description);
            parameter.put(Config.KEY_PROMO_START_DATE,startDateFormat);
            parameter.put(Config.KEY_PROMO_END_DATE,endDateFormat);
            parameter.put(Config.KEY_PROMO_START_TIME,startTime);
            parameter.put(Config.KEY_PROMO_END_TIME,endTime);
            parameter.put(Config.KEY_PROMO_EMAIL,email);
            parameter.put(Config.KEY_PROMO_CONTACT_NUMBER,contactNumber);
            parameter.put(Config.KEY_PROMO_LOCATION,location);
            parameter.put(Config.KEY_PROMO_PRICE,price);
            parameter.put(Config.KEY_PROMO_IMAGE,encodedEventImage);



            RequestHandler rh = new RequestHandler();
            String res = rh.sendPostRequest(Config.URL_ADD_PROMO, parameter);
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(ctx,s,Toast.LENGTH_SHORT).show();
            loading.dismiss();
        }


    }


    public void submitPromotion(View view) {
        String promoTitle, startDate, endDate, startTime, endTime, email, contactNum, society, location, price, description;
        boolean priceValidity = true;
//        spinnerLocation = (Spinner) findViewById(R.id.spinnerCreatePromo);
//        imageViewPromotionCreate = (ImageView) findViewById(R.id.imageViewPromotionCreate);
//        editTextPromotionTitle = (EditText) findViewById(R.id.editTextPromotionTitle);
//        editTextCreatePromoEmail = (EditText) findViewById(R.id.editTextCreatePromoEmail);
//        editTextCreatePromoContact = (EditText) findViewById(R.id.editTextCreatePromoContact);
//        editTextCreatePromoPrice = (EditText) findViewById(R.id.editTextCreatePromoPrice);
//        editTextCreatePromoDetail = (EditText) findViewById(R.id.editTextCreatePromoDetail);
//        textViewCreatePromoStartDate = (TextView) findViewById(R.id.textViewCreatePromoStartDate);
//        textViewCreatePromoEndDate = (TextView) findViewById(R.id.textViewCreatePromoEndDate);
//        textViewCreatePromoStartTime = (TextView) findViewById(R.id.textViewCreatePromoStartTime);
//        textViewCreatePromoEndTime = (TextView) findViewById(R.id.textViewCreatePromoEndTime);

        promoTitle = editTextPromotionTitle.getText().toString();
        startDate = textViewCreatePromoStartDate.getText().toString();
        endDate = textViewCreatePromoEndDate.getText().toString();
        startTime = textViewCreatePromoStartTime.getText().toString();
        endTime = textViewCreatePromoEndTime.getText().toString();
        email = editTextCreatePromoEmail.getText().toString();
        contactNum = editTextCreatePromoContact.getText().toString();
        location = editTextCreatePromoLocation.getText().toString();
        price = editTextCreatePromoPrice.getText().toString();
        description = editTextCreatePromoDetail.getText().toString();

        Bitmap bitmap = ((BitmapDrawable) imageViewPromotionCreate.getDrawable()).getBitmap();
        String encodedImage = Promotion.bitmapToBase64(bitmap);

        try {
            Double.parseDouble(price);
        }catch(NumberFormatException e){
            priceValidity = false;
        }

        if (promoTitle.equals("") || startDate.equals("") || endDate.equals("") ||
                startTime.equals("") || endTime.equals("") || email.equals("") || contactNum.equals("") ||
                location.equals("") || location.equals("") || price.equals("") || description.equals("")) {
            Toast.makeText(getApplicationContext(), "All field must be entered", Toast.LENGTH_LONG).show();
        } else if (!(contactNum.matches("^[0-9\\-]*$")) || contactNum.length() < 10) {
            //contact number contain charaters other than 0-9, '-' and '+'
            editTextCreatePromoContact.setError("Please enter correct phone number");
        } else if (!priceValidity) {
            //price entered cannot be parsed into double
            editTextCreatePromoPrice.setError("Please enter number only");
        } else {
            PromotionCreate.BackgroundInsertPromoTask backgroundInsertPromoTask = new PromotionCreate.BackgroundInsertPromoTask(this);
            backgroundInsertPromoTask.execute(promoTitle, startDate, endDate, startTime, endTime, email, contactNum, location, price, description, encodedImage);
        }
    }
    public void doCrop(Uri imageUri){
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMinCropResultSize(600,600)
                .setAspectRatio(5,3)
                //.setMaxCropResultSize(1000,480)
                .start(this);
    }

    private Bitmap scale(Bitmap b) {
        ScrollView scrollView = (ScrollView)findViewById(R.id.content_promotion_create);
        return Bitmap.createScaledBitmap(b, (scrollView.getWidth()-40),600, false);
    }

}
