package actuseries.android.com.actuseries.metier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clement on 19/12/2014.
 */
public class Member {
    private String login;
    private String token;
    private String avatarUrl;

    // Stats
    private int nbBadges;
    private int nbEpisodes;
    private double progress;
    private int nbSeasons;
    private int nbShows;

    // TODO à revoir si intégration ici de la liste de séries
    private List<Serie> series;


    public Member(String token, String login) {
        this.token = token;
        this.login = login;
        this.series = new ArrayList<>();
    }

    public void fillMember(JSONObject member) {
        try {
            this.login = member.getString("login");
            this.avatarUrl = member.getString("avatar");

            JSONObject stats = member.getJSONObject("stats");
            this.nbBadges = stats.getInt("badges");
            this.nbEpisodes = stats.getInt("episodes");
            this.progress = stats.getDouble("progress");
            this.nbSeasons = stats.getInt("seasons");
            this.nbShows = stats.getInt("shows");

            JSONArray array = member.getJSONArray("shows");

            for (int i = 0; i < array.length(); i++) {
                JSONObject show = array.getJSONObject(i);
                this.series.add(new Serie(show));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Member getFromPersistance() {
        // TODO unimplemented method
        return null;
    }

    public String getToken() {
        return this.token;
    }

    public String getLogin() { return this.login; }

    public List<Serie> getSeries() {
        return this.series;
    }
}
