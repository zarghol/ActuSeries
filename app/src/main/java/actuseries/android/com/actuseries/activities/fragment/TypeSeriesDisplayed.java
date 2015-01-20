package actuseries.android.com.actuseries.activities.fragment;

import java.util.ArrayList;
import java.util.List;

import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 20/01/2015.
 */
public enum TypeSeriesDisplayed {
    TOVIEW(0, "A voir"),
    ARCHIVED(1, "Archivée"),
    ACTIVE(2, "Active"),
    ALL(3, "Toutes");

    private int position;
    private String label;

    private TypeSeriesDisplayed(int position, String label) {
        this.position = position;
        this.label = label;
    }

    public List<Serie> trieSeries(List<Serie> series) {

        if (this == ALL) {
            return series;
        }

        List<Serie> result = new ArrayList<>();

        for (Serie s : series) {
            if (s.isActive()) {
                if (this == ACTIVE || (this == TOVIEW && !s.toutVue())) {
                    result.add(s);
                }
            } else if (this == ARCHIVED) {
                result.add(s);
            }
        }

        return result;
    }

    public int getPosition() {
        return this.position;
    }

    public String getLabel() {
        return this.label;
    }

    public  static TypeSeriesDisplayed fromPosition(int position) {
        for (TypeSeriesDisplayed t : TypeSeriesDisplayed.values()) {
            if (t.position == position) {
                return t;
            }
        }
        return null;
    }
}
