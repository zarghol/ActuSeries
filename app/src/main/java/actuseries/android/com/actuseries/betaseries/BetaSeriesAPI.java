package actuseries.android.com.actuseries.betaseries;

import android.graphics.Point;
import android.util.Log;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import actuseries.android.com.actuseries.metier.Episode;
import actuseries.android.com.actuseries.metier.Member;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 11/12/2014.
 */
public class BetaSeriesAPI {
    private final static String apiKey = "e88a334499a9";
    private final String token;

    public BetaSeriesAPI(String token) {
        this.token = token;
    }

    public BetaSeriesAPI() {
        this(null);
    }

    public Member login(String username, String password) {
        String passMD5 = this.getMD5(password);
        return this.loginMD5(username, passMD5);
    }

    public Member loginMD5(String username, String passwordMD5) {
        Request request = this.buildRequest(RequestCategory.MEMBERS, RequestMethod.AUTH);
        request.addOption("login", username);
        request.addOption("password", passwordMD5);
        request.setHttpMethod(HttpMethod.POST);

        try {
            String token = request.send().getString("token");
            return new Member(token);
        } catch(Exception e) {
            Log.e("ActuSeries", "erreur de récupération de token ", e);
            return null;
        }
    }

    public Member createAccount(String username, String password, String mail) {
        Request request = this.buildRequest(RequestCategory.MEMBERS, RequestMethod.SIGNUP);
        request.setHttpMethod(HttpMethod.POST);
        request.addOption("login", username);
        request.addOption("password", this.getMD5(password));
        request.addOption("email", mail);

        Log.d("actuseries", "essai de creation avec \nlogin : " + username + "\npassword : " + password + "\nmail : " + mail);


        try {
            String token = request.send().getString("token");
            return new Member(token);
        } catch(Exception e) {
            Log.e("ActuSeries", "erreur lors de la creation de compte utilisateur", e);
        }
        return null;
    }

    public void destroyToken() {
        Request request = this.buildRequest(RequestCategory.MEMBERS, RequestMethod.DESTROY);
        request.setHttpMethod(HttpMethod.POST);

        try {
            request.send();
        } catch(Exception e) {
            Log.e("Actuseries", "erreur lors de la deconnexion", e);
        }
    }

    public JSONObject getMemberInformation(boolean summary) {
        Request request = this.buildRequest(RequestCategory.MEMBERS, RequestMethod.INFOS);
        if(summary) {
            request.addOption("summary", "true");
        }

        try {
            JSONObject memberjson = request.send().getJSONObject("member");
            return memberjson;
        } catch(Exception e) {
            Log.e("Actuseries", "erreur lors de la récupération du membre", e);
        }
        return null;
    }

    public JSONArray getListSeries() {
        Request request = this.buildRequest(RequestCategory.MEMBERS, RequestMethod.INFOS);
        Log.d("Actuseries", "token : " + this.token);

        try {
            return request.send().getJSONObject("member").getJSONArray("shows");
        } catch(Exception e) {
            Log.e("Actuseries", "erreur lors de la récupération du membre", e);
        }
        return null;
    }

    public void getEpisodesInformation(Serie serie, Point size) {
        this.getEpisodes(serie);
        this.getBanner(serie, size);
    }

    public void getBanner(Serie serie, Point realSize) {
        Request request = this.buildRequest(RequestCategory.PICTURES, RequestMethod.SHOWS);
        request.addOption("id", "" + serie.getId());

        // TODO récupérer les dimensions depuis les ressources
        int heightBanniere = 100;
        int widthBanniere = realSize.y == 0 ? 390 : (realSize.x * heightBanniere) / realSize.y;

        request.addOption("height", "" + heightBanniere);
        request.addOption("width", "" + widthBanniere);
        request.addOption("picked", "banner");

        try {
            serie.setBanner(request.getImage());
        } catch(Exception e) {
            Log.e("Actuseries", "erreur lors de la récupération de la bannière", e);
        }
    }

    public void getEpisodes(Serie serie) {
        if(serie.getEpisodes().size() != 0) {
            serie.clearEpisodes();
        }
        Request request = this.buildRequest(RequestCategory.SHOWS, RequestMethod.EPISODES);

        request.addOption("id", "" + serie.getId());

        try {
            JSONArray episodes = request.send().getJSONArray("episodes");

            for(int i = 0; i < episodes.length(); i++) {
                JSONObject ep = episodes.getJSONObject(i);
                Episode episode = new Episode(ep);
                serie.addEpisode(episode);
            }

            serie.trieEpisode();
        } catch(Exception e) {
            Log.e("Actuseries", "erreur lors de la récupération des épisodes", e);
        }
    }

    public List<Serie> searchShow(String name) {
        Request request = this.buildRequest(RequestCategory.SHOWS, RequestMethod.SEARCH);

        request.addOption("title", name);
        List<Serie> result = new ArrayList<>();


        try {
            JSONObject json = request.send();
            JSONArray shows = json.getJSONArray("shows");

            for(int i = 0; i < shows.length(); i++) {
                result.add(new Serie(shows.getJSONObject(i)));
            }
        } catch(Exception e) {
            Log.e("ActuSeries", "erreur de recherche", e);
        }
        return result;
    }

    public void writeSeen(Episode episode) {
        Request request = this.buildRequest(RequestCategory.EPISODES, RequestMethod.WATCHED);
        request.setHttpMethod(episode.estVue() ? HttpMethod.POST : HttpMethod.DELETE);
        request.addOption("id", episode.getId() + "");

        try {
            request.send();
        } catch(Exception e) {
            Log.e("Actuseries", "erreur lors du marquage comme vu", e);
        }
    }

    public void writeMark(Episode episode) {
        Request request = this.buildRequest(RequestCategory.EPISODES, RequestMethod.NOTE);
        request.setHttpMethod(HttpMethod.POST);
        request.addOption("id", episode.getId() + "");
        request.addOption("note", episode.getNoteEpisode() + "");

        try {
            request.send();
        } catch(Exception e) {
            Log.e("Actuseries", "erreur lors du notage", e);
        }
    }

    public boolean addToAccount(Serie serie) {
        Request request = this.buildRequest(RequestCategory.SHOWS, RequestMethod.SHOW);
        request.setHttpMethod(HttpMethod.POST);
        request.addOption("id", serie.getId() + "");

        try {
            request.send();
            return true;
        } catch(Exception e) {
            Log.e("Actuseries", "erreur lors de l'ajout au compte", e);
        }
        return false;
    }

    public void archive(Serie serie) {
        Request request = this.buildRequest(RequestCategory.SHOWS, RequestMethod.ARCHIVE);
        request.setHttpMethod(serie.isActive() ? HttpMethod.DELETE : HttpMethod.POST);
        request.addOption("id", serie.getId() + "");

        try {
            request.send();
        } catch(Exception e) {
            Log.e("Actuseries", "erreur lors du marquage comme vu", e);
        }
    }

    private Request buildRequest(RequestCategory category, RequestMethod method) {
        Request request = new Request();
        request.setApiKey(BetaSeriesAPI.apiKey);
        request.setToken(this.token);

        request.setCategory(category);
        request.setMethod(method);

        return request;
    }

    private String getMD5(String chaine) {
        byte[] mdpCrypt = DigestUtils.md5(chaine);

        String m = "";
        for(int i = 0; i < mdpCrypt.length; i++) {
            m += String.format("%02x", mdpCrypt[i]);
        }

        return m;
    }
}
