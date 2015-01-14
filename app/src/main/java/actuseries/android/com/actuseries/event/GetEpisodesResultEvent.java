package actuseries.android.com.actuseries.event;

import java.util.List;

import actuseries.android.com.actuseries.metier.Episode;

/**
 * Created by charly on 14/01/2015.
 */
public class GetEpisodesResultEvent {
    private List<Episode> episodes;

    public GetEpisodesResultEvent(List<Episode> episodes) {

        this.episodes = episodes;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

}
