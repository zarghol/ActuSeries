package actuseries.android.com.actuseries.betaseries;

import android.graphics.Point;

import java.util.List;

import actuseries.android.com.actuseries.activities.fragment.SeriesDisplay;
import actuseries.android.com.actuseries.metier.Episode;
import actuseries.android.com.actuseries.metier.Member;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Thomas on 07/02/2015.
 */
public interface BetaSeriesCaller {

    public Member memberLogin(final String identifiant, final String password);

    public List<Serie> getMemberSeries();

    public void getSerieWithBanner(Serie s);

    public List<Episode> getEpisodes(Serie s);

    public void memberLogout();

    public Member accountCreation(String identifiant, String password, String email);

    public Point getScreenSize();

    public void setScreenSize(Point screenSize);

    public List<Serie> getSeries(SeriesDisplay seriesDisplay);

    public boolean isLoggedIn();

    public void markAsWatched(Episode episode);

    public void setRating(Episode episode);

    public void searchSerie(String nomSerie);

    public List<Serie> getSearchResults();

    public void addSerie(Serie serie);

    public void archiveSerie(Serie serie);
}
