package com.test.pointageis2m.Controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.ybq.android.spinkit.SpinKitView;
import com.test.pointageis2m.R;
import com.test.pointageis2m.Service.Callback.PostPointageCallback;
import com.test.pointageis2m.Service.PointageAPIService;
import com.test.pointageis2m.Service.PointageService;

import org.json.JSONArray;

public class SynchronizeFragment extends Fragment {
    TextView status, dateHour, nombre;
    Button synch;
    SpinKitView loading;
    PointageAPIService pointageAPIService;
    PointageService pointageService;

    public SynchronizeFragment() {
        // Required empty public constructor
    }

    public static SynchronizeFragment newInstance(String param1, String param2) {
        SynchronizeFragment fragment = new SynchronizeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_synchronize, container, false);
        init(view);

        //Set default value
        nombre.setText(String.valueOf(pointageService.getAllSavedPointages().size()));

        setListeners();
        return view;
    }

    private void init(View view){
        status = view.findViewById(R.id.textViewSynchStatus);
        dateHour = view.findViewById(R.id.textViewDateHour);
        synch = view.findViewById(R.id.Scn);
        loading = view.findViewById(R.id.spin_kit);
        nombre = view.findViewById(R.id.textViewNombre);
        status.setVisibility(View.INVISIBLE);
        pointageAPIService = new PointageAPIService(requireContext());
        pointageService = new PointageService(requireContext());
    }

    private void setListeners(){
        synch.setOnClickListener(view -> {
            loading.setVisibility(View.VISIBLE);
            synch();
        });
    }

    private void synch(){
        pointageAPIService.synchronize(new PostPointageCallback() {
            @Override
            public void getResponse(JSONArray response) {
                loading.setVisibility(View.INVISIBLE);
                pointageService.deleteAllPointages();
                status.setVisibility(View.VISIBLE);
                status.setText("Synchronisation éffectuée");
                status.setTextColor(getResources().getColor(R.color.green_success));
                nombre.setText("0");
            }

            @Override
            public void getError(VolleyError error) {
                loading.setVisibility(View.INVISIBLE);
                status.setVisibility(View.VISIBLE);
                status.setText("Erreur lors de la requête - " + error.getMessage());
                status.setTextColor(getResources().getColor(R.color.black));
            }
        });
    }
}