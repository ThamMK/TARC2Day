package rsf2.android.tarc2day;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;

public class PromotionInfo extends AppCompatActivity {

    private static Promotion promotion;
    private TextView textViewTitle;
    private TextView textViewDetail;
    private TextView textViewTime;
    private TextView textViewDate;
    private TextView textViewPrice;
    private TextView textViewContact;
    private TextView textViewLocation;
    private ImageView imageViewPromotionInfo;

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
        imageViewPromotionInfo = (ImageView) findViewById(R.id.imageViewPromotionInfo);

        Intent intent = getIntent();
        Promotion promotion = intent.getParcelableExtra("PROMOTION");
        getDetails(promotion);
    }

    class BackgroundTask extends AsyncTask<String,Void,Bitmap> {


        @Override
        protected Bitmap doInBackground(String... params) {
            //Pass in the event id
            //return getBitmapFromURL(params[0]);
            try {
                return Picasso.with(PromotionInfo.this).load(params[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {


            imageViewPromotionInfo = (ImageView) findViewById(R.id.imageViewPromotionInfo);

            //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1600, 1600);
            //imageView.setLayoutParams(layoutParams);

            // imageView.getLayoutParams().height = 450;

            imageViewPromotionInfo.setImageBitmap(bitmap);
            //imageView.requestLayout();
        }
    }

    protected void getDetails(Promotion promotion) {

        this.promotion = promotion;

        textViewTitle.setText(promotion.getTitle());
        textViewDetail.setText(promotion.getDescription());
        textViewDate.setText(promotion.getStartDate() + " - " + promotion.getEndDate());
        textViewPrice.setText("RM " + promotion.getPrice());
        textViewContact.setText(promotion.getContactNo());
        textViewLocation.setText(promotion.getLocation());

        int width = dpToPx(365);
        int height = dpToPx(200);

        imageViewPromotionInfo = (ImageView) findViewById(R.id.imageViewPromotionInfo) ;
        Picasso.with(this).load(promotion.getImageUrl()).placeholder(R.drawable.progress_animation ).resize(width,height).into(imageViewPromotionInfo);

    }


    public int dpToPx(int dp) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return Math.round(px);
    }
}
