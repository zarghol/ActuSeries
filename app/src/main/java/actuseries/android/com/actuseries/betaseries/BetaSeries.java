package actuseries.android.com.actuseries.betaseries;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import actuseries.android.com.actuseries.metier.Member;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 11/12/2014.
 */
public class BetaSeries {
    private String apiKey;
    // TODO voir comment pas dupliquer entre membre et ici
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

    public String getToken() {
        return token;
    }

    public String obtainToken(String username, String password) {
        String passMD5 = this.getMD5(password);
        return this.obtainTokenWithMD5(username, passMD5);
    }

    public String obtainTokenWithMD5(String username, String passwordMD5) {
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
                Log.e("ActuSeries", "erreur de récupération de token ", e);
            }
        });

        if (this.token == null) {
            this.token = "";
        }

        return this.token;
    }

    public Member createAccount(String username, String password, String mail) {
        Request request = this.buildRequest(RequestCategory.MEMBERS, RequestMethod.SIGNUP);
        request.addOption("login", username);
        request.addOption("password", password);
        request.addOption("email", mail);

        return request.send(new CompletionHandlerData<Member>() {
            @Override
            public Member completionMethod(JSONObject json) throws JSONException {
                if (json.getJSONArray("errors").length() == 0 && !json.isNull("user")) {
                    // TODO apres création du compte, pas besoin de se logger -> récupération du token
                    Member m = new Member("kglg");

                    return m;
                }
                return null;
            }

            @Override
            public void handleError(Exception e) {
                Log.e("ActuSeries", "erreur lors de la creation de compte utilisateur", e);
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
                Log.e("Actuseries", "erreur lors de la deconnexion", e);
            }
        });
    }


    public List<Serie> getMemberInformations(final Member member) {
        Request request = this.buildRequest(RequestCategory.MEMBERS, RequestMethod.INFOS);

        final List<Serie> series = request.send(new CompletionHandlerData<List<Serie>>() {
            @Override
            public List<Serie> completionMethod(JSONObject json) throws Exception {
                List<Serie> series = new ArrayList<>();
                Log.d("ActuSeries", json.toString());

                JSONObject memberjson = json.getJSONObject("member");

                JSONArray array = memberjson.getJSONArray("shows");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject show = array.getJSONObject(i);
                    series.add(new Serie(show));
                }

               member.fillMember(memberjson);

                return series;
            }

            @Override
            public void handleError(Exception e) {
                Log.e("Actuseries", "erreur lors de la récupération du membre", e);
            }
        });

        return series;
    }

    public Serie fillSerie(JSONObject show) {
        Serie s = new Serie(show);
        /*
        Async.background {
            self.recupBanner(serie)
            self.recupEpisodes(serie)
        }
         */
        // TODO récupérer la liste des épisodes et la banniere de maniere asynchrone => permet d'avoir la liste de série dans un premier temps pour affichage simple
        return s;
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
                Log.e("ActuSeries", "erreur de recherche", e);
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


    private String getMD5(String chaine) {
        byte[] mdpCrypt = DigestUtils.md5(chaine);

        String m = "";
        for (int i = 0; i < mdpCrypt.length; i++) {
            m += String.format("%02x", mdpCrypt[i]);
        }
        return m;
    }
}
