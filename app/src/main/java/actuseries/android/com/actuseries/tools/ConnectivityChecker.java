package actuseries.android.com.actuseries.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Classe permettant de vérifier la connectivité Internet du téléphone.
 */
public class ConnectivityChecker {

    private ConnectivityManager connectivityManager;

    public ConnectivityChecker(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    //méthode permettant de vérifier si une connexion est disponible
    public static boolean connectivityAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}