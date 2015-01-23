package actuseries.android.com.actuseries.betaseries;

import android.graphics.Point;
import android.util.Log;

import java.util.List;

import actuseries.android.com.actuseries.activities.fragment.SeriesDisplay;
import actuseries.android.com.actuseries.metier.Episode;
import actuseries.android.com.actuseries.metier.Member;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 11/12/2014.
 */
public class AccesBetaseries {
    private final static String cleApi = "e88a334499a9";
    private static AccesBetaseries instance;
    private Member membreConnecte;
    private BetaSeries betaSeries;
    private Point screenSize;

    private AccesBetaseries() {
        this.membreConnecte = Member.getFromPersistance();
        String token = this.membreConnecte != null ? this.membreConnecte.getToken() : "";
        this.screenSize = new Point();
        this.betaSeries = new BetaSeries(cleApi);
    }

    private static AccesBetaseries getInstance() {
        if(instance == null) {
            instance = new AccesBetaseries();
        }
        return instance;
    }

    public static Member connexionMembre(final String identifiant, final String password) {
        AccesBetaseries inst = AccesBetaseries.getInstance();
        inst.setMembreConnecte(inst.betaSeries.obtainMember(identifiant, password));
        return inst.membreConnecte;
    }

    public static List<Serie> recupereSeriesMembre() {
        AccesBetaseries inst = AccesBetaseries.getInstance();
        inst.betaSeries.getMemberInformations(inst.membreConnecte);
        Log.d("ActuSeries", "series : " + inst.membreConnecte.getSeries().size());
        return inst.membreConnecte.getSeries();
    }


    public static void recupereSerieAvecBanniere(Serie s) {
        AccesBetaseries inst = AccesBetaseries.getInstance();

        inst.betaSeries.recupInfoEpisode(s, inst.screenSize);
        //inst.betaSeries.recupBanner(s);
    }

    public static List<Episode> recupereEpisodes(Serie s) {
        AccesBetaseries.getInstance().betaSeries.recupEpisodes(s);
        return s.getEpisodes();
    }

    public static void deconnexionMembre() {
        AccesBetaseries inst = AccesBetaseries.getInstance();
        inst.betaSeries.destroyToken();
        inst.setMembreConnecte(null);

    }

    public static Member creationCompte(String identifiant, String password, String email) {
        AccesBetaseries inst = AccesBetaseries.getInstance();
        inst.setMembreConnecte(inst.betaSeries.createAccount(identifiant, password, email));
        return inst.membreConnecte;
    }

    public static Point getScreenSize() {
        return AccesBetaseries.getInstance().screenSize;
    }

    public static void setScreenSize(Point screenSize) {
        AccesBetaseries.getInstance().screenSize = screenSize;
    }

    public static List<Serie> getSeries(SeriesDisplay seriesDisplay) {
        return seriesDisplay.sort(AccesBetaseries.getInstance().membreConnecte.getSeries());
    }

    public static boolean estConnecte() {
        AccesBetaseries inst = AccesBetaseries.getInstance();
        return inst.membreConnecte != null && !inst.membreConnecte.getToken().equals("");

    }

    private void setMembreConnecte(Member membreConnecte) {
        this.membreConnecte = membreConnecte;
        this.betaSeries = new BetaSeries(cleApi, membreConnecte);
    }
}
