package rsf2.android.tarc2day;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MingKeat on 25/11/2016.
 */

public class PromotionAdapter  extends RecyclerView.Adapter<PromotionAdapter.MyViewHolder> {

    private List<Promotion> promotionList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName,textViewDesc,textViewPrice;

        public MyViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textViewEventName);
            textViewDesc = (TextView) view.findViewById(R.id.textViewEventDetails);
            textViewPrice = (TextView) view.findViewById(R.id.textViewEventPrice);
        }
    }

    public PromotionAdapter(List<Promotion> promotionList) {
        this.promotionList = promotionList;
    }

    @Override
    public PromotionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_row, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(PromotionAdapter.MyViewHolder holder, int position) {
        final Promotion promotion = promotionList.get(position);
        holder.textViewName.setText(promotion.getTitle());
        holder.textViewDesc.setText(promotion.getDescription());
        holder.textViewPrice.setText("" + promotion.getPrice());

        final Context context = holder.itemView.getContext();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PromotionInfo.class);
                //Need to remember which event was clicked
                //Pass in the position of the event from the arraylist
                intent.putExtra("PROMOTION",promotion);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return promotionList.size();
    }
}
