package actuseries.android.com.actuseries.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.activities.fragment.SeriesDisplay;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.metier.Serie;
import actuseries.android.com.actuseries.tasks.AddSerieTask;

/**
 * Created by Mickaël on 08/01/2015.
 */
public class SerieDetailActivitySimple extends MainMenuActionBarActivity implements View.OnClickListener{

    private Serie serie;
    private Button buttonAddSerie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serie_detail_activity_simple);

        int numSerie = this.getIntent().getExtras().getInt("numSerie", 0);
        this.buttonAddSerie = (Button) findViewById(R.id.button_ajout_serie);

        this.serie = AccesBetaseries.getListRecherche().get(numSerie);
        this.buttonAddSerie.setOnClickListener(this);
        if(AccesBetaseries.getSeries(SeriesDisplay.ALL).contains(this.serie)) {
            this.buttonAddSerie.setEnabled(false);
            this.buttonAddSerie.setText("Cette série est déjà ajoutée.");
        }


        TextView titre = (TextView) findViewById(R.id.serieDetail_textView_title);
        titre.setText(this.serie.getNomSerie());
//        BitmapDrawable bd = new BitmapDrawable(this.serie.getBanner());
//        titre.setBackground(bd); //<--- ça fait moche
        TextView statut = (TextView) findViewById(R.id.statutSimple);
        TextView genre = (TextView) findViewById(R.id.genreSimple);
        TextView duree = (TextView) findViewById(R.id.lenghtEpisodeSimple);

        statut.setText(statut.getText() + " " + this.serie.getStatut().getStringStatus());
        duree.setText(duree.getText()+" "+this.serie.getDureeEpisode());
        String genres = " ";
        for(String s : this.serie.getGenres())
            genres += genres.equals(" ") ? s : " / " + s;
        genre.setText(genre.getText()+genres);
        ExpandableTextView description = (ExpandableTextView) findViewById(R.id.serieDetail_textView_summary);
        description.setText(this.serie.getDescription());
    }

    @Override
    public void onClick(View v) {
        if(v.equals(buttonAddSerie)) {
            Serie[] s = {this.serie};
            TaskManager.launchTask(AddSerieTask.class, s);
            Toast.makeText(this, R.string.serieDetailActivity_toast_serie_added, Toast.LENGTH_SHORT).show();
        }
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
