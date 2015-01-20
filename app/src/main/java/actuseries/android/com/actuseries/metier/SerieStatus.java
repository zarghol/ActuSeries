package actuseries.android.com.actuseries.metier;

/**
 * Created by Clement on 19/12/2014.
 */
public enum SerieStatus {
    ENCOURS("Continuing"),
    FINI("Ended"),
    AUTRE("Autre");

    public String getStringStatus() {
        return stringStatus;
    }

    final private String stringStatus;

    private SerieStatus(String status) {
        this.stringStatus = status;
    }

    public static SerieStatus valueOfByString(String value) throws IllegalArgumentException {
        for (SerieStatus status : SerieStatus.values()) {
            if (status.stringStatus.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("unknown status.");
    }

}
