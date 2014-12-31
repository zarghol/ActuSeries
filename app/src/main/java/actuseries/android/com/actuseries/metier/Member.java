package actuseries.android.com.actuseries.metier;

import org.json.JSONException;
import org.json.JSONObject;

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

    public Member(String token) {
        this.token = token;
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
}
