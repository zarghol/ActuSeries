package actuseries.android.com.actuseries.event;

import java.util.List;

import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by charly on 14/01/2015.
 */
public class GetSeriesResultEvent {
    private List<Serie> series;

    public GetSeriesResultEvent(List<Serie> series) {
        this.series = series;
    }

    public List<Serie> getSeries() {
        return series;
    }
}
