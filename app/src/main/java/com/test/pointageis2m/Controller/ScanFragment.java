package com.test.pointageis2m.Controller;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.test.pointageis2m.Model.Pointage;
import com.test.pointageis2m.R;
import com.test.pointageis2m.Service.PointageService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanFragment extends Fragment {
    private CodeScanner mCodeScanner;
    private PointageService pointageService;

    public ScanFragment() {
        // Required empty public constructor
    }

    public static ScanFragment newInstance(String param1, String param2) {
        ScanFragment fragment = new ScanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.askPermission();

        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        this.initFragment(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void askPermission(){
        int requestCode = 100;
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(requireActivity(), new String[] {Manifest.permission.CAMERA}, requestCode);
        }
    }

    private void initFragment(View view){
        CodeScannerView scannerView = view.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(requireActivity(), scannerView);
        mCodeScanner.setDecodeCallback(result -> {
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getResult(result.getText());
                }
            });
        });
    }

    private void getResult(String result){
        Gson gson = new Gson();
        try{
            Pointage pointage = gson.fromJson(result, Pointage.class);
            //Log.i("pointage", pointage.toString());
            showSensDialog(pointage);
        }
        catch (Exception e){
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Erreur")
                    .setMessage("Le code QR n'est pas valide")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mCodeScanner.startPreview();
                        }
                    })
                    .show();
        }

    }

    private void showSensDialog(Pointage pointage){
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Sens")
                .setIcon(R.drawable.ic_baseline_compare_arrows_24)
                .setMessage("Veuillez séléctionner le sens du pointage")
                .setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(requireActivity(), "Enregistrement annulé", Toast.LENGTH_SHORT).show();
                        mCodeScanner.startPreview();
                    }
                })
                .setPositiveButton("Entrée", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pointage.setSens(true);
                        savePointage(pointage);
                    }
                })
                .setNegativeButton("Sortie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pointage.setSens(false);
                        savePointage(pointage);
                    }
                })
                .show();
    }

    private void savePointage(Pointage pointage){
        //TODO call service to save pointage
        pointageService = new PointageService(requireContext());
        try{
            pointageService.savePointage(pointage);
        }
        catch (Exception e){
            Log.i("Exception", e.getMessage());
        }

        Log.i("pointage", pointage.toString());
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
        mCodeScanner.startPreview();
    }
}