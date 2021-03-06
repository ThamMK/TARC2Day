package rsf2.android.tarc2day;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GenerateQR extends AppCompatActivity {
    ImageView imageViewQRCode;
    TextView textQRTime,textQRDate,textQRPrice,textQRConctact,textQRLocation,textQRTitle;
    User user;
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences",MODE_PRIVATE);
        String json = sharedPreferences.getString(Config.TAG_USER, "");
        user = gson.fromJson(json,User.class);

        Intent intent = getIntent();
        event = (Event) intent.getParcelableExtra("registerEvent");

        //Initialize the UI design
        String QReventCode = randomQRCode();
        imageViewQRCode = (ImageView) findViewById(R.id.imageViewQRimage);
        textQRTitle = (TextView)findViewById(R.id.textQRTitle);
        textQRTime = (TextView)findViewById(R.id.textQRTime);
        textQRDate = (TextView)findViewById(R.id.textQRDate);
        textQRPrice = (TextView)findViewById(R.id.textQRPrice);
        textQRConctact = (TextView)findViewById(R.id.textQRConctact);
        textQRLocation = (TextView)findViewById(R.id.textQRLocation);

        //Setting up the text
       // textViewTitle.setText(event.getTitle());
        textQRTitle.setText(event.getTitle());

        SimpleDateFormat inputDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputTimeFormat = new SimpleDateFormat("hh:mm a");

        try {
            if(event.getStartDate().equals(event.getEndDate())){
                Date startDateTime = inputDateTimeFormat.parse(event.getStartDate() + " " + event.getStartTime());
                Date endDateTime = inputDateTimeFormat.parse(event.getEndDate() + " " + event.getEndTime());

                textQRDate.setText(outputDateFormat.format(startDateTime));
                textQRTime.setText(outputTimeFormat.format(startDateTime) + " - " + outputTimeFormat.format(endDateTime));
            }
            else {
                Date startDateTime = inputDateTimeFormat.parse(event.getStartDate() + " " + event.getStartTime());
                Date endDateTime = inputDateTimeFormat.parse(event.getEndDate() + " " + event.getEndTime());

                textQRTime.setText(outputTimeFormat.format(startDateTime) + " - " + outputTimeFormat.format(endDateTime));
                textQRDate.setText(outputDateFormat.format(startDateTime) + " - " + outputDateFormat.format(endDateTime));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(event.getPrice() == 0.0)
            textQRPrice.setText("FREE");
        else{
            DecimalFormat df = new DecimalFormat("#.00");
            textQRPrice.setText("RM" + df.format(event.getPrice()));
        }

        textQRConctact.setText(event.getContactNo());
        textQRLocation.setText(event.getLocation());

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(QReventCode, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageViewQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        new BackgroundRegisterEvent().execute(event.getId(),user.getUsername(),QReventCode);

    }

    public String randomQRCode() {
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

    class BackgroundRegisterEvent extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            String eventid = params[0];
            String username = params[1];
            String QRCOde = params[2];

            HashMap<String,String> parameter = new HashMap<>();
            parameter.put(Config.KEY_REGISTEREVENT_ID,eventid);
            parameter.put(Config.KEY_REGISTEREVENT_USERNAME,username);
            parameter.put(Config.KEY_REGISTEREVENT_QRCODE,QRCOde);

            RequestHandler rh = new RequestHandler();
            String res = rh.sendPostRequest(Config.URL_ADD_EVENTDETAILS, parameter);
            return res;
        }
    }

    public void reminderEvent(View view) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, event.getTitle());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "KL TARUC " + event.getLocation());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, event.getEventDescription());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDateTime = event.getStartDate() + " " + event.getStartTime();
        String endDateTime = event.getEndDate() + " " + event.getEndTime();
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        try {
            calStart.setTime(sdf.parse(startDateTime));// all done
            calEnd.setTime(sdf.parse(endDateTime));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calStart.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calEnd.getTimeInMillis());
        startActivity(intent);
    }

}
