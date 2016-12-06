package rsf2.android.tarc2day;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private TextView textViewViewAll;
    private DrawerLayout drawerLayout;
    private LinearLayout drawerLinearLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ImageView toolbarImage;
    private ImageView navDrawerBack;
    private ListView drawerListView;
    private ArrayAdapter<String> adapter;
    private String[] linkArray = { "Events" ,"Society", "Promotion", "My Events", "My Account" , "About us", "Log Out"};
    private Integer[] imageId = {R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        //Set the list view for the drawer

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView = (ListView) findViewById(R.id.navList);
        drawerLinearLayout = (LinearLayout) findViewById(R.id.drawerLinearLayout);
        toolbarImage = (ImageView) findViewById(R.id.toolbarImage);

        //If burger button is clicked, then open nav drawer
        toolbarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!drawerLayout.isDrawerOpen(drawerLinearLayout)) {
                    drawerLayout.openDrawer(drawerLinearLayout);
                }

            }
        });

        navDrawerBack = (ImageView) findViewById(R.id.navDrawerBack);

        navDrawerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerOpen(drawerLinearLayout)) {
                    drawerLayout.closeDrawer(drawerLinearLayout);
                }
            }
        });


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





        CustomListAdapter adapter=new CustomListAdapter(this, linkArray, imageId);

        drawerListView.setAdapter(adapter);

        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String selectedItem= linkArray[+position];
                Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();
                Intent intent;
                switch(selectedItem) {
                    case "Events" :
                        intent = new Intent (getApplicationContext(),EventList.class);
                        startActivity(intent);
                        break;
                    case "Promotion" :
                        intent = new Intent(getApplicationContext(),PromotionList.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
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

    private void addDrawerItems() {

        adapter = new ArrayAdapter<String>(this, R.layout.custom_list, linkArray);
        drawerListView.setAdapter(adapter);
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
