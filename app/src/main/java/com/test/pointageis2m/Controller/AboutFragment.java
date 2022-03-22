package com.test.pointageis2m.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.test.pointageis2m.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context con;
    CardView cardView1;
    CardView cardView2,cardView3,cardView4;

    public AboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);;

         cardView1 = view.findViewById(R.id.card1);
         cardView2 = view.findViewById(R.id.card2);
         cardView3 = view.findViewById(R.id.card3);
         cardView4 = view.findViewById(R.id.card4);


         cardView1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(view.getContext(), VersionActivity.class);
                 startActivity(intent);
                 //Log.i("mety","mandeha mm");
             }
         });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ManuelActivity.class);
                startActivity(intent);
                //Log.i("mety","mandeha mm");
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*AlertDialog alertDialog = new AlertDialog.Builder(AboutFragment.this).create(); //Read Update
                alertDialog.setTitle("hi");
                alertDialog.setMessage("this is my app");

                alertDialog.setButton("Continue..", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //here you can add functions
                    }
                });

                alertDialog.show();  //<-- See This!

                 */

                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Mode nuit")
                        .setMessage("Choisissez votre theme")
                        .setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Sombre", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            }
                        })
                        .setNegativeButton("light", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            }
                        })
                        .show();
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ChangeMdpActivity.class);
                startActivity(intent);
                //Log.i("mety","mandeha mm");
            }
        });
         return view;

        //return inflater.inflate(R.layout.fragment_about, container, false);
    }
}