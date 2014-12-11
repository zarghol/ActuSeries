package actuseries.android.com.actuseries.betaseries;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Clement on 11/12/2014.
 */
// FIXME utiliser un seul objet de ce type si possible
public class BetaSeries {
    private String apiKey;
    // TODO voir si pas mieux dans AccesBetaseries, avec membre ???
    private String token;

    public BetaSeries() {
        this("", "");
    }

    public BetaSeries(String apiKey, String token) {
        this.apiKey = apiKey;
        this.token = token;
    }

    public BetaSeries(String apiKey) {
        this(apiKey, "");
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getToken() {
        return token;
    }

    public void obtainToken(String username, String passwordMD5) {
        Request request = this.buildRequest(RequestCategory.MEMBERS, RequestMethod.AUTH);

        request.addOption("login", username);
        request.addOption("password", passwordMD5);

        this.token = request.send(new CompletionHandlerData<String>() {
            @Override
            public String completionMethod(JSONObject json) throws Exception {
                return json.getJSONObject("member").getString("token");
            }

            @Override
            public void handleError(Exception e) {
                Log.e("ActuSeries", "erreur de récupération de token");
            }
        });
    }

    public boolean createAccount(String username, String password, String mail) {
        Request request = this.buildRequest(RequestCategory.MEMBERS, RequestMethod.SIGNUP);
        request.addOption("login", username);
        request.addOption("password", password);
        request.addOption("email", mail);

        return request.send(new CompletionHandlerData<Boolean>() {
            @Override
            public Boolean completionMethod(JSONObject json) throws JSONException {
                return (json.getJSONArray("errors").length() == 0 && !json.isNull("user"));
                // TODO a voir : apres création du compte, besoin de se logger, ou déjà fait justement ???? si oui, récupération du token
            }

            @Override
            public void handleError(Exception e) {
                Log.e("Actuseries", "erreur lors de la creation de compte utilisateur");
            }
        });
    }

    public void destroyToken() {
        Request request = this.buildRequest(RequestCategory.MEMBERS, RequestMethod.DESTROY);

        request.send(new CompletionHandlerData<Void>() {
            @Override
            public Void completionMethod(JSONObject json) throws Exception {

                return null;
            }

            @Override
            public void handleError(Exception e) {

            }
        });
    }




// AVANCE

    public void searchShow(String name) {
        Request request = this.buildRequest(RequestCategory.SHOWS, RequestMethod.SEARCH);

        request.addOption("title", name);

        List<String> noms = request.send(new CompletionHandlerData<List<String>>() {
            @Override
            public List<String> completionMethod(JSONObject json) throws Exception {
                List<String> result = new ArrayList<String>();
                JSONObject shows = json.getJSONObject("shows");
                for (int i = 0; i < (shows.length() > 5 ? 5 : shows.length()); i++) {
                    result.add(shows.getJSONObject("title").getString("" + i));
                }

                return result;
            }

            @Override
            public void handleError(Exception e) {
                Log.e("ActuSeries", "erreur de recherche");
            }
        });
    }

    private Request buildRequest(RequestCategory category, RequestMethod method) {
        Request request = new Request();
        request.setApiKey(this.apiKey);

        if (!this.token.equals("")) {
            request.setToken(this.token);
        }

        request.setCategory(category);
        request.setMethod(method);

        return request;
    }
}
