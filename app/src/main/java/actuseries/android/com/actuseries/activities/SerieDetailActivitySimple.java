package actuseries.android.com.actuseries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import javax.xml.datatype.Duration;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.activities.fragment.SeriesDisplay;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.metier.Serie;
import actuseries.android.com.actuseries.tasks.AddSerieTask;
import actuseries.android.com.actuseries.tasks.GetEpisodesTask;

/**
 * Created by Mickaël on 08/01/2015.
 */
public class SerieDetailActivitySimple extends MainMenuActionBarActivity implements View.OnClickListener{

    private EpisodesLogAdapter adapter;
    private Serie serie;
    private int numSerie;
    private ExpandableTextView description;
    private Button buttonAddSerie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serie_detail_activity_simple);

        this.numSerie = this.getIntent().getExtras().getInt("numSerie", 0);
        this.buttonAddSerie = (Button) findViewById(R.id.button_ajout_serie);

        this.serie = AccesBetaseries.getListRecherche().get(this.numSerie);
        this.buttonAddSerie.setOnClickListener(this);
        if(AccesBetaseries.getSeries(SeriesDisplay.ALL).contains(this.serie))
             this.buttonAddSerie.setSelected(false);


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
        this.description = (ExpandableTextView) findViewById(R.id.serieDetail_textView_summary);
        this.description.setText(this.serie.getDescription());



    }

    @Override
    public void onClick(View v) {
        if(v.equals(buttonAddSerie)) {
            Serie[] s = {this.serie};
            TaskManager.launchTask(AddSerieTask.class,s);
            Toast.makeText(this, "La série à été ajouté avec SUCCES!", Toast.LENGTH_SHORT);
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
