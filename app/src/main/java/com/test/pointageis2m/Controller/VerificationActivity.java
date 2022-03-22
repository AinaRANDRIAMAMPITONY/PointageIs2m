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

public class VerificationActivity extends AppCompatActivity {
    TextInputLayout password;
    Button login, exit;
    DBHelpers DB;
    PasswordService passwordService;
    Context context;

    //mangataka anle mots de pass le efa nampidirian raha mitovy de mandeha fa raha  ts mety
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        context = this;
        passwordService = new PasswordService(context);


        login = findViewById(R.id.login);
        exit = findViewById(R.id.exit);
        password = findViewById(R.id.password);
        DB = new DBHelpers(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password.getEditText().getText().toString();

                if( TextUtils.isEmpty(pass)) {
                    Toast.makeText(VerificationActivity.this, "All fields Required", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean checkUsers = pass.equals(passwordService.readPassword());
                    if(checkUsers){
                        Toast.makeText(VerificationActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(VerificationActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    }
                }
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
