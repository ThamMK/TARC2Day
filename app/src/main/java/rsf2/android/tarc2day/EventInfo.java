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

public class EventInfo extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewTime;
    private TextView textViewDate;
    private TextView textViewPrice;
    private TextView textViewContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Event event = intent.getParcelableExtra("EVENT");
        getDetails(event);


    }

    protected void getDetails(Event event) {



        textViewTitle = (TextView) findViewById(R.id.textViewEventInfoName);
        textViewTitle.setText(event.getTitle());

        textViewDate = (TextView) findViewById(R.id.textViewEventInfoDate);
        textViewDate.setText(event.getStartDate() + " - " + event.getEndDate());

        textViewPrice = (TextView) findViewById(R.id.textViewEventInfoPrice);
        textViewPrice.setText("RM " + event.getPrice());

        textViewContact = (TextView) findViewById(R.id.textViewEventInfoContact);
        textViewContact.setText(event.getContactNo());
    }

}
