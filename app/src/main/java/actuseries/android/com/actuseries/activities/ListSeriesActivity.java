package actuseries.android.com.actuseries.activities;

import android.content.Intent;
import android.os.AsyncTask;
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
import actuseries.android.com.actuseries.event.GetSeriesResultEvent;
import actuseries.android.com.actuseries.event.LoginResultEvent;
import actuseries.android.com.actuseries.metier.Serie;
import actuseries.android.com.actuseries.tasks.GetSeriesTask;

/**
 * Created by Clement on 08/01/2015.
 */
public class ListSeriesActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private LogAdapterSeries adapter;
    private List<Serie> series;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_series_activity);

        this.series = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listeSeries);
        TextView noseriesview = (TextView) findViewById(R.id.noseriesTextView);
        lv.setEmptyView(noseriesview);

        new GetSeriesTask().execute();
        lv.setOnItemClickListener(this);
        //on s'abonne au bus d'évènements
        EventBus.getInstance().register(this);

    }

    @Override
    protected void onDestroy(){
        //on se désabonne du bus d'évènement
        EventBus.getInstance().unregister(this);
        super.onDestroy();
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

/*        if (this.series.get(position).getEpisodes().size() == 0) {
            this.taskEpisodes.cancel(true);
        }*/
        startActivityForResult(j, 1);
    }

    //on reçoit le message associé à l'évènement de récupération des séries
    @Subscribe
    public void onGetSeriesTaskResult(GetSeriesResultEvent event) {
        series = event.getSeries();
        adapter = new LogAdapterSeries(series, getApplicationContext());
        lv.setAdapter(adapter);

        for (Serie s : series) {
            Log.d("actuseries", s.getNomSerie());
        }
    }
}
