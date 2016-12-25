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
    String locationName, locationLat, locationLong;


    public EventInfoAdapter(FragmentManager fm, int numTabs, Event event,String locationName, String locationLat, String locationLong) {
        super(fm);
        this.numTabs = numTabs;
        this.event = event;
        this.locationName = locationName;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
    }


    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();

        switch (position) {
            case 0:
                EventDetailFragment eventDetailFragment = new EventDetailFragment();

                bundle.putString("Description",event.getEventDescription());
                eventDetailFragment.setArguments(bundle);
                return eventDetailFragment;
            case 1:
                EventLocationFragment eventLocationFragment = new EventLocationFragment();
                bundle.putString("locationName",locationName);
                bundle.putString("locationLat",locationLat);
                bundle.putString("locationLong",locationLong);
                eventLocationFragment.setArguments(bundle);
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
