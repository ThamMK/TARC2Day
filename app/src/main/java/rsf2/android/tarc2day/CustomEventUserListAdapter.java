package rsf2.android.tarc2day;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shiangyoung on 25/12/2016.
 */

public class CustomEventUserListAdapter extends ArrayAdapter<RegisterdEvent>{

    ArrayList<RegisterdEvent> event;
    Context context;
    int resouce;



    public CustomEventUserListAdapter(Context context, int resource,ArrayList<RegisterdEvent> objects) {
        super(context, resource, objects);
        this.event = objects;
        this.context = context;
        this.resouce = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_event_list_layout,null,true);
        }

        Event event = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.myEventListImage);
        Picasso.with(context).load(event.getImageUrl()).fit().into(imageView);

        TextView textViewEvent = (TextView) convertView.findViewById(R.id.eventListTitle);
        textViewEvent.setText(event.getTitle());

        TextView textViewDate = (TextView) convertView.findViewById(R.id.eventListDate);
        TextView textViewTime = (TextView) convertView.findViewById(R.id.eventListTime);

        SimpleDateFormat inputDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputTimeFormat = new SimpleDateFormat("hh:mm a");

        try {
            if(event.getStartDate().equals(event.getEndDate())){
                Date startDateTime = inputDateTimeFormat.parse(event.getStartDate() + " " + event.getStartTime());
                Date endDateTime = inputDateTimeFormat.parse(event.getEndDate() + " " + event.getEndTime());

                textViewDate.setText(outputDateFormat.format(startDateTime));
                textViewTime.setText(outputTimeFormat.format(startDateTime) + " - " + outputTimeFormat.format(endDateTime));
            }
            else {
                Date startDateTime = inputDateTimeFormat.parse(event.getStartDate() + " " + event.getStartTime());
                Date endDateTime = inputDateTimeFormat.parse(event.getEndDate() + " " + event.getEndTime());

                textViewTime.setText(outputTimeFormat.format(startDateTime) + " - " + outputTimeFormat.format(endDateTime));
                textViewDate.setText(outputDateFormat.format(startDateTime) + " - " + outputDateFormat.format(endDateTime));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
