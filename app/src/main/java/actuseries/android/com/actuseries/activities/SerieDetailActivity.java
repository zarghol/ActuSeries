package actuseries.android.com.actuseries.activities;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.activities.fragment.SeriesDisplay;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.metier.Episode;
import actuseries.android.com.actuseries.metier.Serie;
import actuseries.android.com.actuseries.tasks.GetEpisodesTask;

/**
 * Created by Clement on 08/01/2015.
 */
public class SerieDetailActivity extends MainMenuActionBarActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    private EpisodesLogAdapter adapter;
    private Serie serie;
    private int numSerie;
    private SeriesDisplay seriesDisplay;
    private TextView description;

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

        this.adapter = new EpisodesLogAdapter(this.serie.getEpisodes(), getApplicationContext());
        this.lv.setAdapter(this.adapter);

        TextView titre = (TextView) findViewById(R.id.serieDetail_textView_title);
        titre.setText(this.serie.getNomSerie());
//        BitmapDrawable bd = new BitmapDrawable(this.serie.getBanner());
//        titre.setBackground(bd); //<--- ça fait moche
        TextView statut = (TextView) findViewById(R.id.serieDetail_textView_status);
        statut.setText(getResources().getText(R.string.serieDetailActivity_status) + " " + this.serie.getStatut().getStringStatus());
        this.description = (TextView) findViewById(R.id.serieDetail_textView_summary);
        this.description.setText(this.serie.getDescription());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent episodeDetailActivityIntent = new Intent(this, EpisodeDetailActivity.class);
        episodeDetailActivityIntent.putExtra("indexSerie", this.numSerie);
        episodeDetailActivityIntent.putExtra("indexEpisode", position);
        startActivityForResult(episodeDetailActivityIntent, 1);
    }

/*    //on reçoit le message associé à l'évènement de récupération des épisodes <=== plus besoin, on récupère plus tot
    @Subscribe
    public void onGetEpisodesTaskResult(GetEpisodesResultEvent event) {
        Log.d("actuseries", "nb episodes: " + episodes.size());
        this.serie.getEpisodes() = event.getEpisodes();
        Log.d("actuseries", "nb episodes: " + episodes.size());
        adapter.notifyDataSetChanged();
        Log.d("actuseries", "nb episodes: " + episodes.size());
    }*/
}
