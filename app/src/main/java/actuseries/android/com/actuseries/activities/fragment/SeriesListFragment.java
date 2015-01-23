package actuseries.android.com.actuseries.activities.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import actuseries.android.com.actuseries.activities.SerieDetailActivity;
import actuseries.android.com.actuseries.activities.SeriesLogAdapter;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link }
 * interface.
 */
public class SeriesListFragment extends android.support.v4.app.ListFragment {

    private static final String ARG_PARAM1 = "TypeSerie";

    private List<Serie> series;
    private SeriesLogAdapter adapter;
    private SeriesDisplay seriesDisplay;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SeriesListFragment() {
    }

    public static SeriesListFragment newInstance(SeriesDisplay param1) {
        SeriesListFragment fragment = new SeriesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1.name());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            this.seriesDisplay = SeriesDisplay.valueOf(getArguments().getString(ARG_PARAM1));
            this.series = AccesBetaseries.getSeries(this.seriesDisplay);
        }
        this.adapter = new SeriesLogAdapter(this.series, getActivity());
        setListAdapter(this.adapter);


/*        TextView noseriesTextView = new TextView(getActivity());
        noseriesTextView.setText("Pas de séries :(");

        noseriesTextView.setTextColor(getResources().getColor(R.color.grey));
        noseriesTextView.setTextSize(12);
        noseriesTextView.setGravity(Gravity.CENTER_VERTICAL
                | Gravity.CENTER_HORIZONTAL);
        noseriesTextView.setVisibility(View.GONE);
        ((ViewGroup) getListView().getParent()).addView(noseriesTextView);


        this.getListView().setEmptyView(noseriesTextView);*/
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent j = new Intent(getActivity(), SerieDetailActivity.class);
        j.putExtra("numSerie", position);
        j.putExtra("typePosition", this.seriesDisplay.getPosition());

        startActivityForResult(j, 1);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
/*    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Serie serie);
    }*/
    public void notifyDataChanged() {
        if(this.adapter != null) {
            this.adapter.notifyDataSetChanged();
        }
    }

}
