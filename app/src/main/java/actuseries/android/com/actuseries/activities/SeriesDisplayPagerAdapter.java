package actuseries.android.com.actuseries.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import actuseries.android.com.actuseries.activities.fragment.SeriesDisplay;
import actuseries.android.com.actuseries.activities.fragment.SeriesListFragment;

/**
 * Created by Clement on 20/01/2015.
 */
public class SeriesDisplayPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<SeriesListFragment> fragments;

    public SeriesDisplayPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.fragments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.fragments.add(SeriesListFragment.newInstance(SeriesDisplay.fromPosition(i)));
        }
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return this.fragments.get(position);
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