package com.test.pointageis2m.Service;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;

import com.test.pointageis2m.Model.Pointage;
import com.test.pointageis2m.Utils.SQLiteHelper;
import com.test.pointageis2m.Utils.VolleySingleton;

import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.List;

public class PointageService {
    SQLiteHelper db;
    Context context;

    public PointageService(Context context){
        this.context = context;
        db = new SQLiteHelper(context);
    }

    public void savePointage(Pointage pointage){
        db.addPointage(pointage);
    }

    public void deletePointage(String id){
        db.deletePointageRecord(id);
    }

    public void deleteAllPointages(){
        db.deleteAllPointageRecord();
    }

    public List<Pointage> getAllSavedPointages(){
        return db.readAllPointages();
    }

}
