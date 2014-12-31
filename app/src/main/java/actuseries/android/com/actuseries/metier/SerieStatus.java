package actuseries.android.com.actuseries.metier;

/**
 * Created by Clement on 19/12/2014.
 */
public enum SerieStatus {
    ENCOURS("continuing"),
    FINI("ended"),
    AUTRE("autre");

    private String stringStatus;

    private SerieStatus(String status) {
        this.stringStatus = status;
    }
}
