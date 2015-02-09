package actuseries.android.com.actuseries.betaseries.caller;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import actuseries.android.com.actuseries.activities.fragment.SeriesDisplay;
import actuseries.android.com.actuseries.betaseries.BetaSeriesAPI;
import actuseries.android.com.actuseries.metier.Episode;
import actuseries.android.com.actuseries.metier.Member;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 11/12/2014.
 */
public class BaseBetaSeriesCaller implements BetaSeriesCaller {
    private final static String cleApi = "e88a334499a9";
    private Member member;
    private BetaSeriesAPI betaSeriesAPI;
    private Point screenSize;
    private List<Serie> searchResults;

    public BaseBetaSeriesCaller(String token) {
        //TODO: initialiser screenSize aux dimensions de l'écran
        this.screenSize = new Point();
        this.searchResults = new ArrayList<Serie>();
        if(token != null && !token.isEmpty()) {
            this.betaSeriesAPI = new BetaSeriesAPI(cleApi, token);
            this.member = new Member(token);
        } else {
            this.betaSeriesAPI = new BetaSeriesAPI(cleApi);
            this.member = null;
        }
    }

    public BaseBetaSeriesCaller() {
        this(null);
    }

    public Member memberLogin(final String login, final String password) {
        this.member = this.betaSeriesAPI.login(login, password);
        return this.member;
    }

    public Member getMemberSummary() {
        this.member = this.betaSeriesAPI.getMemberInformation(true);
        return this.member;
    }

    public List<Serie> getMemberSeries() {
        this.member = this.betaSeriesAPI.getMemberInformation(false);
        Log.d("ActuSeries", "series : " + this.member.getSeries().size());
        return this.member.getSeries();
    }

    public void getSerieWithBanner(Serie s) {
        this.betaSeriesAPI.getEpisodesInformation(s, this.screenSize);
    }

    public List<Episode> getEpisodes(Serie s) {
        this.betaSeriesAPI.getEpisodes(s);
        return s.getEpisodes();
    }

    public void memberLogout() {
        this.betaSeriesAPI.destroyToken();
        this.member = null;
    }

    public Member accountCreation(String login, String password, String email) {
        this.member = this.betaSeriesAPI.createAccount(login, password, email);
        return this.member;
    }

    public Point getScreenSize() {
        return this.screenSize;
    }

    public void setScreenSize(Point screenSize) {
        this.screenSize = screenSize;
    }

    public List<Serie> getSeries(SeriesDisplay seriesDisplay) {
        List<Serie> series = this.member.getSeries();
        return seriesDisplay == null ? series : seriesDisplay.sort(series);
    }

    public boolean isLoggedIn() {
        return this.member != null && !this.member.getToken().isEmpty();

    }

    public void searchSerie(String nomSerie) {
        this.searchResults.clear();
        this.searchResults.addAll(this.betaSeriesAPI.searchShow(nomSerie));
    }

    public void markAsWatched(Episode episode) {
        this.betaSeriesAPI.writeSeen(episode);
    }

    public void setRating(Episode episode) {
        this.betaSeriesAPI.writeMark(episode);
    }

    public void addSerie(Serie serie) {
        if(this.betaSeriesAPI.addToAccount(serie)) {
            this.member.addSerie(serie);
        }
    }

    public List<Serie> getSearchResults() {
        return this.searchResults;
    }

    public void archiveSerie(Serie serie) {
        this.betaSeriesAPI.archive(serie);
    }
}
