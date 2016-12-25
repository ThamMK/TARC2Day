package rsf2.android.tarc2day;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
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

    private DrawerLayout drawerLayout;
    private LinearLayout drawerLinearLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ImageView toolbarImage;
    private ImageView navDrawerBack;
    private ListView drawerListView;
    private CustomListAdapter adapter;
    private String[] linkArray = { "Events" ,"Society", "Promotion", "My Events", "My Account" , "About us", "Log Out"};
    private String[] adminArray = { "Events" ,"Society", "Promotion", "My Events", "My Account" ,"Create Event", "About us", "Log Out"};
    private Integer[] adminImageId = {R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage, R.drawable.logoimage};
    private Integer[] imageId = {R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage};

    private User user;
    private boolean admin;
    private TextView textViewUsername;

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


        textViewUsername = (TextView) findViewById(R.id.textViewUsername);

        //Get the user data from shared preference
        getUserData();
        textViewUsername.setText(user.getUsername());
        if(admin)
            Toast.makeText(this,"user is admin",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"user is not an admin",Toast.LENGTH_LONG).show();

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




        if(admin) {
            adapter = new CustomListAdapter(this, adminArray, adminImageId);

            drawerListView.setAdapter(adapter);

            drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    String selectedItem = adminArray[+position];
                    Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();
                    Intent intent;
                    switch (selectedItem) {
                        case "Events":
                            intent = new Intent(getApplicationContext(), EventList.class);
                            startActivity(intent);
                            break;
                        case "Society":
                            intent = new Intent(getApplicationContext(), SocietyList.class);
                            startActivity(intent);
                            break;
                        case "Promotion":
                            intent = new Intent(getApplicationContext(), PromotionList.class);
                            startActivity(intent);
                            break;
                        case "My Events":
                            intent = new Intent(getApplicationContext(), MyEvent.class);
                            startActivity(intent);
                            break;
                        case "Create Event":
                            intent = new Intent(getApplicationContext(), CreateEvent.class);
                            startActivity(intent);
                            break;
                        case "Log Out":
                            finishActivity(0);
                        default:
                            break;
                    }
                }
            });


        } else {
            adapter = new CustomListAdapter(this, linkArray, imageId);
            drawerListView.setAdapter(adapter);

            drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    String selectedItem = linkArray[+position];
                    Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();
                    Intent intent;
                    switch (selectedItem) {
                        case "Events":
                            intent = new Intent(getApplicationContext(), EventList.class);
                            startActivity(intent);
                            break;
                        case "Society":
                            intent = new Intent(getApplicationContext(), SocietyList.class);
                            startActivity(intent);
                            break;
                        case "Promotion":
                            intent = new Intent(getApplicationContext(), PromotionList.class);
                            startActivity(intent);
                            break;
                        case "My Events":
                            intent = new Intent(getApplicationContext(), MyEvent.class);
                            startActivity(intent);
                            break;

                        case "Log Out":
                            finishActivity(0);
                        default:
                            break;
                    }
                }
            });

        }
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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");
        //simpleDateFormat.applyPattern("d MMM yyyy");
        Date date = new Date();

        String startDate = simpleDateFormat.format(date);
        String endDate = simpleDateFormat.format(date);


        //Society society = new Society("Computer Science Society","Ming Keat","testing","test","test2");
        //Event event = new Event("Computer Science Night", "Code", startDate,endDate,society.getName(),0,"012123","tmk@gmail.com");
        //eventList.add(event);

        //event = new Event("Hackathon", "Coding", startDate,endDate,society.getName(),0,"4","tmk@gmail.com");
        //eventList.add(event);

        //event = new Event("Idea", "Hacking", startDate,endDate,society.getName(),0,"123","tmk@gmail.com");
        //eventList.add(event);

    }

    //This method will return the user data from shared preferences
    protected void getUserData() {

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences",MODE_PRIVATE);
        String json = sharedPreferences.getString(Config.TAG_USER, "");

        user = gson.fromJson(json,User.class);
        admin = sharedPreferences.getBoolean(Config.TAG_ADMIN,false);

    }

/*    private void addDrawerItems() {

        adapter = new ArrayAdapter<String>(this, R.layout.custom_list, linkArray);
        drawerListView.setAdapter(adapter);
    }*/

    private void societyData() {

        //Get event data from database
        Society society = new Society("Computer Science Society","Ming Keat","This is society description part\n" +
                "It may contain multiple lines of text","012-3456789","test@email.com");
        societyList.add(society);
        society = new Society("Accounting Society","Ming Keat","This is society description part\nIt may contain multiple lines of text","012-3456789","test@email.com");
        societyList.add(society);
        society = new Society("Astronomy Society","Ming Keat","This is society description part\n" +
                "It may contain multiple lines text","012-3456789","test@email.com");
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