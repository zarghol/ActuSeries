package actuseries.android.com.actuseries.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import actuseries.android.com.actuseries.activities.fragment.ListSerieFragment;
import actuseries.android.com.actuseries.activities.fragment.TypeSeriesDisplayed;

/**
 * Created by Clement on 20/01/2015.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        return ListSerieFragment.newInstance(TypeSeriesDisplayed.fromPosition(position));
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return TypeSeriesDisplayed.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TypeSeriesDisplayed.fromPosition(position).getLabel();
    }
}