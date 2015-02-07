package actuseries.android.com.actuseries.tasks;

import android.os.AsyncTask;
import android.util.Log;

import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.locator.BetaSeriesCallerLocator;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 23/01/2015.
 */
public class AddSerieTask extends AsyncTask<Serie, Void, Void> {

    @Override
    protected Void doInBackground(Serie... serie) {
        Log.d("actuseries", Thread.currentThread().getName() + " Ajout de la série!");
        boolean taskCancelled = TaskManager.cancelTask(GetSeriesTask.class);
        BetaSeriesCallerLocator.getService().addSerie(serie[0]);
        if(taskCancelled) {
            TaskManager.launchTask(GetSeriesTask.class, null);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void rien) {
        //on poste un évènement dans le bus d'évènement qui indique la récupération des séries réussie
        // TODO poster un evenement indiquant la fin de récuperation totale
        Log.d("actuseries", "nia");
    }
}
