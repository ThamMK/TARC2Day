package rsf2.android.tarc2day;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MingKeat on 25/11/2016.
 */

public class EventAdapter  extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private List<Event> eventList;
    TextView textViewShowData;
    int width;
    int height;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName,textViewDesc,textViewPrice;
        public ImageView imageView;


        public MyViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textViewEventName);
            textViewDesc = (TextView) view.findViewById(R.id.textViewEventDetails);
            textViewPrice = (TextView) view.findViewById(R.id.textViewEventPrice);
            imageView = (ImageView) view.findViewById(R.id.imageViewEvent);

        }
    }

    public EventAdapter(List<Event> eventList) {
        this(eventList,1000,600);
    }

    public EventAdapter(List<Event> eventList,int width,int height){
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
        holder.textViewDesc.setText(event.getEventDescription());
        holder.textViewPrice.setText("" + event.getPrice());

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



}
