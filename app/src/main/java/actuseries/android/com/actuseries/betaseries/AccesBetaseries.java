package actuseries.android.com.actuseries.betaseries;

import android.util.Log;

import actuseries.android.com.actuseries.metier.Member;

/**
 * Created by Clement on 11/12/2014.
 */
public class AccesBetaseries {
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

        this.betaSeries = new BetaSeries(cleApi, token);
    }

    public static void connexionMembre(String identifiant, String password) {
        // TODO unimplemented method
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

                inst.betaSeries.getMemberInformations(inst.membreConnecte);
                Log.d("ActuSeries", "series : " + inst.membreConnecte.getSeries().size());
            }
        }).start();
    }
}
