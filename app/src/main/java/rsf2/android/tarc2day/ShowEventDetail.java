package rsf2.android.tarc2day;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ShowEventDetail extends AppCompatActivity {

    ImageView imageViewQRCode;
    TextView textQRTime,textQRDate,textQRPrice,textQRConctact,textQRLocation,textQRTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_detail);

        Intent intent = getIntent();
        RegisterdEvent event = (RegisterdEvent) intent.getParcelableExtra("myRegisterEvent");

        imageViewQRCode = (ImageView) findViewById(R.id.imageViewShowEventDetailimage);
        textQRTitle = (TextView)findViewById(R.id.textShowEventDetailTitle);
        textQRTime = (TextView)findViewById(R.id.textShowEventDetailTime);
        textQRDate = (TextView)findViewById(R.id.textShowEventDetailDate);
        textQRPrice = (TextView)findViewById(R.id.textShowEventDetailPrice);
        textQRConctact = (TextView)findViewById(R.id.textShowEventDetailConctact);
        textQRLocation = (TextView)findViewById(R.id.textShowEventDetailLocation);

        textQRTitle.setText(event.getTitle());
        textQRDate.setText(event.getStartTime() + " - " + event.getEndTime());
        textQRDate.setText(event.getStartDate() + " - " + event.getEndDate());
        textQRPrice.setText("RM " + event.getPrice());
        textQRConctact.setText(event.getContactNo());
        textQRLocation.setText(event.getLocation());

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(event.getQrCode(), BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageViewQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
