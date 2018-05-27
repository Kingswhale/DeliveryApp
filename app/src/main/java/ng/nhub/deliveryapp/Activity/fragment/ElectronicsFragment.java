package ng.nhub.deliveryapp.Activity.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ng.nhub.deliveryapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ElectronicsFragment extends Fragment {

    private Context context;

    public ElectronicsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_electronics, container, false);

        return view;
    }

}
