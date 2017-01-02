package rsf2.android.tarc2day;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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

public class PromotionAdapter  extends RecyclerView.Adapter<PromotionAdapter.MyViewHolder> {

    private List<Promotion> promotionList;
    int width, height;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName,textViewDate,textViewPrice,textViewLocation;
        public ImageView imageViewEvent;

        public MyViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textViewPromotionListName);
            textViewDate = (TextView) view.findViewById(R.id.textViewPromotionListDate);
            textViewLocation = (TextView) view.findViewById(R.id.textViewPromotionListLocation);
            textViewPrice = (TextView) view.findViewById(R.id.textViewPromotionListPrice);
            imageViewEvent = (ImageView) view.findViewById(R.id.imageViewPromotion);
        }
    }

    public PromotionAdapter(List<Promotion> promotionList) {
        this(promotionList,1000,600);
    }

    public PromotionAdapter(List<Promotion> promotionList,int width,int height){
        this.promotionList = promotionList;
        this.width = width;
        this.height = height;
    }

    @Override
    public PromotionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promotion_list_row, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(PromotionAdapter.MyViewHolder holder, int position) {
        final Promotion promotion = promotionList.get(position);
        final Context context = holder.itemView.getContext();
        holder.textViewName.setText(promotion.getTitle());
        //holder.textViewDesc.setText(promotion.getDescription());

        if(promotion.getStartDate().equals(promotion.getEndDate())) {
            holder.textViewDate.setText(promotion.getStartDate());
        } else {
            holder.textViewDate.setText(promotion.getStartDate() + " - " + promotion.getEndDate());
        }

        if(promotion.getPrice() == 0) {
            holder.textViewPrice.setText("FREE");
        } else {
            holder.textViewPrice.setText("RM" + promotion.getPrice());
        }

        holder.textViewLocation.setText(promotion.getLocation());

        Picasso.with(context).load(promotion.getImageUrl()).placeholder( R.drawable.progress_animation ).resize(width,height).into(holder.imageViewEvent);

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
