package actuseries.android.com.actuseries.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.activities.fragment.SeriesDisplay;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.metier.Episode;

public class EpisodeDetailActivity extends MainMenuActionBarActivity implements View.OnClickListener {

    private Episode episode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_detail_activity);

        int indexSerie = this.getIntent().getExtras().getInt("indexSerie", 0);
        int indexEpisode = this.getIntent().getExtras().getInt("indexEpisode", 0);
        SeriesDisplay seriesDisplay = SeriesDisplay.fromPosition(this.getIntent().getIntExtra("typePosition", 0));
        this.episode = AccesBetaseries.getSeries(seriesDisplay).get(indexSerie).getEpisodes().get(indexEpisode);

        TextView label = (TextView) findViewById(R.id.episodeDetail_textView_title);
        String text = "Épisode " + this.episode.getSaison() + "x" + this.episode.getNumEpisode() + " - " + this.episode.getNomEpisode();
        label.setText(text);

        TextView description = (TextView) findViewById(R.id.episodeDetail_textView_summary);
        description.setText(this.episode.getDescriptionEpisode());

        RatingBar bar = (RatingBar) findViewById(R.id.episodeDetail_ratingBar_rating);
        bar.setRating(3.0f);

        this.setBouton().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                episode.toggleVue();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setBouton();
                        Toast.makeText(getApplicationContext(), "episode marqué", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    public Button setBouton() {
        Button boutonVue = (Button) findViewById(R.id.episodeDetail_button_watched);

        if(this.episode.estVue()) {
            boutonVue.setText(R.string.episodeDetailActivity_unmarkWatched);
            boutonVue.setCompoundDrawablesWithIntrinsicBounds(R.drawable.brokeneye, 0, 0, 0);
        } else {
            boutonVue.setText(R.string.episodeDetailActivity_markWatched);
            boutonVue.setCompoundDrawablesWithIntrinsicBounds(R.drawable.eye, 0, 0, 0);
        }
        return boutonVue;
    }
}
