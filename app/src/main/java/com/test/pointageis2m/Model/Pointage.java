package com.test.pointageis2m.Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Pointage {
    private String idPointage = generateId();
    private int matricule = 0;
    private boolean sens = true;
    private String statut = "";
    private String nom = "";
    private String prenom = "";
    private String raison = "";
    private String heure = getDate();

    public Pointage(int matricule, boolean sens, String statut, String nom, String prenom, String raison){
       this.matricule = matricule;
       this.sens = sens;
       this.statut = statut;
       this.nom = nom;
       this.prenom = prenom;
       this.raison = raison;
    }

    public Pointage(int matricule, boolean sens, String statut, String nom, String prenom, String raison, String dateHeure){
        this.matricule = matricule;
        this.sens = sens;
        this.statut = statut;
        this.nom = nom;
        this.prenom = prenom;
        this.raison = raison;
        this.heure = dateHeure;
    }

    private String getDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE);
        return simpleDateFormatDate.format(calendar.getTime());
    }

    private String generateId(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int max = 10000;
        int min = 0;
        int nextValue = (int) Math.round(Math.random() * (max-min+1)+min);
        return timestamp.getTime() + String.valueOf(nextValue);
    }

    public int getMatricule() {
        return matricule;
    }
    public String getIdPointage(){
        return idPointage;
    }
    public String getStatut(){
        return statut;
    }
    public String getNom(){
        return nom;
    }
    public String getPrenom(){
        return prenom;
    }
    public String getRaison(){
        return raison;
    }
    public String getHeure(){
        return heure;
    }
    public boolean isSens(){
        return sens;
    }
    public void setIdPointage(String idPointage)
    {
        this.idPointage = idPointage;
    }
    public void setMatricule(int matricule)
    {
        this.matricule = matricule;
    }
    public void setStatut(String statut)
    {
        this.statut = statut;
    }
    public void setNom(String nom)
    {
        this.nom = nom;
    }
    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }
    public void setRaison(String raison)
    {
        this.raison = raison;
    }
    public void setSens(boolean sens)
    {
        this.sens = sens;
    }
    public void setHeure(String heure)
    {
        this.heure = heure;
    }


}
