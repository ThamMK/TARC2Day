package rsf2.android.tarc2day;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by MingKeat on 26/12/2016.
 */

public class CustomMessageListAdapter extends ArrayAdapter<Message> {
    public CustomMessageListAdapter(Context context, Message[] messageArray) {
        super(context, 0, messageArray);

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message message = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.messages,parent,false);
        }

        //Set the data
        TextView textViewMessageUsername = (TextView) convertView.findViewById(R.id.message_user);
        TextView textViewMessage = (TextView) convertView.findViewById(R.id.message_text);
        TextView textViewDate = (TextView) convertView.findViewById(R.id.message_date);

        textViewMessageUsername.setText(message.getUsername());
        textViewDate.setText(message.getMessageDate().toString());
        textViewMessage.setText(message.getMessage());

        return convertView;


    }
}
