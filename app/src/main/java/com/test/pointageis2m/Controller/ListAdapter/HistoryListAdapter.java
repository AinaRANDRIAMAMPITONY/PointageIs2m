package com.test.pointageis2m.Controller.ListAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.test.pointageis2m.Model.Pointage;
import com.test.pointageis2m.R;
import com.test.pointageis2m.Service.PointageService;

import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder>{

    private List<Pointage> pointages;
    private Context context;
    private PointageService pointageService;
    RecyclerView mRecyclerView;
    TextView status;

    public HistoryListAdapter(List<Pointage> pointages, Context context, RecyclerView recyclerView, TextView status){
        this.mRecyclerView = recyclerView;
        this.pointages = pointages;
        this.context = context;
        this.status = status;
        this.pointageService = new PointageService(this.context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        LayoutInflater li = LayoutInflater.from(ctx);
        View view = li.inflate(R.layout.history_list_item, parent, false);

        //View itemView = LayoutInflater.from(this.context).inflate(R.layout.history_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pointage pointage = pointages.get(position);

        holder.matricule.setText(String.valueOf(pointage.getMatricule()));
        holder.statut.setText(pointage.getStatut());
        String nomPrenom = pointage.getNom() + " " +pointage.getPrenom();
        holder.nomPrenom.setText(nomPrenom);
        holder.chipDate.setText(pointage.getHeure());
        holder.raison.setText(pointage.getRaison());
        String sens = "entrée";
        if(!pointage.isSens()){
            sens = "sortie";
        }
        holder.sens.setText(sens);

        holder.btnSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askDeleteConfirmation(holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.pointages.size();
    }

    private void remove(RecyclerView.ViewHolder holder){
        int position = holder.getAdapterPosition();

        Pointage pointage = pointages.get(position);
        pointages.remove(position);
        pointageService.deletePointage(pointage.getIdPointage());
        notifyItemRemoved(position);

        if(pointages.size() == 0){
            status.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
    }

    private void askDeleteConfirmation(RecyclerView.ViewHolder holder){

        new MaterialAlertDialogBuilder(context)
                .setTitle("Suppression")
                .setMessage("Etes-vous sur de supprimer ce pointage?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        remove(holder);
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView matricule, statut, nomPrenom,sens,raison;
        public Chip chipDate;
        public Button btnSupprimer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.init(itemView);
        }

        private void init(View itemView){
            matricule = itemView.findViewById(R.id.textViewMatricule);
            statut = itemView.findViewById(R.id.textViewStatus);
            nomPrenom = itemView.findViewById(R.id.textViewNomPrenom);
            chipDate = itemView.findViewById(R.id.chipDate);
            sens = itemView.findViewById(R.id.textViewSens);
            raison = itemView.findViewById(R.id.textViewRaison);
            btnSupprimer = itemView.findViewById(R.id.buttonSpr);
        }
    }
}
