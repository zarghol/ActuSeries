package actuseries.android.com.actuseries.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.event.EventBus;
import actuseries.android.com.actuseries.event.GetSerieResultEvent;
import actuseries.android.com.actuseries.event.GetSeriesResultEvent;
import actuseries.android.com.actuseries.event.LoginResultEvent;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by charly on 14/01/2015.
 */
public class GetSeriesTask extends AsyncTask<Void, Serie, Void> {
    @Override
    protected Void doInBackground(Void... params) {
        // Debug the task thread name
        Log.d("actuseries", Thread.currentThread().getName() + " récupérations des infos");
        // Comment faire un
        List<Serie> series = AccesBetaseries.recupereSeriesMembre();

        for (Serie s: series) {
            AccesBetaseries.recupereSerieAvecBanniere(s);
            this.publishProgress(s);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Serie... values) {
        super.onProgressUpdate(values);
        EventBus.getInstance().post(new GetSerieResultEvent(values[0]));
    }

    @Override
    protected void onPostExecute(Void rien) {
        //on poste un évènement dans le bus d'évènement qui indique la récupération des séries réussie
        // TODO poster un evenement indiquant la fin de récuperation totale
        //EventBus.getInstance().post(new GetSeriesResultEvent(series));
    }
}
