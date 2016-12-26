package rsf2.android.tarc2day;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CreateSociety extends AppCompatActivity {

    EditText editTextCreateSocietyName, editTextCreateSocietyDescription, editTextCreateSocietyPersonInCharge;
    EditText editTextCreateSocietyContactNo, editTextCreateSocietyEmail;
    ImageView imageViewCreateSocietyImage;

    private final static int RESULT_SELECT_IMAGE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_society);

        //Declaration of resources
        editTextCreateSocietyName = (EditText) findViewById(R.id.editTextCreateSocietyName);
        editTextCreateSocietyDescription = (EditText) findViewById(R.id.editTextCreateSocietyDescription);
        editTextCreateSocietyPersonInCharge = (EditText) findViewById(R.id.editTextCreateSocietyPersonInCharge);
        editTextCreateSocietyContactNo = (EditText) findViewById(R.id.editTextCreateSocietyContactNo);
        editTextCreateSocietyEmail = (EditText) findViewById(R.id.editTextCreateSocietyEmail);
        imageViewCreateSocietyImage = (ImageView) findViewById(R.id.createSocietyImageFile);

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
        /*if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                ImageView imageView = (ImageView) findViewById(R.id.createEventImageFile);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

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

                ImageView imageView = (ImageView) findViewById(R.id.createSocietyImageFile);
                imageView.setImageBitmap(bitmap);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    class BackgroundInsertSocietyTask extends AsyncTask<String,Void,String>{
        Context ctx;
        String date;
        ProgressDialog loading;

        BackgroundInsertSocietyTask(Context context){
            this.ctx = context;
            loading = ProgressDialog.show(CreateSociety.this, "Uploading Society", "Please wait...",true,true);
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("PARAMETERS", params.toString());

            String societyName = params[0];
            String societyDescription = params[1];
            String societyPersonInCharge = params[2];
            String societyContactNo = params[3];
            String societyEmail = params[4];
            String encodedSocietyImage = params[5];

            HashMap<String,String> parameter = new HashMap<>();
            parameter.put(Config.KEY_SOCIETY_NAME,societyName);
            parameter.put(Config.KEY_SOCIETY_DESCRIPTION,societyDescription);
            parameter.put(Config.KEY_SOCIETY_PERSON_IN_CHARGE,societyPersonInCharge);
            parameter.put(Config.KEY_SOCIETY_CONTACT_NUMBER,societyContactNo);
            parameter.put(Config.KEY_SOCIETY_EMAIL,societyEmail);
            parameter.put(Config.KEY_SOCIETY_IMAGE,encodedSocietyImage);

            RequestHandler rh = new RequestHandler();
            String res = rh.sendPostRequest(Config.URL_ADD_SOCIETY, parameter);
            return res;
        }


        @Override
        protected void onPostExecute(String s) {
            loading.dismiss();
            Toast.makeText(ctx,s,Toast.LENGTH_SHORT).show();
        }
    }

    public void submitSociety(View view){
        String societyName,societyDescription,societyPersonInCharge,societyContactNo,societyEmail;

        societyName = editTextCreateSocietyName.getText().toString();
        societyDescription = editTextCreateSocietyDescription.getText().toString();
        societyPersonInCharge = editTextCreateSocietyPersonInCharge.getText().toString();
        societyContactNo = editTextCreateSocietyContactNo.getText().toString();
        societyEmail = editTextCreateSocietyEmail.getText().toString();

        Bitmap bitmap = ((BitmapDrawable)imageViewCreateSocietyImage.getDrawable()).getBitmap();
        String encodedImage = Society.bitmapToBase64(bitmap);

        BackgroundInsertSocietyTask backgroundInsertSocietyTask = new BackgroundInsertSocietyTask(this);
        backgroundInsertSocietyTask.execute(societyName,societyDescription,societyPersonInCharge,societyContactNo,societyEmail,encodedImage);
    }

    public void doCrop(Uri imageUri){
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMinCropResultSize(600,600)
                //.setMaxCropResultSize(1000,480)
                .start(this);
    }

    private Bitmap scale(Bitmap b) {
        ScrollView scrollView = (ScrollView)findViewById(R.id.activity_create_society);
        return Bitmap.createScaledBitmap(b, (scrollView.getWidth()-40),600, false);
    }
}
