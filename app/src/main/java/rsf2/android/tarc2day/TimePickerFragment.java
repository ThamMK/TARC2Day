package rsf2.android.tarc2day;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by MingKeat on 3/12/2016.
 */

public class TimePickerFragment extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        int hours = date.getHours();
        int minutes = date.getMinutes();

        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hours,minutes,true);
    }

}
