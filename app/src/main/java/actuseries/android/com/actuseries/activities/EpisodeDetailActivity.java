package actuseries.android.com.actuseries.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.activities.fragment.SeriesDisplay;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.metier.Episode;

public class EpisodeDetailActivity extends MainMenuActionBarActivity {

    private Episode episode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_detail_activity);

        int indexSerie = this.getIntent().getExtras().getInt("indexSerie", 0);
        int indexEpisode = this.getIntent().getExtras().getInt("indexEpisode", 0);
        SeriesDisplay seriesDisplay = SeriesDisplay.fromPosition(this.getIntent().getIntExtra("typePosition", 0));
        this.episode = AccesBetaseries.getSeries(seriesDisplay).get(indexSerie).getEpisodes().get(indexEpisode);

        TextView label = (TextView) findViewById(R.id.textView_label_episode);
        String text = "Épisode " + this.episode.getSaison() + "x" + this.episode.getNumEpisode() + " - " + this.episode.getNomEpisode();
        label.setText(text);

        TextView description = (TextView) findViewById(R.id.textView_description_episode);
        description.setText(this.episode.getDescriptionEpisode());

        RatingBar bar = (RatingBar) findViewById(R.id.ratingBar_note_episode);
        bar.setRating(3.0f);

        if(this.episode.estVue()) {
            Button boutonVue = (Button) findViewById(R.id.button_vue);
            boutonVue.setText(R.string.episode_detail_activity_unmark_watched);
        }
    }
}
