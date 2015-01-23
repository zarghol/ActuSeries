package actuseries.android.com.actuseries.activities.fragment;

import java.util.ArrayList;
import java.util.List;

import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 20/01/2015.
 */
public enum SeriesDisplay {
    //    FIXME: Utiliser les ressources et pas des chaînes en dur !
//    WATCHLIST(0, R.string.series_list_watchlist),
//    ARCHIVED(1, R.string.series_list_archived),
//    ACTIVE(2, R.string.series_list_active),
//    ALL(3, R.string.series_list_all);
    WATCHLIST(0, "A voir"),
    ARCHIVED(1, "Archivées"),
    ACTIVE(2, "Actives"),
    ALL(3, "Toutes");

    private int position;
    //    private int label;
    private String label;

    //    private SeriesDisplay(int position, int label) {
    private SeriesDisplay(int position, String label) {
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

    //    public int getLabel() {
    public String getLabel() {
        return this.label;
    }
}