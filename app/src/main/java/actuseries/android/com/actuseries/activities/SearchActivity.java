package actuseries.android.com.actuseries.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.event.GetSeriesResultEvent;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.metier.Serie;
import actuseries.android.com.actuseries.tasks.SearchTask;


/**
 * Created by Clement on 08/01/2015.
 */
public class SearchActivity extends MainMenuActionBarActivity implements SearchView.OnQueryTextListener {
    private List<Serie> listSerie;
    private SeriesLogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        this.listSerie = new ArrayList<>();
        this.adapter = new SeriesLogAdapter(this.listSerie, getBaseContext());

        ListView listSearch = (ListView) findViewById(R.id.list_search);
        listSearch.setAdapter(this.adapter);

        SearchView searchText = (SearchView) findViewById(R.id.search_view);
        searchText.setOnQueryTextListener(this);
    }

    //on reçoit le message associé à l'évènement de récupération d'une série
    @Subscribe
    public void onGetSeriesTaskResult(GetSeriesResultEvent event) {
        this.listSerie.clear();
        this.listSerie.addAll(event.getSeries());
        Log.d("actuseries", "recherche, nb series recues : " + this.listSerie.size());
        this.adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        String[] listNom = {query.replace(" ", "+")};
/*        SearchTask task = new SearchTask();
        task.execute(listNom);*/
        TaskManager.launchTask(SearchTask.class, listNom);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
