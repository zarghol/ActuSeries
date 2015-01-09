package actuseries.android.com.actuseries.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 08/01/2015.
 */
public class ListSeriesActivity extends ActionBarActivity {

    private LogAdapterSeries adapter;
    private List<Serie> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_series_activity);

        this.series = new ArrayList<>();
        ListView lv = (ListView) findViewById(R.id.listeSeries);
        TextView noseriesview = (TextView) findViewById(R.id.noseriesTextView);
        lv.setEmptyView(noseriesview);

        GetSeriesTask task = new GetSeriesTask();
        task.execute();
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
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetSeriesTask extends AsyncTask<Void, Void, List<Serie> > {

        @Override
        protected List<Serie> doInBackground(Void... rien) {
            List<Serie> series = null;
            // Debug the task thread name
            Log.d("actuseries", Thread.currentThread().getName() + " récupérations des infos");
            series = AccesBetaseries.recupereInfosMembre();
            return series;
        }

        @Override
        protected void onPostExecute(List<Serie> result) {
            series = result;
            ListView lv = (ListView) findViewById(R.id.listeSeries);

            adapter = new LogAdapterSeries(series, getApplicationContext());
            lv.setAdapter(adapter);

            for (Serie s : series) {
                Log.d("actuseries", s.getNomSerie());
            }
        }
    }
}
