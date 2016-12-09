package rsf2.android.tarc2day;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PromotionInfo extends AppCompatActivity {


    private TextView textViewTitle;
    private TextView textViewDetail;
    private TextView textViewTime;
    private TextView textViewDate;
    private TextView textViewPrice;
    private TextView textViewContact;
    private TextView textViewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewTitle = (TextView) findViewById(R.id.textViewPromotionTitle);
        textViewDetail = (TextView) findViewById(R.id.textViewPromotionDetail);
        textViewTime = (TextView) findViewById(R.id.textViewPromotionTime);
        textViewDate = (TextView) findViewById(R.id.textViewPromotionDate);
        textViewPrice = (TextView) findViewById(R.id.textViewPromotionPrice);
        textViewContact = (TextView) findViewById(R.id.textViewPromotionContact);
        textViewLocation = (TextView) findViewById(R.id.textViewPromotionLocation);

        Intent intent = getIntent();
        Promotion promotion = intent.getParcelableExtra("PROMOTION");
        getDetails(promotion);
    }

    protected void getDetails(Promotion promotion) {

        textViewTitle.setText(promotion.getTitle());
        textViewDetail.setText(promotion.getDescription());
        textViewDate.setText(promotion.getStartDate() + " - " + promotion.getEndDate());
        textViewPrice.setText("RM " + promotion.getPrice());
        textViewContact.setText(promotion.getContactNo());
        textViewLocation.setText(promotion.getLocation());



    }

}
