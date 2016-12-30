package rsf2.android.tarc2day;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
 * Created by MingKeat on 25/11/2016.
 */

public class EventAdapter  extends RecyclerView.Adapter<EventAdapter.MyViewHolder> implements Filterable {

    private List<Event> eventList;
    private List<Event> copy;
    private List<Event> filteredList;
    private EventFilter eventFilter;
    TextView textViewShowData;
    int width;
    int height;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName,textViewPrice,textViewTime,textViewDate;
        public ImageView imageView;


        public MyViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textViewEventName);
            //textViewDesc = (TextView) view.findViewById(R.id.textViewEventDetails);
            textViewTime  = (TextView) view.findViewById(R.id.textViewEventTime);
            textViewDate = (TextView) view.findViewById(R.id.textViewEventDate);
            textViewPrice = (TextView) view.findViewById(R.id.textViewEventPrice);
            imageView = (ImageView) view.findViewById(R.id.imageViewEvent);

        }

    }

    public EventAdapter(List<Event> eventList) {
        this(eventList,1000,600);

        getFilter();
    }

    public EventAdapter(List<Event> eventList,int width,int height){
        this.eventList = new ArrayList(eventList);
        this.filteredList = new ArrayList(eventList);
        copy = new ArrayList(eventList);
        this.width = width;
        this.height = height;

        getFilter();
    }

    public EventAdapter(Activity activity, List<Event> eventList, int width, int height) {
        this.eventList = eventList;
        this.width = width;
        this.height = height;

    }

    @Override
    public EventAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_row, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventAdapter.MyViewHolder holder, int position) {
        final Event event = eventList.get(position);
        final Context context;

        context = holder.itemView.getContext();
        holder.textViewName.setText(event.getTitle());
        //holder.textViewDesc.setText(event.getEventDescription());

        SimpleDateFormat inputDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputTimeFormat = new SimpleDateFormat("hh:mm a");

        try {
            if(event.getStartDate().equals(event.getEndDate())){
                Date startDateTime = inputDateTimeFormat.parse(event.getStartDate() + " " + event.getStartTime());
                Date endDateTime = inputDateTimeFormat.parse(event.getEndDate() + " " + event.getEndTime());

                holder.textViewDate.setText(outputDateFormat.format(startDateTime));
                holder.textViewTime.setText(outputTimeFormat.format(startDateTime) + " - " + outputTimeFormat.format(endDateTime));
            }
            else {
                Date startDateTime = inputDateTimeFormat.parse(event.getStartDate() + " " + event.getStartTime());
                Date endDateTime = inputDateTimeFormat.parse(event.getEndDate() + " " + event.getEndTime());

                holder.textViewTime.setText(outputTimeFormat.format(startDateTime) + " - " + outputTimeFormat.format(endDateTime));
                holder.textViewDate.setText(outputDateFormat.format(startDateTime) + " - " + outputDateFormat.format(endDateTime));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(event.getPrice() == 0.0)
            holder.textViewPrice.setText("FREE");
        else{
            DecimalFormat df = new DecimalFormat("#.00");
            holder.textViewPrice.setText("RM" + df.format(event.getPrice()));
        }

        Picasso.with(context).load(event.getImageUrl()).placeholder( R.drawable.progress_animation ).resize(width,height).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EventInfo.class);
                //Need to remember which event was clicked
                //Pass in the position of the event from the arraylist
                intent.putExtra("EVENT",event);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    @Override
    public Filter getFilter() {
        if (eventFilter == null) {
            eventFilter = new EventFilter();
        }

        return eventFilter;
    }



    private class EventFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                List<Event> tempList = new ArrayList<Event>();

                // search content in event list
                for (Event event : copy) {
                    if (event.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(event);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = copy.size();
                filterResults.values = copy;
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            eventList = (ArrayList<Event>) results.values;

            notifyDataSetChanged();
        }
    }
}
