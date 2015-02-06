package actuseries.android.com.actuseries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.otto.Subscribe;

import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.activities.fragment.SeriesDisplay;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.event.GetSerieResultEvent;
import actuseries.android.com.actuseries.event.LogoutResultEvent;
import actuseries.android.com.actuseries.metier.Episode;
import actuseries.android.com.actuseries.metier.Serie;
import actuseries.android.com.actuseries.tasks.GetEpisodesTask;

/**
 * Created by Clement on 08/01/2015.
 */
public class SerieDetailActivity extends MainMenuActionBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView lv;
    private EpisodesLogAdapter adapter;
    private Serie serie;
    private int numSerie;
    private SeriesDisplay seriesDisplay;
    private Button boutonArchive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serie_detail_activity);

        this.numSerie = this.getIntent().getExtras().getInt("numSerie", 0);
        this.seriesDisplay = SeriesDisplay.fromPosition(this.getIntent().getIntExtra("typePosition", 0));

        this.lv = (ListView) findViewById(R.id.serieDetail_listView_episodes);
        this.lv.setEmptyView(findViewById(R.id.serieDetail_textView_noEpisodes));
        this.lv.setOnItemClickListener(this);

        this.serie = AccesBetaseries.getSeries(this.seriesDisplay).get(this.numSerie);

        if(this.serie.getEpisodes().size() == 0) {
            new GetEpisodesTask().execute(numSerie, this.seriesDisplay.getPosition());
        }

        this.adapter = new EpisodesLogAdapter(this.seriesDisplay.sortEpisodes(this.serie.getEpisodes()), getApplicationContext(), this);
        this.lv.setAdapter(this.adapter);

        TextView titre = (TextView) findViewById(R.id.serieDetail_textView_title);
        titre.setText(this.serie.getNomSerie());
//        BitmapDrawable bd = new BitmapDrawable(this.serie.getBanner());
//        titre.setBackground(bd); //<--- ça fait moche
        TextView statut = (TextView) findViewById(R.id.serieDetail_textView_status);
        statut.setText(getResources().getText(R.string.serieDetailActivity_status) + " " + this.serie.getStatut().getStringStatus());
        ExpandableTextView description = (ExpandableTextView) findViewById(R.id.serieDetail_textView_summary);
        description.setText(this.serie.getDescription());

        this.boutonArchive = (Button) findViewById(R.id.serieDetail_button_archive);
        this.boutonArchive.setOnClickListener(this);
        if (!this.serie.isActive()) {
            this.boutonArchive.setText(R.string.serieDetailActivity_button_unarchive);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent episodeDetailActivityIntent = new Intent(this, EpisodeDetailActivity.class);
        episodeDetailActivityIntent.putExtra("indexSerie", this.numSerie);
        episodeDetailActivityIntent.putExtra("indexEpisode", position);
        startActivityForResult(episodeDetailActivityIntent, 1);
    }

    public void onClick(View v) {
        if (v.equals(this.boutonArchive)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    serie.toggleArchive();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), (serie.isActive() ? R.string.serieDetailActivity_toast_serie_unarchived : R.string.serieDetailActivity_toast_serie_archived ), Toast.LENGTH_SHORT).show();

                            if (!serie.isActive()) {
                                boutonArchive.setText(R.string.serieDetailActivity_button_unarchive);
                            } else {
                                boutonArchive.setText(R.string.serieDetailActivity_button_archive);
                            }
                        }
                    });
                }
            }).start();
        } else {
            RelativeLayout layout = (RelativeLayout) v.getParent();
            final int position = this.lv.getPositionForView(layout);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    serie.getEpisodes().get(position).toggleVue();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.serieDetailActivity_toast_episode_marked, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        }
    }

    @Subscribe
    public void onDetailSerieReceived(GetSerieResultEvent serieEvent) {
        // si on est entrain de récupérer les infos de la série en arriere plan et que celle-ci arrivent avec la liste des épisodes, on met à jour
        if (serieEvent.getSerie().getId() == this.serie.getId()) {
            this.serie = serieEvent.getSerie();
            this.adapter = new EpisodesLogAdapter(this.seriesDisplay.sortEpisodes(this.serie.getEpisodes()), getApplicationContext(), this);
            this.lv.setAdapter(this.adapter);
        }
    }
}
