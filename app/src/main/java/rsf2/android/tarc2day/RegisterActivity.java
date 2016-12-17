package rsf2.android.tarc2day;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.AsyncListUtil;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button btnSignUp;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextReenterPassword;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextDate;
    private EditText editTextContactNumber;

    protected String username;
    protected String password;
    protected String reenterPassword;
    protected String name;
    protected String date;
    protected String email;
    protected String contactNumber;




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

                    signUp();
                } else {

                    Toast.makeText(RegisterActivity.this,"Fields are empty",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected void signUp(){



        //Connect to database here
        //Use an inner class with asynchronous task to add new user into the database
        class AddUser extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPostExecute(String s) {

            }


            @Override
            protected String doInBackground(Void... v) {


                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_USER_USERNAME,username);
                params.put(Config.KEY_USER_PASSWORD,password);
                params.put(Config.KEY_USER_NAME,name);
                params.put(Config.KEY_USER_EMAIL,email);
                params.put(Config.KEY_USER_DATE,date);
                params.put(Config.KEY_USER_CONTACT,contactNumber);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_USER, params);
                return res;
            }
        }

        //Create an object of the innerclass and pass in the string values

        AddUser addUser = new AddUser();
        addUser.execute();


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

        if(password.contentEquals(reenterPassword)) {
            return true;
        } else {
            return false;
        }

    }




}
