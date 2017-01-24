package xyz.enableit.shaketopup.fragment;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xyz.enableit.shaketopup.R;
import xyz.enableit.shaketopup.adapter.RecyclerViewAdapter;
import xyz.enableit.shaketopup.model.Offer;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {


    public FragmentHome() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private View rootView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        loadView();

        initializeViewsAdapter();

        setList();


        return rootView;
    }

    private void setList() {


        Offer offer = new Offer();
        List<Offer> offerList = new ArrayList<>();

        for (int i = 0; i < 20; i++)
            offerList.add(offer);

        recyclerViewAdapter.setOfferList(offerList);

    }

    private void loadView() {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.home_rv_offer);
    }

    private void initializeViewsAdapter() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerViewAdapter = new RecyclerViewAdapter(getContext());
        recyclerView.setAdapter(recyclerViewAdapter);
    }

}
