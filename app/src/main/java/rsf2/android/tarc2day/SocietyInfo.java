package rsf2.android.tarc2day;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class SocietyInfo extends AppCompatActivity{

    private TextView textViewName;
    private TextView textViewPersonInCharge;
    private TextView textViewDescription;
    private TextView textViewContactNo;
    private TextView textViewEmail;
    private ImageView imageView;
    Society society;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        society = intent.getParcelableExtra("SOCIETY");
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
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + society.getEmail()));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Request join the society " + society.getName());
        emailIntent.putExtra(Intent.EXTRA_TEXT, "I would like to request to join the societ" + society.getName());
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
}

    public int dpToPx(int dp) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return Math.round(px);
    }
}
