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
    private Member member;
    private BetaSeriesAPI betaSeriesAPI;
    private Point screenSize;
    private List<Serie> searchResults;

    public BaseBetaSeriesCaller(String token) {
        this();
        this.betaSeriesAPI = new BetaSeriesAPI(token);
        this.member = new Member(token);
    }

    public BaseBetaSeriesCaller() {
        //TODO: initialiser screenSize aux dimensions de l'écran
        this.screenSize = new Point();
        this.searchResults = new ArrayList<>();
        this.betaSeriesAPI = new BetaSeriesAPI();
    }

    private void setMember(Member member) {
        this.member = member;
        this.betaSeriesAPI = new BetaSeriesAPI(member.getToken());
    }

    public Member accountCreation(String login, String password, String email) {
        this.setMember(this.betaSeriesAPI.createAccount(login, password, email));
        return this.member;
    }

    public Member memberLogin(final String login, final String password) {
        this.setMember(this.betaSeriesAPI.login(login, password));
        return this.member;
    }

    public Member getMemberSummary() {
        // on ne recrée pas un membre ici, on le complète (on garde le token dedans etc...)
        this.member.retrieveInformation(this.betaSeriesAPI.getMemberInformation(true));
        return this.member;
    }

    public List<Serie> getSeries(SeriesDisplay seriesDisplay) {
        List<Serie> series = this.member.getSeries();
        return seriesDisplay == null ? SeriesDisplay.ALL.sort(series) : seriesDisplay.sort(series);
    }

    public List<Serie> retrieveSeries() {
        this.member.addSeries(this.betaSeriesAPI.getListSeries());
        return this.getSeries(SeriesDisplay.ALL);
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
        this.betaSeriesAPI = new BetaSeriesAPI();
    }

    public Point getScreenSize() {
        return this.screenSize;
    }

    public void setScreenSize(Point screenSize) {
        this.screenSize = screenSize;
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
