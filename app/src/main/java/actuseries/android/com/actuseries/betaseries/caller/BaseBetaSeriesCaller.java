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

    public BaseBetaSeriesCaller() {
        this.member = Member.getFromPersistance();
        String token = this.member != null ? this.member.getToken() : "";
        this.screenSize = new Point();
        this.betaSeriesAPI = new BetaSeriesAPI(cleApi);
        this.searchResults = new ArrayList<Serie>();
    }

    public Member memberLogin(final String identifiant, final String password) {
        this.setMember(this.betaSeriesAPI.obtainMember(identifiant, password));
        return this.member;
    }

    public List<Serie> getMemberSeries() {
        this.betaSeriesAPI.getMemberInformations(this.member);
        Log.d("ActuSeries", "series : " + this.member.getSeries().size());
        return this.member.getSeries();
    }

    public void getSerieWithBanner(Serie s) {
        this.betaSeriesAPI.recupInfoEpisode(s, this.screenSize);
        //inst.betaSeries.recupBanner(s);
    }

    public List<Episode> getEpisodes(Serie s) {
        this.betaSeriesAPI.getEpisodes(s);
        return s.getEpisodes();
    }

    public void memberLogout() {
        this.betaSeriesAPI.destroyToken();
        this.setMember(null);
    }

    public Member accountCreation(String identifiant, String password, String email) {
        this.setMember(this.betaSeriesAPI.createAccount(identifiant, password, email));
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

    private void setMember(Member member) {
        this.member = member;
        this.betaSeriesAPI = new BetaSeriesAPI(cleApi, member);
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
