package de.hdm_tunes2;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LibraryFragment extends Fragment {

    private TextView label;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wikipedia,container,false);

        context = view.getContext();

        label = (TextView) view.findViewById(R.id.label);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

}
