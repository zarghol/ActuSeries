package actuseries.android.com.actuseries.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.activities.fragment.TypeSeriesDisplayed;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.event.EventBus;
import actuseries.android.com.actuseries.event.GetEpisodesResultEvent;
import actuseries.android.com.actuseries.metier.Episode;
import actuseries.android.com.actuseries.metier.Serie;
import actuseries.android.com.actuseries.tasks.GetEpisodesTask;

/**
 * Created by Clement on 08/01/2015.
 */
public class DetailSerieActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, OnClickListener {

    private LogAdapterEpisodes adapter;
    private Serie serie;
    ListView lv;
    private int numSerie;
    private TypeSeriesDisplayed typeSeriesDisplayed;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_serie_activity);

        this.numSerie = this.getIntent().getExtras().getInt("numSerie", 0);
        this.typeSeriesDisplayed = TypeSeriesDisplayed.fromPosition(this.getIntent().getIntExtra("typePosition", 0));

        this.lv = (ListView) findViewById(R.id.listeEpisodes);
        this.lv.setEmptyView(findViewById(R.id.noEpisodesTextView));
        this.lv.setOnItemClickListener(this);

        this.serie = AccesBetaseries.getSeries(this.typeSeriesDisplayed).get(this.numSerie);

        if (this.serie.getEpisodes().size() == 0) {
            new GetEpisodesTask().execute(numSerie, this.typeSeriesDisplayed.getPosition());
        }

        this.adapter = new LogAdapterEpisodes(this.serie.getEpisodes(), getApplicationContext());
        this.lv.setAdapter(this.adapter);

        TextView titre = (TextView) findViewById(R.id.textView_titre);
        titre.setText(this.serie.getNomSerie());
/*        BitmapDrawable bd = new BitmapDrawable(this.serie.getBanner());
        titre.setBackground(bd);*/
        TextView statut = (TextView) findViewById(R.id.textView_statut);
        statut.setText("Statut : " + this.serie.getStatut().getStringStatus());
        this.description = (TextView) findViewById(R.id.textView_description);
        this.description.setText(this.serie.getDescription());
        this.description.setOnClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d("actuseries", "clique ! ");
        Episode e = this.serie.getEpisodes().get(position);

        Intent j = new Intent(this, DetailEpisodeActivity.class);
        j.putExtra("indexSerie", this.numSerie);
        j.putExtra("indexEpisode", position);
        startActivityForResult(j, 1);
    }

    //on reçoit le message associé à l'évènement de récupération des épisodes
    @Subscribe
    public void onGetEpisodesTaskResult(GetEpisodesResultEvent event) {
        /*Log.d("actuseries", "nb episodes: " + episodes.size());
        this.serie.getEpisodes() = event.getEpisodes();
        Log.d("actuseries", "nb episodes: " + episodes.size());
        adapter.notifyDataSetChanged();
        Log.d("actuseries", "nb episodes: " + episodes.size());*/
    }

    @Override
    public void onClick(View v) {
        Paint paint = new Paint();
        Rect bounds = new Rect();

        int text_height = 0;
        int text_width = 0;

        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(description.getWidth());

        String text = description.getText().toString();

        paint.getTextBounds(text, 0, text.length(), bounds);

        description.setTextSize(bounds.height());
    }
}
