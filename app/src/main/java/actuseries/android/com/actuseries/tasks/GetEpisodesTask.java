package actuseries.android.com.actuseries.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import actuseries.android.com.actuseries.activities.fragment.SeriesDisplay;
import actuseries.android.com.actuseries.event.GetEpisodesResultEvent;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.locator.BetaSeriesCallerLocator;
import actuseries.android.com.actuseries.metier.Episode;

/**
 * Created by charly on 14/01/2015.
 */
public class GetEpisodesTask extends AsyncTask<Integer, Void, List<Episode>> {
    @Override
    protected List<Episode> doInBackground(Integer... params) {
        int numSerie = params[0];
        SeriesDisplay seriesDisplay = SeriesDisplay.fromPosition(params[1]);
        // Debug the task thread name
        Log.d("actuseries", Thread.currentThread().getName() + " récupérations des épisodes");
        return BetaSeriesCallerLocator.getService().getEpisodes(BetaSeriesCallerLocator.getService().getSeries(seriesDisplay).get(numSerie));
    }

    @Override
    protected void onPostExecute(List<Episode> episodes) {
        //on poste un évènement dans le bus d'évènement qui indique la récupération des épisodes réussie
        TaskManager.post(new GetEpisodesResultEvent(episodes));
    }
}
