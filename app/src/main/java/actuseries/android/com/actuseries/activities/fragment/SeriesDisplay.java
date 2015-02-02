package actuseries.android.com.actuseries.activities.fragment;

import java.util.ArrayList;
import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 20/01/2015.
 */
public enum SeriesDisplay {
    ALL(0, R.string.seriesList_all),
    WATCHLIST(1, R.string.seriesList_watchlist),
    ACTIVE(2, R.string.seriesList_active),
    ARCHIVED(3, R.string.seriesList_archived);

    private int position;
    private int label;


    private SeriesDisplay(int position, int label) {
        this.position = position;
        this.label = label;
    }

    public static SeriesDisplay fromPosition(int position) {
        for(SeriesDisplay t : SeriesDisplay.values()) {
            if(t.position == position) {
                return t;
            }
        }
        return null;
    }

    public List<Serie> sort(List<Serie> series) {
        List<Serie> result = new ArrayList<>();

        if(this == ALL) {
            result.addAll(series);
            return result;
        }

        for(Serie s : series) {
            if(s.isActive()) {
                if(this == ACTIVE || (this == WATCHLIST && !s.toutVue())) {
                    result.add(s);
                }
            } else if(this == ARCHIVED) {
                result.add(s);
            }
        }

        return result;
    }

    public int getPosition() {
        return this.position;
    }

    public int getLabel() {
        return this.label;
    }
}
