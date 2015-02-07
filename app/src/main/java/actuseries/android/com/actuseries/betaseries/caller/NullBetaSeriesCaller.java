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

    public Member memberLogin(final String identifiant, final String password) {
        return null;
    }

    public List<Serie> getMemberSeries() {
        return null;
    }

    public void getSerieWithBanner(Serie s) {
    }

    public List<Episode> getEpisodes(Serie s) {
        return null;
    }

    public void memberLogout() {
    }

    public Member accountCreation(String identifiant, String password, String email) {
        return null;
    }

    public Point getScreenSize() {
        return null;
    }

    public void setScreenSize(Point screenSize) {
    }

    public List<Serie> getSeries(SeriesDisplay seriesDisplay) {
        return null;
    }

    public boolean isLoggedIn() {
        return false;
    }

    public void searchSerie(String nomSerie) {
    }

    private void setMembreConnecte(Member membreConnecte) {
    }

    public void markAsWatched(Episode episode) {
    }

    public void setRating(Episode episode) {
    }

    public void addSerie(Serie serie) {
    }

    public List<Serie> getSearchResults() {
        return null;
    }

    public void archiveSerie(Serie serie) {
    }
}
