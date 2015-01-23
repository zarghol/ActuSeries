package actuseries.android.com.actuseries.tasks;

import android.os.AsyncTask;

import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.event.LoginResultEvent;
import actuseries.android.com.actuseries.metier.Member;

/**
 * Created by charly on 14/01/2015.
 */
public class LoginTask extends AsyncTask<String, Void, Member> {

    @Override
    protected Member doInBackground(String... params) {
        String login = params[0];
        String password = params[1];
        return AccesBetaseries.connexionMembre(login, password);
    }

    @Override
    protected void onPostExecute(Member member) {
        //on poste un évènement dans le bus d'évènement qui indique la connexion réussie
        TaskManager.post(new LoginResultEvent(member != null));
    }
}
