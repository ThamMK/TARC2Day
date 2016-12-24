package rsf2.android.tarc2day;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by MingKeat on 27/11/2016.
 */

public class EventInfoAdapter extends FragmentStatePagerAdapter{

    int numTabs;
    Event event;


    public EventInfoAdapter(FragmentManager fm, int numTabs, Event event) {
        super(fm);
        this.numTabs = numTabs;
        this.event = event;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                EventDetailFragment eventDetailFragment = new EventDetailFragment();

                Bundle bundle = new Bundle();
                bundle.putString("Description",event.getEventDescription());
                eventDetailFragment.setArguments(bundle);
                return eventDetailFragment;
            case 1:
                EventLocationFragment eventLocationFragment = new EventLocationFragment();
                return eventLocationFragment;
            case 2:
                EventQuestionFragment eventQuestionFragment = new EventQuestionFragment();
                return eventQuestionFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
