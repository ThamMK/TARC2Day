package rsf2.android.tarc2day;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.interfaces.Email;
import com.cloudrail.si.interfaces.SMS;
import com.cloudrail.si.services.SendGrid;
import com.cloudrail.si.services.Twilio;

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
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class ForgetPassword extends AppCompatActivity {

    EditText editTextForgetPass;
    String contactNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        editTextForgetPass = (EditText) findViewById(R.id.editTextForgetPass);

        PlayGifView pGif = (PlayGifView) findViewById(R.id.viewGif);
        pGif.setImageResource(R.drawable.forgetpassword);

    }

    public void forgetPassword(View view) {
        String inputEmail = editTextForgetPass.getText().toString();
        if(!inputEmail.equals(""))
            new BackgroundForgetPassTask(this).execute(inputEmail);
        else
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();

    }

    class BackgroundForgetPassTask extends AsyncTask<String, Void, String> {
        Context ctx;
        String json_url;
        String date;
        String JSON_STRING;
        String password;
        String email;
        ProgressDialog loading;
        JSONArray jsonArray;
        String result;

        BackgroundForgetPassTask(Context context) {
            this.ctx = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ForgetPassword.this, "Uploading Events", "Please wait...", true, true);
        }

        @Override
        protected String doInBackground(String... params) {
            email = params[0];
            json_url = "http://thammingkeat.esy.es/GetContactNum.php?email=" + email; //th php url
            password = randomPassword();

            HashMap<String, String> parameter = new HashMap<>();
            parameter.put(Config.KEY_FORGETPASS_EMAIL, email);
            parameter.put(Config.KEY_FORGETPASS_PASSWORD, password);

            RequestHandler rh = new RequestHandler();
            String res = rh.sendPostRequest(Config.URL_FORGET_PASSWORD, parameter);

            result="";
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
                if(result != null || result !="") {
                    jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject JO = jsonArray.getJSONObject(i);
                        contactNumber = JO.getString("contactNumber");
                    }
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }

            return res;
        }

        @Override
        protected void onPostExecute(String s) {

            if(!(contactNumber.equals(""))) {
                //Toast.makeText(ctx, s.trim(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(ctx, contactNumber, Toast.LENGTH_SHORT).show();
                Toast.makeText(ctx, password, Toast.LENGTH_SHORT).show();

                CloudRail.setAppKey("58603ebff5f1db6ef9a62031");

                final Email sendMail = new SendGrid(ctx, "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");
                final SMS sms = new Twilio(ctx, "AC68550e7e51559d1085bbc4dc443f26d2", "f72c5c5612a8a70a06a3413b24a442c6");

                new Thread() {
                    @Override
                    public void run() {
                        //sendMail.sendEmail("shiangyoung96@gmail.com", "Shiang Young", Arrays.asList(email), "TAR2DAY Reset Password", "TAR2DAY had set a new password for your account : " + password, null, null, null);
                        //sms.sendSMS("+18703976174", "+6" + contactNumber, "TAR2DAY had set a new password for your account : " + password);
                    }
                }.start();
            }
            loading.dismiss();
        }

    }

    public String randomPassword() {
        char[] characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random random = new SecureRandom();
        char[] result = new char[20];
        for (int i = 0; i < result.length; i++) {
            // picks a random index out of character set > random character
            int randomCharIndex = random.nextInt(characterSet.length);
            result[i] = characterSet[randomCharIndex];
        }
        return new String(result);
    }

}
