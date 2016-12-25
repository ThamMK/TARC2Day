package rsf2.android.tarc2day;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.security.SecureRandom;
import java.util.*;

public class GenerateQR extends AppCompatActivity {
    ImageView imageViewQRCode;
    TextView textQRTime,textQRDate,textQRPrice,textQRConctact,textQRLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        Intent intent = getIntent();
        Event event = (Event) intent.getParcelableExtra("registerEvent");

        //Initialize the UI design
        String QReventCode = randomQRCode();
        imageViewQRCode = (ImageView) findViewById(R.id.imageViewQRimage);
        textQRTime = (TextView)findViewById(R.id.textQRTime);
        textQRDate = (TextView)findViewById(R.id.textQRDate);
        textQRPrice = (TextView)findViewById(R.id.textQRPrice);
        textQRConctact = (TextView)findViewById(R.id.textQRConctact);
        textQRLocation = (TextView)findViewById(R.id.textQRLocation);

        //Setting up the text
       // textViewTitle.setText(event.getTitle());
        textQRDate.setText(event.getStartTime() + " - " + event.getEndTime());
        textQRDate.setText(event.getStartDate() + " - " + event.getEndDate());
        textQRPrice.setText("RM " + event.getPrice());
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
            String eventTitle = params[0];
            String username = params[1];
            String QRCOde = params[2];

            HashMap<String,String> parameter = new HashMap<>();
            parameter.put(Config.KEY_REGISTEREVENT_TITLE,eventTitle);
            parameter.put(Config.KEY_REGISTEREVENT_USERNAME,username);
            parameter.put(Config.KEY_REGISTEREVENT_QRCODE,QRCOde);

            RequestHandler rh = new RequestHandler();
            //String res = rh.sendPostRequest(, parameter);
            //return res;
            return null;
        }
    }

}
