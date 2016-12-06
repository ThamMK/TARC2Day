package rsf2.android.tarc2day;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private SliderLayout mDemoSlider;
    private List<Event> eventList = new ArrayList<Event>();
    private List<Society> societyList = new ArrayList<Society>();
    private RecyclerView recyclerView, recyclerView2;
    private EventAdapter eventAdapter;
    private SocietyAdapter societyAdapter;
    private TextView textViewViewAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();

        file_maps.put("Promotion 1",R.drawable.promo1);
        file_maps.put("Promotion 2",R.drawable.promo2);
        file_maps.put("Promotion 3",R.drawable.promo3);


        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewEvent);

        eventAdapter = new EventAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eventAdapter);

        eventData();

        textViewViewAll = (TextView) findViewById(R.id.textViewViewAll);
        textViewViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EventList.class);
                startActivity(intent);
            }
        });

        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerViewSociety);

        societyAdapter = new SocietyAdapter(societyList);
        RecyclerView.LayoutManager societyLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(societyLayoutManager);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(societyAdapter);

        societyData();

        textViewViewAll = (TextView) findViewById(R.id.textViewViewAllSociety);
        textViewViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SocietyList.class);
                startActivity(intent);
            }
        });
    }

    private void eventData() {

        //Get event data from database
        String startDate = new Date().toString();
        String endDate = new Date().toString();
        Society society = new Society("Computer Science Society","Ming Keat","testing","test","test2");
        Event event = new Event("Computer Science Night", "Code", startDate,endDate,society.getName(),0,"012123","tmk@gmail.com");
        eventList.add(event);

        event = new Event("Hackathon", "Coding", startDate,endDate,society.getName(),0,"012123","tmk@gmail.com");
        eventList.add(event);

        event = new Event("Ideathon", "Hacking", startDate,endDate,society.getName(),0,"012123","tmk@gmail.com");
        eventList.add(event);

    }

    private void societyData() {

        //Get event data from database
        Society society = new Society("Computer Science Society","Ming Keat","testing","012-3456789","test@email.com");
        societyList.add(society);
        society = new Society("Accounting Society","Ming Keat","testing","012-3456789","test@email.com");
        societyList.add(society);
        society = new Society("Astronomy Society","Ming Keat","testing","012-3456789","test@email.com");
        societyList.add(society);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
