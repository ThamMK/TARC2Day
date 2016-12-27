package rsf2.android.tarc2day;

import android.app.LocalActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class SocietyInfo extends AppCompatActivity{

    private TextView textViewName;
    private TextView textViewPersonInCharge;
    private TextView textViewDescription;
    private TextView textViewContactNo;
    private TextView textViewEmail;
    private ImageView imageView;
    private static Society society;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Society society = intent.getParcelableExtra("SOCIETY");
        getDetails(society);

    }
    protected void getDetails(Society society) {



        textViewName = (TextView) findViewById(R.id.textViewSocietyInfoName);
        textViewName.setText(society.getName());

        textViewPersonInCharge = (TextView) findViewById(R.id.textViewSocietyInfoPersonInCharge);
        textViewPersonInCharge.setText(society.getPersonInCharge());

        textViewDescription = (TextView) findViewById(R.id.textViewSocietyInfoDescription);
        textViewDescription.setText(society.getDescription());

        textViewContactNo = (TextView) findViewById(R.id.textViewSocietyInfoContactNo);
        textViewContactNo.setText(society.getContactNo());

        textViewEmail = (TextView) findViewById(R.id.textViewSocietyInfoEmail);
        textViewEmail.setText(society.getEmail());

        int width = dpToPx(365);
        int height = dpToPx(200);

        imageView = (ImageView) findViewById(R.id.imageViewSocietyInfo) ;
        Picasso.with(this).load(society.getImageUrl()).placeholder(R.drawable.progress_animation).resize(width,height).into(imageView);
    }

    public void registerSociety(View view){
//        final Intent intent = new Intent(this,GenerateQR.class);
//        intent.putExtra("registerSociety",(Parcelable) society);

        AlertDialog.Builder requestBuilder = new AlertDialog.Builder(this);
        requestBuilder.setCancelable(true);
        requestBuilder.setMessage("Your request to join " + society.getName() + " is pending for approve. ");
        final AlertDialog requestDialog = requestBuilder.create();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Society Registration");
        builder.setMessage("Do you confirm want to register society : " + society.getName());
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        startActivity(intent);
                        requestDialog.show();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public int dpToPx(int dp) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return Math.round(px);
    }
}
