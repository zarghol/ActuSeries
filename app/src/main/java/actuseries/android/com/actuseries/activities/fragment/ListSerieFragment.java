package actuseries.android.com.actuseries.activities.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.activities.DetailSerieActivity;
import actuseries.android.com.actuseries.activities.LogAdapterSeries;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.event.EventBus;
import actuseries.android.com.actuseries.event.GetSerieResultEvent;
import actuseries.android.com.actuseries.event.GetSeriesResultEvent;
import actuseries.android.com.actuseries.metier.Serie;
import actuseries.android.com.actuseries.tasks.GetSeriesTask;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link }
 * interface.
 */
public class ListSerieFragment extends android.support.v4.app.ListFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "TypeSerie";

    private List<Serie> series;
    private LogAdapterSeries adapter;
    private  TypeSeriesDisplayed typeSeriesDisplayed;


    //private OnFragmentInteractionListener mListener;

    public static ListSerieFragment newInstance(TypeSeriesDisplayed param1) {
        ListSerieFragment fragment = new ListSerieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1.name());
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListSerieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.typeSeriesDisplayed = TypeSeriesDisplayed.valueOf(getArguments().getString(ARG_PARAM1));
            this.series = AccesBetaseries.getSeries(this.typeSeriesDisplayed);
        }
        this.adapter = new LogAdapterSeries(this.series, getActivity());
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


        //this.getListView().setOnItemClickListener(this);


    }




/*    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

/*    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent j = new Intent(getActivity(), DetailSerieActivity.class);
        j.putExtra("numSerie", position);
        j.putExtra("typePosition", this.typeSeriesDisplayed.getPosition());

        startActivityForResult(j, 1);

        /*if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(this.series.get(position));
        }*/
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
        this.adapter.notifyDataSetChanged();
    }

}