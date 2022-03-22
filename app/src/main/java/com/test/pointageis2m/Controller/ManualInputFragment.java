package com.test.pointageis2m.Controller;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.test.pointageis2m.Model.Pointage;
import com.test.pointageis2m.R;
import com.test.pointageis2m.Service.PointageService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ManualInputFragment extends Fragment {

    AutoCompleteTextView reasonChoices, statusChoices;
    TextInputLayout dateInput,hourInput, matricule, statut, raison;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormatDate;
    Button btnEnregistrer;
    PointageService pointageService;
    RadioButton radioButtonEntree, radioButtonSortie;

    public ManualInputFragment() {
        // Required empty public constructor
    }

    public static ManualInputFragment newInstance(String param1, String param2) {
        ManualInputFragment fragment = new ManualInputFragment();
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
        View view = inflater.inflate(R.layout.fragment_manual_input, container, false);

        init(view);

        return view;
    }

    private void init(View view){
        initItems(view);
        setDefaultValues();
        setListeners();
    }

    private void initItems(View view){
        pointageService = new PointageService(requireContext());
        reasonChoices = view.findViewById(R.id.selectChoice);
        statusChoices = view.findViewById(R.id.selectStatus);
        hourInput = view.findViewById(R.id.hourInput);
        dateInput = view.findViewById(R.id.dateInput);
        btnEnregistrer = view.findViewById(R.id.btnEnregistrer);
        radioButtonEntree = view.findViewById(R.id.entreeRadioButton);
        radioButtonSortie = view.findViewById(R.id.sortieRadioButton);
        matricule = view.findViewById(R.id.textFieldMatricule);
        statut = view.findViewById(R.id.textFieldStatus);
        raison = view.findViewById(R.id.textFieldRaison);
    }

    private void setDefaultValues(){
        String[] status = {"Etudiant", "Enseignant", "Résponsable", "Autre"};
        ArrayAdapter<String> adapter0 = new ArrayAdapter<>(requireContext(), R.layout.list_item, status);
        statusChoices.setAdapter(adapter0);

        String[] reasons = {"Absence du résponsable", "Oublie de la carte", "Perte de la carte", "Carte indisponible"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(requireContext(), R.layout.list_item, reasons);
        reasonChoices.setAdapter(adapter1);

        simpleDateFormatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        String dateTime0 = simpleDateFormatDate.format(calendar.getTime());
        dateInput.getEditText().setText(dateTime0);

        simpleDateFormatDate = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        String dateTime1 = simpleDateFormatDate.format(calendar.getTime());
        hourInput.getEditText().setText(dateTime1);
    }

    private void setListeners(){
        dateInput.getEditText().setOnClickListener(dateView -> {
            MaterialDatePicker picker = MaterialDatePicker.Builder.datePicker()
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();
            picker.show(getParentFragmentManager(), "tag");
            picker.addOnPositiveButtonClickListener(date -> {
                simpleDateFormatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
                String dateTime0 = simpleDateFormatDate.format(new Date(Long.parseLong(date.toString())));
                dateInput.getEditText().setText(dateTime0);
            });
        });

        hourInput.getEditText().setOnClickListener(hourView -> {
            MaterialTimePicker picker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .build();
            picker.show(getParentFragmentManager(), "tag");
            picker.addOnPositiveButtonClickListener(time -> {

                String selection = addZero(picker.getHour()) + ":" + addZero(picker.getMinute());
                hourInput.getEditText().setText(selection);
            });

        });

        btnEnregistrer.setOnClickListener(enregistrer -> {
            insertPointage();
        });
    }

    private String addZero(int toCheck){
        if (toCheck<10){
            return "0"+toCheck;
        }
        else{
            return String.valueOf(toCheck);
        }
    }

    private void insertPointage(){

        if(checkInputs()){
            Log.i("check", "inputs are ok");
            pointageService.savePointage(generatePointage());
            reset();
        }
        else {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Erreur de saisie")
                    .setMessage("Veuillez verifier que les informations saisies soient complètes")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
    }

    private boolean checkInputs(){

        if (matricule.getEditText().getText().toString().trim().length() == 0) {
            return false;
        }

        if (statut.getEditText().getText().toString().trim().length() == 0){
            return false;
        }

        if (raison.getEditText().getText().toString().trim().length() == 0){
            return false;
        }

        if(!radioButtonSortie.isChecked() && !radioButtonEntree.isChecked()){
            return false;
        }

        return true;
    }

    private Pointage generatePointage(){
        int mMatricule = Integer.parseInt(matricule.getEditText().getText().toString());
        String mStatut = statut.getEditText().getText().toString();
        String mRaison = raison.getEditText().getText().toString();
        String mDateHeure = dateInput.getEditText().getText().toString() + " " + hourInput.getEditText().getText().toString();
        boolean mSens = radioButtonEntree.isChecked();

        return new Pointage(
                mMatricule,
                mSens,
                mStatut,
                "",
                "",
                mRaison,
                mDateHeure
        );
    }

    private void reset(){
        matricule.getEditText().setText("");
        statut.getEditText().setText("");
        raison.getEditText().setText("");
        radioButtonEntree.setChecked(false);
        radioButtonSortie.setChecked(false);
        setDefaultValues();
    }
}