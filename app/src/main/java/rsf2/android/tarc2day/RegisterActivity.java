package rsf2.android.tarc2day;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.AsyncListUtil;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private Button btnSignUp;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextReenterPassword;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextDate;
    private EditText editTextContactNumber;
    private CircleImageView profilePicture;

    protected String username;
    protected String password;
    protected String reenterPassword;
    protected String name;
    protected String date;
    protected String email;
    protected String contactNumber;
    protected String encodedImage;


    private final static int RESULT_SELECT_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = validation();
                if(valid) {
                    Toast.makeText(RegisterActivity.this,"Registering",Toast.LENGTH_SHORT).show();
                    AddUser addUser = new AddUser();
                    addUser.execute(username,password,name,email,date,contactNumber,encodedImage);

                } else {

                    Toast.makeText(RegisterActivity.this,"Fields are empty",Toast.LENGTH_LONG).show();
                }
            }
        });

        profilePicture = (CircleImageView) findViewById(R.id.registerProfilePicture);
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //Pick Image From Gallery
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_SELECT_IMAGE);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });









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
                        /*
            try {
              Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                ImageView imageView = (ImageView) findViewById(R.id.createEventImageFile);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
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


                profilePicture.setImageBitmap(bitmap);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    public void doCrop(Uri imageUri){
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMinCropResultSize(136,136)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1,1)
                .setFixAspectRatio(true)
                //.setMaxCropResultSize(1000,480)
                .start(this);
    }


    private Bitmap scale(Bitmap b) {
        return Bitmap.createScaledBitmap(b,136,136,  false);
    }





    //Connect to database here
    //Use an inner class with asynchronous task to add new user into the database
    class AddUser extends AsyncTask<String,Void,String> {

        @Override
        protected void onPostExecute(String s) {
            editTextUsername.setText("");
            editTextContactNumber.setText("");
            editTextPassword.setText("");
            editTextReenterPassword.setText("");
            editTextName.setText("");
            editTextDate.setText("");
            editTextEmail.setText("");

            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);

            Toast.makeText(RegisterActivity.this,"Registered Successfully!", Toast.LENGTH_LONG).show();
        }


        @Override
        protected String doInBackground(String... parameter) {


            HashMap<String,String> params = new HashMap<>();
            params.put(Config.KEY_USER_USERNAME,parameter[0]);
            params.put(Config.KEY_USER_PASSWORD,parameter[1]);
            params.put(Config.KEY_USER_NAME,parameter[2]);
            params.put(Config.KEY_USER_EMAIL,parameter[3]);
            params.put(Config.KEY_USER_DATE,parameter[4]);
            params.put(Config.KEY_USER_CONTACT,parameter[5]);
            params.put(Config.KEY_USER_PROFILE,parameter[6]);

            RequestHandler rh = new RequestHandler();
            String res = rh.sendPostRequest(Config.URL_ADD_USER, params);
            return res;
        }
    }







    protected boolean validation() {

        editTextUsername = (EditText) findViewById(R.id.editTextRegisterUsername);
        editTextName = (EditText) findViewById(R.id.editTextRegisterName);
        editTextPassword = (EditText) findViewById(R.id.editTextRegisterPassword);
        editTextReenterPassword = (EditText) findViewById(R.id.editTextRegisterReenterPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextRegisterEmail);
        editTextDate = (EditText) findViewById(R.id.editTextRegisterDate);
        editTextContactNumber = (EditText) findViewById(R.id.editTextRegisterContactNumber);

        username = editTextUsername.getText().toString();
        name = editTextName.getText().toString();
        password = editTextPassword.getText().toString();
        reenterPassword = editTextReenterPassword.getText().toString();
        email = editTextEmail.getText().toString();
        date = editTextDate.getText().toString();
        contactNumber = editTextContactNumber.getText().toString();

        Bitmap bitmap = ((BitmapDrawable)profilePicture.getDrawable()).getBitmap();
        encodedImage = User.bitmapToBase64(bitmap);

        if(password.contentEquals(reenterPassword)) {
            return true;
        } else {
            return false;
        }

    }




}
