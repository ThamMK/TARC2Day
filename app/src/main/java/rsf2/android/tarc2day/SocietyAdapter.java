package rsf2.android.tarc2day;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SocietyAdapter extends RecyclerView.Adapter<SocietyAdapter.MyViewHolder> {

    private List<Society> societyList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName,textViewPersonInCharge,textViewDescription,textViewContactNo,textViewEmail;

        public MyViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textViewSocietyName);
            textViewPersonInCharge = (TextView) view.findViewById(R.id.textViewSocietyPersonInCharge);
        }
    }

    public SocietyAdapter(List<Society> societyList) {
        this.societyList = societyList;
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
//        holder.textViewDescription.setText(society.getDescription());
//        holder.textViewContactNo.setText(society.getContactNo());
//        holder.textViewEmail.setText(society.getContactNo());

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
