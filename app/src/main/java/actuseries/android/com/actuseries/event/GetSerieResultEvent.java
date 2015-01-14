package actuseries.android.com.actuseries.event;

import java.util.List;

import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 14/01/2015.
 */
public class GetSerieResultEvent {
    private Serie serie;

    public GetSerieResultEvent(Serie serie) {
        this.serie = serie;
    }

    public Serie getSerie() {
        return serie;
    }
}
