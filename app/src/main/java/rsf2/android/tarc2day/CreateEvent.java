package rsf2.android.tarc2day;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;

public class CreateEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    public int selectedDateTextView; //Need to know which date to set
    public int selectedTimeTextView;
    public TextView textViewDate;
    private int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
    }

    protected void onClickImageView(View view) {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.createEventImageFile);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
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
            case R.id.textViewCreateEventStartingDate :
                textViewDate = (TextView) findViewById(R.id.textViewCreateEventStartingDate);
                textViewDate.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year );
                break;
            case R.id.textViewCreateEventEndDate :
                textViewDate = (TextView) findViewById(R.id.textViewCreateEventEndDate);
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
            case R.id.textViewCreateEventStartingTime :
                textViewDate = (TextView) findViewById(R.id.textViewCreateEventStartingTime);
                textViewDate.setText(hourOfDay + ":" + minute);
                break;

            case R.id.textViewCreateEventEndTime :
                textViewDate = (TextView) findViewById(R.id.textViewCreateEventEndTime);
                textViewDate.setText(hourOfDay + ":" + minute);
                break;


            default:
                break;
        }
    }

}
