package actuseries.android.com.actuseries.activities.fragment;

import java.util.ArrayList;
import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 20/01/2015.
 */
public enum SeriesDisplay {
    WATCHLIST(0, R.string.seriesList_watchlist),
    ARCHIVED(1, R.string.seriesList_archived),
    ACTIVE(2, R.string.seriesList_active),
    ALL(3, R.string.seriesList_all);

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

        if(this == ALL) {
            return series;
        }

        List<Serie> result = new ArrayList<>();

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
