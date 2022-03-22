package com.test.pointageis2m.Controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.pointageis2m.Controller.ListAdapter.HistoryListAdapter;
import com.test.pointageis2m.Model.Pointage;
import com.test.pointageis2m.R;
import com.test.pointageis2m.Service.PointageService;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    RecyclerView historyList;
    PointageService pointageService;
    List<Pointage> pointageList = new ArrayList<>();

    TextView status;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        pointageService = new PointageService(requireContext());
        this.init(view);
        return view;
    }

    private void init(View view){

        pointageList = pointageService.getAllSavedPointages();

        status = view.findViewById(R.id.textViewStatusEnregistrement);
        status.setVisibility(View.INVISIBLE);

        historyList = view.findViewById(R.id.historyList);
        historyList.setHasFixedSize(true);
        historyList.setLayoutManager(new LinearLayoutManager(requireContext()));

        RecyclerView.Adapter adapter = new HistoryListAdapter(pointageList, requireContext(), historyList, status);
        historyList.setAdapter(adapter);

        if(pointageList.size() == 0){
            status.setVisibility(View.VISIBLE);
            historyList.setVisibility(View.INVISIBLE);
        }

    }
}