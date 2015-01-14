package actuseries.android.com.actuseries.activities;

import java.util.ArrayList;
import java.util.List;

import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.metier.Episode;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import actuseries.android.com.actuseries.*;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 08/01/2015.
 */
public class ListEpisodesActivity extends ActionBarActivity {

	private LogAdapterEpisodes adapter;
	private List<Episode> episodes;
	private int numSerie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.list_episodes_activity);
		this.numSerie = this.getIntent().getExtras().getInt("numSerie", 0);

		ListView lv = (ListView) findViewById(R.id.listeEpisodes);
        lv.setEmptyView(findViewById(R.id.noEpisodesTextView));

        this.episodes = new ArrayList<>();

		GetEpisodesTask task = new GetEpisodesTask();
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

    private class GetEpisodesTask extends AsyncTask<Void, Void, List<Episode> > {

        @Override
        protected List<Episode> doInBackground(Void... rien) {

            // Debug the task thread name
            Log.d("actuseries", Thread.currentThread().getName() + " récupérations des infos");
            return AccesBetaseries.recupereEpisodes(AccesBetaseries.getSeries().get(numSerie));
        }

        @Override
        protected void onPostExecute(List<Episode> result) {
            episodes = result;
            ListView lv = (ListView) findViewById(R.id.listeEpisodes);

            adapter = new LogAdapterEpisodes(episodes, getApplicationContext());
            lv.setAdapter(adapter);
        }
    }
}
