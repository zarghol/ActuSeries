package actuseries.android.com.actuseries.betaseries;

import android.util.Log;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import actuseries.android.com.actuseries.metier.Member;
import actuseries.android.com.actuseries.metier.Serie;

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

    public static void connexionMembre(final String identifiant, final String password) {
        // TODO pas plutot au niveau des task ??
        new Thread(new Runnable() {
            @Override
            public void run() {
                AccesBetaseries inst = AccesBetaseries.getInstance();
                inst.setMembreConnecte(inst.betaSeries.obtainMember(identifiant, password));
                Log.d("ActuSeries", "connecté, token : " + inst.membreConnecte.getToken());
                inst.setChanged();
                inst.notifyObservers();
            }
        }).start();
    }

    public static void recupereInfosMembre() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AccesBetaseries inst = AccesBetaseries.getInstance();
                inst.betaSeries.getMemberInformations(inst.membreConnecte);
                Log.d("ActuSeries", "series : " + inst.membreConnecte.getSeries().size());
                inst.setChanged();
                inst.notifyObservers(inst.membreConnecte.getSeries());
            }
        }).start();
    }

    public static void deconnexionMembre() {
       // TODO unimplemented method
    }

    public static void creationCompte(/* TODO not defined */) {
        // TODO unimplemented method
    }

    public static void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AccesBetaseries inst = AccesBetaseries.getInstance();
                inst.setMembreConnecte(inst.betaSeries.obtainMember("zarghol", "abernaty"));
                inst.betaSeries.getMemberInformations(inst.membreConnecte);
                Log.d("ActuSeries", "series : " + inst.membreConnecte.getSeries().size());
            }
        }).start();
    }

    private void setMembreConnecte(Member membreConnecte) {
        this.membreConnecte = membreConnecte;
        this.betaSeries = new BetaSeries(cleApi, membreConnecte);
    }

    public static List<Serie> getSeries() {
        Log.d("actuseries", "getSeries");
        return AccesBetaseries.getInstance().membreConnecte.getSeries();
    }

    public static void addObs(Observer o) {
        Log.d("actuseries", "addObs");
        AccesBetaseries.getInstance().addObserver(o);
    }
    public static void removeObs(Observer o) {
        Log.d("actuseries", "removeObs");

        AccesBetaseries.getInstance().deleteObserver(o);
    }

    public static boolean estConnecte() {
        AccesBetaseries inst = AccesBetaseries.getInstance();
        return inst.membreConnecte != null && !inst.membreConnecte.getToken().equals("");

    }
}
