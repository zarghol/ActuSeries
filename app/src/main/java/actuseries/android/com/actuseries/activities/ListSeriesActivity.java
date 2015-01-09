package actuseries.android.com.actuseries.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 08/01/2015.
 */
public class ListSeriesActivity extends ActionBarActivity implements Observer {

    private LogAdapterSeries adapter;
    private List<Serie> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_series_activity);

        ListView lv = (ListView) findViewById(R.id.listeSeries);
        AccesBetaseries.addObs(this);
        AccesBetaseries.recupereInfosMembre();
        this.series = new ArrayList<>();

        adapter = new LogAdapterSeries(this.series, getBaseContext());
        lv.setAdapter(adapter);
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


    @Override
    public void update(Observable observable, Object data) {
        Log.d("actuseries", "update des series : " + data);
        this.series = (List<Serie>) data;

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();

            }
        });
    }
}
