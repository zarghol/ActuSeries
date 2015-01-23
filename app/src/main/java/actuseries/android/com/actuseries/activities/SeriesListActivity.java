package actuseries.android.com.actuseries.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.squareup.otto.Subscribe;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.activities.fragment.SeriesListFragment;
import actuseries.android.com.actuseries.event.EventBus;
import actuseries.android.com.actuseries.event.GetSerieResultEvent;
import actuseries.android.com.actuseries.tasks.GetSeriesTask;

/**
 * Created by Clement on 08/01/2015.
 */
// TODO afficher onglet pour passer entre épisodes a voir uniquement, série actus, et séries archivées
public class SeriesListActivity extends MainMenuActionBarActivity implements android.support.v7.app.ActionBar.TabListener {

    SeriesDisplayPagerAdapter seriesDisplayPagerAdapter;
    ViewPager mViewPager;

    private GetSeriesTask currentTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.series_list_activity);

        /* pour tabs */
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        seriesDisplayPagerAdapter = new SeriesDisplayPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);

        mViewPager.setAdapter(seriesDisplayPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for(int i = 0; i < seriesDisplayPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            getSupportActionBar().addTab(
                  getSupportActionBar().newTab()
                        .setText(seriesDisplayPagerAdapter.getPageTitle(i))
                        .setTabListener(this));
        }

        /* fin */
        this.currentTask = new GetSeriesTask();
        this.currentTask.execute();

        //on s'abonne au bus d'évènements
        EventBus.getInstance().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(this.currentTask != null && this.currentTask.isCancelled()) {
            this.currentTask.execute();
        }
    }

    @Override
    public void onDestroy() {
        //on se désabonne du bus d'évènement
        EventBus.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onStop() {
        if(this.currentTask != null) {
            Log.d("actuseries", "cancelling recup banniere");
            this.currentTask.cancel(true);
            this.currentTask = null;
        }
        super.onPause();
    }

    @Override
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    //on reçoit le message associé à l'évènement de récupération d'une série
    @Subscribe
    public void onGetSeriesTaskResult(GetSerieResultEvent event) {
        /*if (AccesBetaseries.getScreenSize().x == 0) {
            this.getListView().getMeasuredHeight();
            // TODO voir comment récupérer les dimensions d'une cellule !!!
            View v = this.adapter.getView(0, null, null);
            Point p = new Point(v.getWidth(), v.getHeight());
            Log.d("actuseries", "size : "+ p);
            AccesBetaseries.setScreenSize(p);
        }*/

        SeriesListFragment fragment = (SeriesListFragment) this.seriesDisplayPagerAdapter.getItem(mViewPager.getCurrentItem());

        fragment.notifyDataChanged();
    }
}
