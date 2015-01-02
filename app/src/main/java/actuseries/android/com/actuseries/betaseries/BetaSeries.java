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

    public BetaSeries(String apiKey, String token) {
        this.apiKey = apiKey;
        this.token = token;
    }

    public BetaSeries(String apiKey) {
        this(apiKey, "");
    }

    public Member obtainMember(String username, String password) {
        String passMD5 = this.getMD5(password);
        return this.obtainMemberWithMD5(username, passMD5);
    }

    public Member obtainMemberWithMD5(String username, String passwordMD5) {
        Request request = this.buildRequest(RequestCategory.MEMBERS, RequestMethod.AUTH);
        request.addOption("login", username);
        request.addOption("password", passwordMD5);
        request.setHttpMethod(HttpMethod.POST);

        try {
            JSONObject json = request.send();
            Member m = new Member(json.getString("token"), json.getJSONObject("user").getString("login"));
            this.token = m.getToken();
            return m;
        } catch (Exception e) {
            Log.e("ActuSeries", "erreur de récupération de token ", e);
            return null;
        }
    }

    public Member createAccount(String username, String password, String mail) {
        Request request = this.buildRequest(RequestCategory.MEMBERS, RequestMethod.SIGNUP);
        request.addOption("login", username);
        request.addOption("password", password);
        request.addOption("email", mail);


        try {
            JSONObject json = request.send();
            JSONObject jsonuser = json.getJSONObject("user");
            Member m = new Member("kglg", "oug");

            return m;
        } catch (Exception e) {
            Log.e("ActuSeries", "erreur lors de la creation de compte utilisateur", e);
        }
        return null;
    }

    public void destroyToken() {
        Request request = this.buildRequest(RequestCategory.MEMBERS, RequestMethod.DESTROY);

        try {
            request.send();
        } catch (Exception e) {
            Log.e("Actuseries", "erreur lors de la deconnexion", e);
        }
    }


    public void getMemberInformations(Member member) {
        Request request = this.buildRequest(RequestCategory.MEMBERS, RequestMethod.INFOS);

        try {
            JSONObject memberjson = request.send().getJSONObject("member");
            member.fillMember(memberjson);
            // TODO utiliser fillSerie ?
        } catch (Exception e) {
            Log.e("Actuseries", "erreur lors de la récupération du membre", e);
        }
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

        try {
            JSONObject json = request.send();
            List<String> result = new ArrayList<String>();

            JSONObject shows = json.getJSONObject("shows");
            for (int i = 0; i < (shows.length() > 5 ? 5 : shows.length()); i++) {
                result.add(shows.getJSONObject("title").getString("" + i));
            }
        } catch (Exception e) {
            Log.e("ActuSeries", "erreur de recherche", e);
        }
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
