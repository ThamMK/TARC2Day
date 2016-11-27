package rsf2.android.tarc2day;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by MingKeat on 27/11/2016.
 */

public class EventInfoAdapter extends FragmentStatePagerAdapter{

    int numTabs;

    public EventInfoAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;

    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                EventDetailFragment eventDetailFragment = new EventDetailFragment();
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
