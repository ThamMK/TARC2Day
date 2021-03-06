package rsf2.android.tarc2day;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by MingKeat on 5/12/2016.
 */

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemName;
    private final Integer[] imageId;


    public CustomListAdapter(Activity context, String[] itemName, Integer[] imageId) {
        super(context, R.layout.custom_list, itemName);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemName=itemName;
        this.imageId=imageId;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_list, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.navListItem);



        txtTitle.setText(itemName[position]);
        txtTitle.setCompoundDrawablesWithIntrinsicBounds( imageId[position], 0, 0, 0);

        //imageView.setImageResource(imageId[position]);



        return rowView;

    };
}
