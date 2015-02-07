package actuseries.android.com.actuseries.tasks;

import android.os.AsyncTask;
import android.util.Log;

import actuseries.android.com.actuseries.betaseries.BaseBetaSeriesCaller;
import actuseries.android.com.actuseries.event.GetSeriesResultEvent;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.locator.BetaSeriesCallerLocator;

/**
 * Created by Clement on 23/01/2015.
 */
public class SearchTask extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String[] params) {
        Log.d("actuseries", Thread.currentThread().getName() + " récupération de la recherche");
        BetaSeriesCallerLocator.getService().searchSerie(params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void rien) {
        //on poste un évènement dans le bus d'évènement qui indique la récupération des séries réussie
        TaskManager.post(new GetSeriesResultEvent());
    }
}
