package actuseries.android.com.actuseries.activities;

import java.util.ArrayList;
import java.util.List;

import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.metier.Episode;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import actuseries.android.com.actuseries.*;

/**
 * Created by Clement on 08/01/2015.
 */
public class ListEpisodesActivity extends ActionBarActivity {

	private LogAdapterEpisodes adapter;
	private List<Episode> episodes;
	Bundle b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.list_episodes_activity);
		int numSerie = this.getIntent().getExtras().getInt("numSerie", 0);

		ListView lv = (ListView) findViewById(R.id.listeEpisodes);
        lv.setEmptyView(findViewById(R.id.noEpisodesTextView));

        this.episodes = AccesBetaseries.getSeries().get(numSerie).getEpisodes();
        if (this.episodes == null) {
            this.episodes = new ArrayList<>();
        }
		adapter = new LogAdapterEpisodes(this.episodes, getBaseContext());
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
}
