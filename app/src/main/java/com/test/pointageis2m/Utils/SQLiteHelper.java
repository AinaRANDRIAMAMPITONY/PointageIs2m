package com.test.pointageis2m.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.test.pointageis2m.Model.Pointage;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Is2m_pointage.db";
    private static final int DATABASE_VERSION = 1;
    /* pointage */
    private static final String TABLE1_NAME = "pointage_storage";
    //private static final String TABLE2_NAME = "auth_storage";
    private static final String COLUMN_ID = "pointage_id";
    private static final String COLUMN_MATRICULE = "pointage_matricule";
    private static final String COLUMN_STATUT = "pointage_statut";
    private static final String COLUMN_NOM = "pointage_nom";
    private static final String COLUMN_PRENOM = "pointage_prenom";
    private static final String COLUMN_RAISON = "pointage_raison";
    private static final String COLUMN_SENS = "pointage_sens";
    private static final String COLUMN_HEURE = "pointage_heure";

    /* mots de passe */

  private static final String TABLE2_PASSWORD = "password";
  private static final String COLUMN_PASSWORD = "password";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE1_NAME +
                " (" + COLUMN_ID + " TEXT PRIMARY KEY," +
                COLUMN_MATRICULE + " INTEGER," +
                COLUMN_STATUT + " TEXT," +
                COLUMN_NOM + " TEXT," +
                COLUMN_PRENOM + " TEXT," +
                COLUMN_RAISON + " TEXT," +
                COLUMN_SENS + " NUMERIC," +
                COLUMN_HEURE + " TEXT);";

        sqLiteDatabase.execSQL(query);

        String quer = "CREATE TABLE " + TABLE2_PASSWORD +
              "(" + COLUMN_PASSWORD + " TEXT PRIMARY KEY );";
      sqLiteDatabase.execSQL(quer);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addPointage(Pointage pointage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, pointage.getIdPointage());
        cv.put(COLUMN_MATRICULE, pointage.getMatricule());
        cv.put(COLUMN_STATUT, pointage.getStatut());
        cv.put(COLUMN_NOM, pointage.getNom());
        cv.put(COLUMN_PRENOM, pointage.getPrenom());
        cv.put(COLUMN_RAISON, pointage.getRaison());
        int num = 1;
        if (!pointage.isSens()){
            num = 0;
        }
        cv.put(COLUMN_SENS, num);
        cv.put(COLUMN_HEURE, pointage.getHeure());

        long result = db.insert(TABLE1_NAME,null, cv);

        if(result == -1){
            Toast.makeText(context, "Une erreur est survenu, enregistrement annulé", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "Pointage " + pointage.getMatricule() + " sauvegardé", Toast.LENGTH_LONG).show();
        }

    };

    public void deletePointageRecord(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE1_NAME, COLUMN_ID + "=" + id, null);
        if(result == -1){
            Toast.makeText(context, "Une erreur est survenu, suppression annulé", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context, "Pointage effacé avec succès", Toast.LENGTH_LONG).show();
        }
    }

    public long deleteAllPointageRecord(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE1_NAME, null, null);
    }

    public List<Pointage> readAllPointages(){
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = null;
        try{
            db = this.getReadableDatabase();
        }
        catch (Exception e){
            Log.i("Exception", e.getMessage());
        }
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        List<Pointage> pointageList = new ArrayList<>();
        while (true){
            assert cursor != null;
            if (!cursor.moveToNext())
                break;
            Pointage pointage = new Pointage();
                pointage.setIdPointage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                pointage.setMatricule(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MATRICULE)));
                pointage.setStatut(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUT)));
                pointage.setNom(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOM)));
                pointage.setPrenom(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRENOM)));
                pointage.setRaison(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RAISON)));

                boolean sens = true;

            if(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SENS)) == 0){
                sens = false;
            }

            pointage.setSens(sens);
            pointage.setHeure(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HEURE)));

            pointageList.add(pointage);
        }

        return pointageList;
    }

    public void addPassword (String password){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues cv = new ContentValues();

      cv.put(COLUMN_PASSWORD, password);
      String req = "DELETE FROM  "+TABLE2_PASSWORD;
      db.execSQL(req);
      long result = db.insert(TABLE2_PASSWORD,null, cv);

      if(result == -1){
        Toast.makeText(context, "Une erreur est survenu, enregistrement annulé", Toast.LENGTH_LONG).show();
      }
      else{
        Toast.makeText(context, "mots de passe sauvegardé", Toast.LENGTH_LONG).show();
      }
    }

    public String readPassword(){
      String query = "SELECT * FROM " + TABLE2_PASSWORD;
      SQLiteDatabase db = null;
      try{
        db = this.getReadableDatabase();
      }
      catch (Exception e){
        Log.i("Exception", e.getMessage());
      }
      Cursor cursor = null;
      if (db != null) {
        cursor = db.rawQuery(query, null);
      }
      String password = "";
      while (true){
        assert cursor != null;
        if (!cursor.moveToNext()) break;
        password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
        Log.i("dede",password);
      }
      return password;
    }


}
