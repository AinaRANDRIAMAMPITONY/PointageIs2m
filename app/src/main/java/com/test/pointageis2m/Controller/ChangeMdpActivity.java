package com.test.pointageis2m.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.test.pointageis2m.Database.DBHelpers;
import com.test.pointageis2m.R;
import com.test.pointageis2m.Service.PasswordService;

public class ChangeMdpActivity extends AppCompatActivity {

    TextInputLayout ancienpassword,newpassword,confirmpassword;
    Button enregistrer, retour;
    DBHelpers DB;
    PasswordService passwordService;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mdp);
        context = this;
        passwordService = new PasswordService(context);

        enregistrer = findViewById(R.id.save);
        retour = findViewById(R.id.reTour);
        ancienpassword = findViewById(R.id.ancienpassword);
        newpassword = findViewById(R.id.newpassword);
        confirmpassword = findViewById(R.id.confirmpassword);
        DB = new DBHelpers(this);

        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ancienpass = ancienpassword.getEditText().getText().toString();
                String newpass = newpassword.getEditText().getText().toString();
                String confirmpass = confirmpassword.getEditText().getText().toString();

                if(TextUtils.isEmpty(ancienpass) || TextUtils.isEmpty(newpass) || TextUtils.isEmpty(confirmpass))
                    Toast.makeText(ChangeMdpActivity.this,"All fields Required",Toast.LENGTH_SHORT).show();
                else{
                    //raha mitovy amin'ilay mdp taloha le ancien password
                    boolean checkUsers = ancienpass.equals(passwordService.readPassword());
                    if(checkUsers){
                        //raha mitovy ilay password anakiroa vao ampidirina hanoloana azy
                        if(newpass.equals(confirmpass)) {
                            //manova an'ilay mdp any amin BD
                            //manomboka eto tsy mety
                            passwordService.addPassword(newpass);
                        }else {
                        Toast.makeText(ChangeMdpActivity.this,"password are not matching",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ChangeMdpActivity.this,"tsy marina ilay ancien mots de passe",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AboutFragment.class);
                startActivity(intent);
            }
        });
    }
}