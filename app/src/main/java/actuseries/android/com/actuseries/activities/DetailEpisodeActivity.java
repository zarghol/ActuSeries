package actuseries.android.com.actuseries.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.activities.fragment.TypeSeriesDisplayed;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.metier.Episode;

public class DetailEpisodeActivity extends ActionBarActivity {

    private Episode episode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_episode);

        int indexSerie = this.getIntent().getExtras().getInt("indexSerie", 0);
        int indexEpisode = this.getIntent().getExtras().getInt("indexEpisode", 0);
        TypeSeriesDisplayed typeSeriesDisplayed = TypeSeriesDisplayed.fromPosition(this.getIntent().getIntExtra("typePosition", 0));
        this.episode = AccesBetaseries.getSeries(typeSeriesDisplayed).get(indexSerie).getEpisodes().get(indexEpisode);

        TextView label = (TextView) findViewById(R.id.textView_label_episode);
        String text = "Épisode " + this.episode.getSaison() + "x" + this.episode.getNumEpisode() + " - " + this.episode.getNomEpisode();
        label.setText(text);

        TextView description = (TextView) findViewById(R.id.textView_description_episode);
        description.setText(this.episode.getDescriptionEpisode());

        RatingBar bar = (RatingBar) findViewById(R.id.ratingBar_note_episode);
        bar.setRating(3.0f);

        if (this.episode.estVue()) {
            Button boutonVue = (Button) findViewById(R.id.button_vue);
            boutonVue.setText(R.string.nonvu);
        }

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
