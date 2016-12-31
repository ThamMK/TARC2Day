package rsf2.android.tarc2day;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ShowEventDetail extends AppCompatActivity {

    ImageView imageViewQRCode;
    TextView textQRTime, textQRDate, textQRPrice, textQRConctact, textQRLocation, textQRTitle;
    RegisterdEvent event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_detail);

        Intent intent = getIntent();
        event = (RegisterdEvent) intent.getParcelableExtra("myRegisterEvent");

        imageViewQRCode = (ImageView) findViewById(R.id.imageViewShowEventDetailimage);
        textQRTitle = (TextView) findViewById(R.id.textShowEventDetailTitle);
        textQRTime = (TextView) findViewById(R.id.textShowEventDetailTime);
        textQRDate = (TextView) findViewById(R.id.textShowEventDetailDate);
        textQRPrice = (TextView) findViewById(R.id.textShowEventDetailPrice);
        textQRConctact = (TextView) findViewById(R.id.textShowEventDetailConctact);
        textQRLocation = (TextView) findViewById(R.id.textShowEventDetailLocation);

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
            BitMatrix bitMatrix = multiFormatWriter.encode(event.getQrCode(), BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageViewQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
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