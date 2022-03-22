package com.test.pointageis2m.Service;

import android.content.Context;

import com.test.pointageis2m.Utils.SQLiteHelper;

public class PasswordService {
        SQLiteHelper db;
        Context context;
        public PasswordService(Context context) {
          this.context = context;
          db = new SQLiteHelper(context);
        }
        public void addPassword(String password){
          db.addPassword(password);
        }
        public String readPassword(){
          return db.readPassword();
        }
}
