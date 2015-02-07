package actuseries.android.com.actuseries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.squareup.otto.Subscribe;

import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.betaseries.BaseBetaSeriesCaller;
import actuseries.android.com.actuseries.event.GetSeriesResultEvent;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.locator.BetaSeriesCallerLocator;
import actuseries.android.com.actuseries.metier.Serie;
import actuseries.android.com.actuseries.tasks.SearchTask;


/**
 * Created by Clement on 08/01/2015.
 */
public class SearchActivity extends MainMenuActionBarActivity implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {
    private List<Serie> listSerie;
    private SeriesLogAdapter adapter;
    private ProgressBar loadingProgressBar;
    private ListView listSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        this.listSerie = BetaSeriesCallerLocator.getService().getSearchResults();
        this.adapter = new SeriesLogAdapter(this.listSerie, getBaseContext());
        this.loadingProgressBar = (ProgressBar) findViewById(R.id.search_progressBar_loading);

        listSearch = (ListView) findViewById(R.id.list_search);
        listSearch.setAdapter(this.adapter);

        listSearch.setOnItemClickListener(this);

        SearchView searchText = (SearchView) findViewById(R.id.search_view);
        searchText.setOnQueryTextListener(this);
    }

    //on reçoit le message associé à l'évènement de récupération d'une série
    @Subscribe
    public void onGetSeriesTaskResult(GetSeriesResultEvent event) {
        Log.d("actuseries", "recherche, nb series recues : " + this.listSerie.size());
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_search).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        String[] listNom = {query.replace(" ", "+")};
/*        SearchTask task = new SearchTask();
        task.execute(listNom);*/
        TaskManager.launchTask(SearchTask.class, listNom);
        listSearch.setEmptyView(loadingProgressBar);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent j = new Intent(this, SerieDetailActivitySimple.class);
        j.putExtra("numSerie", position);

        startActivityForResult(j, 1);
    }
}
