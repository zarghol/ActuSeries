package actuseries.android.com.actuseries.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.event.GetSeriesResultEvent;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.metier.Serie;
import actuseries.android.com.actuseries.tasks.GetSeriesTask;
import actuseries.android.com.actuseries.tasks.SearchTask;


/**
 * Created by Clement on 08/01/2015.
 */
public class SearchActivity extends ActionBarActivity {
    private List<Serie> listSerie;
    private SeriesLogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        listSerie = new ArrayList<Serie>();
        // Set up the ViewPager with the sections adapter.
        adapter = new SeriesLogAdapter(listSerie, getBaseContext());

        ListView listSearch = (ListView) findViewById(R.id.list_search);
        listSearch.setAdapter(adapter);
        final EditText searchText = (EditText) findViewById(R.id.searchText);
        searchText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void afterTextChanged(Editable s) {
                String[] listNom = {searchText.getText().toString()};
                TaskManager.launchTask(SearchTask.class, listNom);
            }
        });

    }

    @Override
    public void onStop() {
        TaskManager.cancelTask(SearchTask.class);
        super.onPause();
    }

    //on reçoit le message associé à l'évènement de récupération d'une série
    @Subscribe
    public void onGetSeriesTaskResult(GetSeriesResultEvent event) {
        this.listSerie = event.getSeries();
        adapter.notifyDataSetChanged();

    }


}
