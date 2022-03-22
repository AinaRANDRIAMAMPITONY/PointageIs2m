package com.test.pointageis2m.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.test.pointageis2m.Database.DBHelpers;
import com.test.pointageis2m.R;
import com.test.pointageis2m.Service.PasswordService;

public class RegisterActivity extends AppCompatActivity {
  TextInputLayout newpass, repass;
  Button login, exit;
  DBHelpers DB;
  PasswordService passwordService;

  //mangataka mots de passe amin voalohan
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    //maka anle champ ana mdp any anaty base des donnee raha efa misy
    //raha efa mis le mdp de makany amle activite verification izy fa raha mbola tss de mijanona amnito activite ito iany
    passwordService = new PasswordService(this);

    Log.i("adaddada", passwordService.readPassword() + " hhy");
    if (!passwordService.readPassword().isEmpty() || !passwordService.readPassword().equals("")) {
      Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
      startActivity(intent);
    }


    newpass = findViewById(R.id.password);
    repass = findViewById(R.id.repassword);
    DB = new DBHelpers(this);
    login = findViewById(R.id.save);
    exit = findViewById(R.id.exit);

    login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String pas = newpass.getEditText().getText().toString();
        String repas = repass.getEditText().getText().toString();

        login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            passwordService.addPassword(pas);
            Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
            startActivity(intent);
          }
        });
//                if(TextUtils.isEmpty(pas) || TextUtils.isEmpty(repas))
//                    Toast.makeText(RegisterActivity.this,"All fields Required",Toast.LENGTH_SHORT).show();
//                else
//                if(pas.equals(repas)){
//                    Boolean checkuUser = DB.checkUsernamePassword(pas);
//                    if(!checkuUser){
//                        Boolean insert = DB.insertData(pas);
//                        if(insert){
//                            Toast.makeText(RegisterActivity.this,"Registered successfully",Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
//                            startActivity(intent);
//                        }
//                        else{
//                            Toast.makeText(RegisterActivity.this,"Registered failed",Toast.LENGTH_SHORT).show();
//                        }
//                    }else {
//                        Toast.makeText(RegisterActivity.this,"User already exist",Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Toast.makeText(RegisterActivity.this,"password are not matching",Toast.LENGTH_SHORT).show();
//                }

      }
    });

    exit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        System.exit(0);
      }
    });
  }
}
