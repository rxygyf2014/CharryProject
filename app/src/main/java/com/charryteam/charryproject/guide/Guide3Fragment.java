package com.charryteam.charryproject.guide;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charryteam.charryproject.MainActivity;
import com.charryteam.charryproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Guide3Fragment extends Fragment {


    private TextView start;

    public Guide3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guide3, container, false);
        start = ((TextView) view.findViewById(R.id.tv_guide_start));
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

}
