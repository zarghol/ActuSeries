package actuseries.android.com.actuseries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.event.EventBus;
import actuseries.android.com.actuseries.event.GetEpisodesResultEvent;
import actuseries.android.com.actuseries.metier.Episode;
import actuseries.android.com.actuseries.tasks.GetEpisodesTask;

/**
 * Created by Clement on 08/01/2015.
 */
public class ListEpisodesActivity extends ActionBarActivity {

	private LogAdapterEpisodes adapter;
	private List<Episode> episodes;
    ListView lv;
	private Integer numSerie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.list_episodes_activity);
		this.numSerie = this.getIntent().getExtras().getInt("numSerie", 0);

		lv = (ListView) findViewById(R.id.listeEpisodes);
        lv.setEmptyView(findViewById(R.id.noEpisodesTextView));

        this.episodes = new ArrayList<>();

	    new GetEpisodesTask().execute(numSerie);
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

    //on reçoit le message associé à l'évènement de récupération des épisodes
    @Subscribe
    public void onGetEpisodesTaskResult(GetEpisodesResultEvent event) {
        Log.d("actuseries", "nb episodes: " + episodes.size());
        episodes = event.getEpisodes();
        Log.d("actuseries", "nb episodes: " + episodes.size());
        adapter = new LogAdapterEpisodes(episodes, getApplicationContext());
        Log.d("actuseries", "nb episodes: " + episodes.size());
        lv.setAdapter(adapter);
    }
}
