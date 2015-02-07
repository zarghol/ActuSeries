package actuseries.android.com.actuseries.tasks;

import android.os.AsyncTask;

/**
 * Created by Clement on 30/01/2015.
 */
public class SeenTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Void rien) {
        //on poste un évènement dans le bus d'évènement qui indique la connexion réussie
        //TaskManager.post(new LoginResultEvent(member != null));
    }
}
