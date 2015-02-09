package actuseries.android.com.actuseries.betaseries.caller;

import android.graphics.Point;

import java.util.List;

import actuseries.android.com.actuseries.activities.fragment.SeriesDisplay;
import actuseries.android.com.actuseries.metier.Episode;
import actuseries.android.com.actuseries.metier.Member;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Thomas on 07/02/2015.
 */
public class NullBetaSeriesCaller implements BetaSeriesCaller {

    @Override
    public Member memberLogin(final String login, final String password) {
        return null;
    }

    @Override
    public List<Serie> getMemberSeries() {
        return null;
    }

    @Override
    public void getSerieWithBanner(Serie s) {
    }

    @Override
    public List<Episode> getEpisodes(Serie s) {
        return null;
    }

    @Override
    public void memberLogout() {
    }

    @Override
    public Member accountCreation(String login, String password, String email) {
        return null;
    }

    @Override
    public Point getScreenSize() {
        return null;
    }

    @Override
    public void setScreenSize(Point screenSize) {
    }

    @Override
    public Member getMemberSummary() {
        return null;
    }

    @Override
    public List<Serie> getSeries(SeriesDisplay seriesDisplay) {
        return null;
    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public void searchSerie(String nomSerie) {
    }

    @Override
    public void markAsWatched(Episode episode) {
    }

    @Override
    public void setRating(Episode episode) {
    }

    @Override
    public void addSerie(Serie serie) {
    }

    @Override
    public List<Serie> getSearchResults() {
        return null;
    }

    @Override
    public void archiveSerie(Serie serie) {
    }
}
