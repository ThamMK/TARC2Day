package rsf2.android.tarc2day;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PromotionList extends AppCompatActivity {

    private List<Promotion> promotionList = new ArrayList<Promotion>();
    private RecyclerView recyclerView;
    private PromotionAdapter promotionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPromotionList);

        promotionAdapter = new PromotionAdapter(promotionList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(promotionAdapter);

        promotionData();
    }

    private void promotionData() {

        //Get event data from database
        //The activity will then populate here onwards.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");
        //simpleDateFormat.applyPattern("d MMM yyyy");
        Date date = new Date();

        String startDate = simpleDateFormat.format(date);
        String endDate = simpleDateFormat.format(date);
        Society society = new Society("Computer Science Society","Ming Keat","testing","test","test2");
        Promotion promotion = new Promotion("Promotion 1", "Very fun", startDate, endDate, 0.0, "012", "Test-Location");
        promotionList.add(promotion);

    }


}
