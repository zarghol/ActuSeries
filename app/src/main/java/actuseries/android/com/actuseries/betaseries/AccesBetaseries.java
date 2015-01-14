package actuseries.android.com.actuseries.betaseries;

import android.util.Log;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import actuseries.android.com.actuseries.metier.Episode;
import actuseries.android.com.actuseries.metier.Member;
import actuseries.android.com.actuseries.metier.Serie;
import actuseries.android.com.actuseries.tasks.GetSeriesTask;

/**
 * Created by Clement on 11/12/2014.
 */
public class AccesBetaseries extends Observable {
    private final static String cleApi = "e88a334499a9";
    private Member membreConnecte;
    private BetaSeries betaSeries;

    private static AccesBetaseries instance;

    private static AccesBetaseries getInstance() {
        if (instance == null) {
            instance = new AccesBetaseries();
        }
        return instance;
    }

    private AccesBetaseries() {
        this.membreConnecte = Member.getFromPersistance();
        String token = this.membreConnecte != null ? this.membreConnecte.getToken() : "";

        this.betaSeries = new BetaSeries(cleApi);
    }

    public static Member connexionMembre(final String identifiant, final String password) {
        // TODO pas plutot au niveau des task ??
        AccesBetaseries inst = AccesBetaseries.getInstance();
        inst.setMembreConnecte(inst.betaSeries.obtainMember(identifiant, password));
        inst.setChanged();
        inst.notifyObservers();
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
        inst.betaSeries.recupBanner(s);
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

    public static void creationCompte(String identifiant, String password, String email) {
        AccesBetaseries inst = AccesBetaseries.getInstance();

        inst.setMembreConnecte(inst.betaSeries.createAccount(identifiant, password, email));
    }

    private void setMembreConnecte(Member membreConnecte) {
        this.membreConnecte = membreConnecte;
        this.betaSeries = new BetaSeries(cleApi, membreConnecte);
    }

    public static List<Serie> getSeries() {
        return AccesBetaseries.getInstance().membreConnecte.getSeries();
    }

    @Deprecated
    public static void addObs(Observer o) {
        AccesBetaseries.getInstance().addObserver(o);
    }

    @Deprecated
    public static void removeObs(Observer o) {
        AccesBetaseries.getInstance().deleteObserver(o);
    }

    public static boolean estConnecte() {
        AccesBetaseries inst = AccesBetaseries.getInstance();
        return inst.membreConnecte != null && !inst.membreConnecte.getToken().equals("");

    }
}
