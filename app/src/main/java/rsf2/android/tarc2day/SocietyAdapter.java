package rsf2.android.tarc2day;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SocietyAdapter extends RecyclerView.Adapter<SocietyAdapter.MyViewHolder> {

    private List<Society> societyList;
    int width, height;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName,textViewPersonInCharge,textViewDescription,textViewContactNo,textViewEmail;
        public ImageView imageViewSociety;
        public MyViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textViewSocietyName);
            textViewPersonInCharge = (TextView) view.findViewById(R.id.textViewSocietyPersonInCharge);
            imageViewSociety = (ImageView) view.findViewById(R.id.imageViewSociety);
        }
    }

    public SocietyAdapter(List<Society> societyList) {
        this(societyList,1000,600);
    }

    public SocietyAdapter(List<Society> societyList,int width,int height){
        this.societyList = societyList;
        this.width = width;
        this.height = height;
    }

    @Override
    public SocietyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.society_list_row, parent, false);


        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(SocietyAdapter.MyViewHolder holder, int position) {
        final Society society = societyList.get(position);
        final Context context;
        context = holder.itemView.getContext();
        holder.textViewName.setText(society.getName());
        holder.textViewPersonInCharge.setText(society.getPersonInCharge());
        Picasso.with(context).load(society.getImageUrl()).placeholder( R.drawable.progress_animation ).resize(width,height).into(holder.imageViewSociety);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SocietyInfo.class);
                //Need to remember which society was clicked
                //Pass in the position of the society from the arraylist
                intent.putExtra("SOCIETY",society);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return societyList.size();
    }
}
