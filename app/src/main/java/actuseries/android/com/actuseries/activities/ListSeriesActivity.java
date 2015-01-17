package actuseries.android.com.actuseries.activities;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.event.EventBus;
import actuseries.android.com.actuseries.event.GetSerieResultEvent;
import actuseries.android.com.actuseries.event.GetSeriesResultEvent;
import actuseries.android.com.actuseries.metier.Serie;
import actuseries.android.com.actuseries.tasks.GetSeriesTask;

/**
 * Created by Clement on 08/01/2015.
 */
// TODO afficher onglet pour passer entre épisodes a voir uniquement, série actus, et séries archivées
public class ListSeriesActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private LogAdapterSeries adapter;
    private List<Serie> series;
    private ListView lv;
    private GetSeriesTask currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_series_activity);

        this.lv = (ListView) findViewById(R.id.listeSeries);
        this.lv.setEmptyView(findViewById(R.id.noseriesTextView));
        this.lv.setOnItemClickListener(this);

        this.series = AccesBetaseries.getSeries();

        this.adapter = new LogAdapterSeries(this.series, getApplicationContext());
        this.lv.setAdapter(this.adapter);

        this.currentTask = new GetSeriesTask();
        this.currentTask.execute();

        //on s'abonne au bus d'évènements
        EventBus.getInstance().register(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.currentTask != null && this.currentTask.isCancelled()) {
            this.currentTask.execute();
        }
    }

    @Override
    protected void onDestroy(){
        //on se désabonne du bus d'évènement
        EventBus.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if (this.currentTask != null) {
            Log.d("actuseries", "cancelling recup banniere");
            this.currentTask.cancel(true);
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_deconnexion) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AccesBetaseries.deconnexionMembre();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent j = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(j);
                            finish();
                        }
                    });
                }
            }).start();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent j = new Intent(this, ListEpisodesActivity.class);
        j.putExtra("numSerie", position);

        startActivityForResult(j, 1);
    }

    //on reçoit le message associé à l'évènement de récupération des séries
    @Subscribe
    public void onGetSeriesTaskResult(GetSeriesResultEvent event) {
        this.series = event.getSeries();
        this.adapter = new LogAdapterSeries(series, getApplicationContext());
        this.lv.setAdapter(this.adapter);

        for (Serie s : this.series) {
            Log.d("actuseries", s.getNomSerie());
        }
    }

    //on reçoit le message associé à l'évènement de récupération d'une série
    @Subscribe
    public void onGetSeriesTaskResult(GetSerieResultEvent event) {
        if (AccesBetaseries.getScreenSize().x == 0) {
            this.lv.getMeasuredHeight();
            // TODO voir comment récupérer les dimensions d'une cellule !!!
            View v = this.adapter.getView(0, null, null);
            Point p = new Point(v.getWidth(), v.getHeight());
            Log.d("actuseries", "size : "+ p);
            AccesBetaseries.setScreenSize(p);
        }

        adapter.notifyDataSetChanged();
    }
}
