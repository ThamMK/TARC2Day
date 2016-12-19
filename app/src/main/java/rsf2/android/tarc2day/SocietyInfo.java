package rsf2.android.tarc2day;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class SocietyInfo extends AppCompatActivity{

    private TextView textViewName;
    private TextView textViewPersonInCharge;
    private TextView textViewDescription;
    private TextView textViewContactNo;
    private TextView textViewEmail;
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Society society = intent.getParcelableExtra("SOCIETY");
        getDetails(society);
//
//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutSocietyInfo);
//        tabLayout.addTab(tabLayout.newTab().setText("Details"));
//        tabLayout.addTab(tabLayout.newTab().setText("Location"));
//        tabLayout.addTab(tabLayout.newTab().setText("Comment"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerSocietyInfo);
//        final EventInfoAdapter adapter = new EventInfoAdapter
//                (getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

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
    }
}
