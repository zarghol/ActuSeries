package actuseries.android.com.actuseries.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.event.EventBus;
import actuseries.android.com.actuseries.event.GetSeriesResultEvent;
import actuseries.android.com.actuseries.event.LoginResultEvent;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by charly on 14/01/2015.
 */
public class GetSeriesTask extends AsyncTask<Void, Void, List<Serie>> {
    @Override
    protected List<Serie> doInBackground(Void... params) {
        // Debug the task thread name
        Log.d("actuseries", Thread.currentThread().getName() + " récupérations des infos");
        return AccesBetaseries.recupereSeriesMembre();
    }

    @Override
    protected void onPostExecute(List<Serie> series) {
        //on poste un évènement dans le bus d'évènement qui indique la récupération des séries réussie
        EventBus.getInstance().post(new GetSeriesResultEvent(series));
    }
}
