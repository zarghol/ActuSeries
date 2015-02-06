package actuseries.android.com.actuseries.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.event.GetSerieResultEvent;
import actuseries.android.com.actuseries.event.GetSeriesResultEvent;
import actuseries.android.com.actuseries.event.LoginResultEvent;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.metier.Member;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 23/01/2015.
 */
public class SearchTask extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String[] params) {
        Log.d("actuseries", Thread.currentThread().getName() + " récupération de la recherche");
        AccesBetaseries.rechercheSerie(params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void rien) {
        //on poste un évènement dans le bus d'évènement qui indique la récupération des séries réussie
        TaskManager.post(new GetSeriesResultEvent());
    }
}
