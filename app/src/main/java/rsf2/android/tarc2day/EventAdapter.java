package rsf2.android.tarc2day;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MingKeat on 25/11/2016.
 */

public class EventAdapter  extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private List<Event> eventList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName,textViewDesc,textViewPrice;

        public MyViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textViewEventName);
            textViewDesc = (TextView) view.findViewById(R.id.textViewEventDetails);
            textViewPrice = (TextView) view.findViewById(R.id.textViewEventPrice);
        }
    }

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public EventAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_row, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(EventAdapter.MyViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.textViewName.setText(event.getTitle());
        holder.textViewDesc.setText(event.getEventDescription());
        holder.textViewPrice.setText("" + event.getPrice());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
