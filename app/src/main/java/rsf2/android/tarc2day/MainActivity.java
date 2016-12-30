package rsf2.android.tarc2day;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private SliderLayout mDemoSlider;
    private List<Event> eventList = new ArrayList<Event>();
    private List<Society> societyList = new ArrayList<Society>();
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private TextView textViewViewAll;

    private DrawerLayout drawerLayout;
    private LinearLayout drawerLinearLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ImageView toolbarImage;
    private ImageView navDrawerBack;
    private ListView drawerListView;
    private CustomListAdapter adapter;
    private String[] linkArray = { "Events" ,"Society", "Promotion", "My Events", "My Account" , "About us", "Log Out"};
    private String[] adminArray = { "Events" ,"Society", "Promotion", "My Events", "My Account" ,"Create Event", "Create Promotion", "Create Society","About us", "Log Out"};
    private Integer[] adminImageId = {R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage, R.drawable.logoimage, R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage, R.drawable.logoimage};
    private Integer[] imageId = {R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage,R.drawable.logoimage};

    private User user;
    private boolean admin;
    private TextView textViewName;
    private CircleImageView profilePictureView;

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


        textViewName = (TextView) findViewById(R.id.textViewName);
        profilePictureView = (CircleImageView) findViewById(R.id.imageViewNavDrawer);
        //Get the user data from shared preference
        getUserData();
        textViewName.setText(user.getName());


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

        BackgroundEventTask backgroundEventTask = new BackgroundEventTask();
        backgroundEventTask.execute();


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
                        case "Create Promotion":
                            intent = new Intent(getApplicationContext(), PromotionCreate.class);
                            startActivity(intent);
                            break;
                        case "Create Society":
                            intent = new Intent(getApplicationContext(), CreateSociety.class);
                            startActivity(intent);
                            break;
                        case "My Account":
                            intent = new Intent(getApplicationContext(), MyAccount.class);
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
                        case "My Account":
                            intent = new Intent(getApplicationContext(), MyAccount.class);
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

        BackgroundProfileTask backgroundProfileTask = new BackgroundProfileTask();
        backgroundProfileTask.execute(user.getUsername());

    }

    class BackgroundProfileTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            RequestHandler requestHandler = new RequestHandler();

            HashMap<String,String> parameter = new HashMap<>();
            parameter.put("username",params[0]);

            String response = requestHandler.sendPostRequest(Config.URL_GET_USER,parameter);
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String encodedImage = jsonObject.getString("profilePicture");

                user.setPassword(jsonObject.getString(Config.KEY_USER_PASSWORD));
                if(!encodedImage.isEmpty()) {
                    Bitmap profilePicture = User.base64ToBitmap(encodedImage);

                    user.setProfilePicture(profilePicture);


                    profilePictureView.setImageBitmap(user.getProfilePicture());
                } else {
                    Toast.makeText(getApplicationContext(),"No image", Toast.LENGTH_LONG).show();
                    profilePictureView.setImageResource(R.drawable.fileimage);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

/*    private void addDrawerItems() {

        adapter = new ArrayAdapter<String>(this, R.layout.custom_list, linkArray);
        drawerListView.setAdapter(adapter);
    }*/



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


    class BackgroundEventTask extends AsyncTask<Void,Void,String> {

        int width;
        int height;

        JSONArray jsonArray;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {
            String result="";

           RequestHandler requestHandler = new RequestHandler();
           result = requestHandler.sendGetRequest(Config.URL_GET_EVENT_MAIN);


            try {
                jsonArray = new JSONArray(result);
                String eventId, name, description, startDate, endDate, startTime, endTime, email, contactNumber, societyId, societyName,locationName,encodedImage,locationId;
                String uri;
                Double price;
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject JO = jsonArray.getJSONObject(i);
                    eventId = JO.getString("eventId");
                    name = JO.getString("name");
                    description = JO.getString("description");
                    startDate = JO.getString("startDate");
                    endDate = JO.getString("endDate");
                    startTime = JO.getString("startTime");
                    endTime = JO.getString("endTime");
                    price = Double.parseDouble(JO.getString("price"));
                    email = JO.getString("email");
                    contactNumber = JO.getString("contactNumber");
                    societyId = JO.getString("societyId");
                    societyName = JO.getString("societyName");
                    locationId = JO.getString("locationId");
                    locationName = JO.getString("locationName");

                    //encodedImage = JO.getString("image");
                    uri = JO.getString("imageUrl");

                    Event event = new Event(eventId,name,description,startDate,endDate,startTime,endTime,societyName,price,contactNumber,email,locationId,locationName,uri);
                    eventList.add(event);

                }
                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                width = displaymetrics.widthPixels;
                height = dpToPx(200);


            }
            catch(JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            eventAdapter = new EventAdapter(eventList,width,height);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(eventAdapter);


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public int dpToPx(int dp) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return Math.round(px);
    }

    public void startActivityMyAccount(View view) {

        Intent intent = new Intent(MainActivity.this, MyAccount.class);
        startActivity(intent);

    }

}