package rsf2.android.tarc2day;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Circle;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAccount extends Activity {
    private User user;
    boolean admin;
    CircleImageView profilePictureView;
    //ImageSetting
    private final static int RESULT_SELECT_IMAGE = 100;
    EditText editTextName, editTextEmail, editTextContactNumber, editTextBirthday;
    Button btnChangePassword, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        profilePictureView = (CircleImageView) findViewById(R.id.profilePicture);
        editTextName = (EditText) findViewById(R.id.editTextUpdateName);
        editTextEmail = (EditText) findViewById(R.id.editTextUpdateEmail);
        editTextContactNumber = (EditText) findViewById(R.id.editTextUpdateContactNumber);
        editTextBirthday = (EditText) findViewById(R.id.editTextUpdateBirthday);

        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        getUserData();

        editTextName.setText(user.getName());
        editTextEmail.setText(user.getEmail());
        editTextContactNumber.setText(user.getContactNo());
        editTextBirthday.setText(user.getDateOfBirth());
        Toast.makeText(getApplicationContext(),user.getPassword(),Toast.LENGTH_LONG).show();
        profilePictureView.setOnClickListener(new View.OnClickListener() {
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



        btnChangePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Pop up an alert dialog
                AlertDialog alertDialog = new AlertDialog.Builder(MyAccount.this).create();

                //Put edit text into the alert dialog
                alertDialog.setTitle("Change Password");

                LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                final EditText editTextPassword = new EditText(getApplication());
                editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editTextPassword.setHint("Enter current password");
                editTextPassword.setHintTextColor(getResources().getColor(R.color.alpha_gray));
                editTextPassword.setTextColor(getResources().getColor(R.color.black));

                final EditText editTextNewPassword = new EditText(getApplication());
                editTextNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editTextNewPassword.setTextColor(getResources().getColor(R.color.black));
                editTextNewPassword.setHintTextColor(getResources().getColor(R.color.alpha_gray));
                editTextNewPassword.setHint("Enter new password");

                final EditText editTextReenterNewPassword = new EditText(getApplication());
                editTextReenterNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editTextReenterNewPassword.setTextColor(getResources().getColor(R.color.black));
                editTextReenterNewPassword.setHintTextColor(getResources().getColor(R.color.alpha_gray));
                editTextReenterNewPassword.setHint("Reenter new password");

                linearLayout.addView(editTextPassword);
                linearLayout.addView(editTextNewPassword);
                linearLayout.addView(editTextReenterNewPassword);

                alertDialog.setView(linearLayout);
                //Set positive button (submit)
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String currentPassword = editTextPassword.getText().toString();

                        if(currentPassword.contentEquals(user.getPassword())) {
                            //Check if the reentered password matches
                            String newPassword = editTextNewPassword.getText().toString();
                            String reenterNewPassword = editTextReenterNewPassword.getText().toString();

                            if(newPassword.equals(reenterNewPassword)) {
                                if(newPassword.equals(user.getPassword())){
                                    Toast.makeText(getApplicationContext(),"new password cannot be current password", Toast.LENGTH_LONG).show();
                                }else {
                                    //Update password
                                    dialog.dismiss();
                                    BackgroundUpdatePasswordTask backgroundUpdatePasswordTask = new BackgroundUpdatePasswordTask();
                                    backgroundUpdatePasswordTask.execute(user.getUsername(), newPassword);
                                }
                            } else {
                                //New passwords don't match

                                Toast.makeText(getApplicationContext(),"Passwords do not match", Toast.LENGTH_LONG).show();
                            }


                        } else {
                            //Incorrect user password
                            //Don't let them update password
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Wrong password",Toast.LENGTH_LONG).show();
                        }


                    }
                });
                //Set negative button (cancel)
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = editTextName.getText().toString();
                final String email = editTextEmail.getText().toString();
                final String date = editTextBirthday.getText().toString();
                final String contactNumber = editTextContactNumber.getText().toString();
                Bitmap bitmap = ((BitmapDrawable)profilePictureView.getDrawable()).getBitmap();
                final String encodedImage = User.bitmapToBase64(bitmap);



                //Pop up an alert dialog
                AlertDialog alertDialog = new AlertDialog.Builder(MyAccount.this).create();

                alertDialog.setTitle("Enter current password");
                //Put edit text into the alert dialog
                final EditText editTextPassword = new EditText(getApplication());
                editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editTextPassword.setTextColor(getResources().getColor(R.color.black));
                alertDialog.setView(editTextPassword);
                //Set positive button (submit)
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String password = editTextPassword.getText().toString();

                        if(password.contentEquals(user.getPassword())) {
                            dialog.dismiss();
                            BackgroundUpdateProfileTask backgroundUpdateProfileTask = new BackgroundUpdateProfileTask();
                            backgroundUpdateProfileTask.execute(user.getUsername(),name,email,date,contactNumber,encodedImage);
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                //Set negative button (cancel)
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();


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


                profilePictureView.setImageBitmap(bitmap);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    protected void getUserData() {

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences",MODE_PRIVATE);
        String json = sharedPreferences.getString(Config.TAG_USER, "");

        user = gson.fromJson(json,User.class);
        admin = sharedPreferences.getBoolean(Config.TAG_ADMIN,false);

        BackgroundProfileTask backgroundProfileTask = new BackgroundProfileTask();
        backgroundProfileTask.execute(user.getUsername());

    }



    public void doCrop(Uri imageUri){
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMinCropResultSize(136,136)
                //.setMaxCropResultSize(1000,480)
                .start(this);
    }


    private Bitmap scale(Bitmap b) {
        return Bitmap.createScaledBitmap(b,profilePictureView.getWidth(),profilePictureView.getHeight(),  false);
    }

    class BackgroundProfileTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            RequestHandler requestHandler = new RequestHandler();

            HashMap<String,String> parameter = new HashMap<>();
            parameter.put("username",params[0]);

            String response = requestHandler.sendPostRequest(Config.URL_GET_USER,parameter);
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String encodedImage = jsonObject.getString("profilePicture");
                if(!encodedImage.isEmpty()) {
                    Bitmap profilePicture = User.base64ToBitmap(encodedImage);

                    user.setProfilePicture(profilePicture);

                    profilePictureView.setImageBitmap(user.getProfilePicture());
                } else {
                    profilePictureView.setImageResource(R.drawable.fileimage);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    class BackgroundUpdateProfileTask extends AsyncTask<String,Void,String> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            loading = ProgressDialog.show(MyAccount.this, "Updating user account", "Please wait...",true,true);
        }

        @Override
        protected String doInBackground(String... parameter) {

            HashMap<String,String> params = new HashMap<>();
            params.put(Config.KEY_USER_USERNAME,parameter[0]);
            params.put(Config.KEY_USER_NAME,parameter[1]);
            params.put(Config.KEY_USER_EMAIL,parameter[2]);
            params.put(Config.KEY_USER_DATE,parameter[3]);
            params.put(Config.KEY_USER_CONTACT,parameter[4]);
            params.put(Config.KEY_USER_PROFILE,parameter[5]);

            RequestHandler rh = new RequestHandler();
            String res = rh.sendPostRequest(Config.URL_UPDATE_USER, params);
            return res;

        }

        @Override
        protected void onPostExecute(String s) {
            loading.dismiss();
            if(!s.isEmpty()) {
                Toast.makeText(getApplicationContext(),"Successfully updated profile!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),"Failed to update profile!", Toast.LENGTH_LONG).show();
            }


        }
    }

    class BackgroundUpdatePasswordTask extends AsyncTask<String,Void,String> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            loading = ProgressDialog.show(MyAccount.this, "Updating user account", "Please wait...",true,true);
        }

        @Override
        protected String doInBackground(String... parameter) {

            HashMap<String,String> params = new HashMap<>();
            params.put(Config.KEY_USER_USERNAME,parameter[0]);
            params.put(Config.KEY_USER_PASSWORD,parameter[1]);

            RequestHandler rh = new RequestHandler();
            String res = rh.sendPostRequest(Config.URL_UPDATE_USER_PASSWORD, params);
            return res;

        }

        @Override
        protected void onPostExecute(String s) {
            loading.dismiss();
            if(!s.isEmpty()) {
                Toast.makeText(getApplicationContext(),"Successfully updated profile!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),"Failed to update profile!", Toast.LENGTH_LONG).show();
            }


        }
    }
}
