package actuseries.android.com.actuseries.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import actuseries.android.com.actuseries.activities.fragment.SeriesDisplay;
import actuseries.android.com.actuseries.activities.fragment.SeriesListFragment;

/**
 * Created by Clement on 20/01/2015.
 */
public class SeriesDisplayPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public SeriesDisplayPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        return SeriesListFragment.newInstance(SeriesDisplay.fromPosition(position));
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return SeriesDisplay.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(SeriesDisplay.fromPosition(position).getLabel());
    }
}