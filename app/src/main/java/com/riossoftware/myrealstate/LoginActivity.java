package com.riossoftware.myrealstate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText txtemail;
    private EditText txtpwd;
    private Button login;
    private ImageButton logGoogle;
    private Button singin;

    FirebaseAuth auth;
    DatabaseReference db;

    private String email = "";
    private String pwd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();


        txtemail = (EditText) findViewById(R.id.email);
        txtpwd = (EditText) findViewById(R.id.pwd);
        login = (Button) findViewById(R.id.login);
        logGoogle = (ImageButton) findViewById(R.id.logGoogle);
        singin = (Button) findViewById(R.id.singin);


        //Ingresar a la plataforma
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = txtemail.getText().toString();
                pwd = txtpwd.getText().toString();
                if(!email.isEmpty() && !pwd.isEmpty())
                    readUser();
                else
                    Toast.makeText(LoginActivity.this,"Ingrese por favor todos los datos",Toast.LENGTH_LONG).show();
            }
        });


        //Ir a registrar usuario
        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SinginActivity.class);
               startActivity(intent);
                finish();
            }
        });

    }



    private void readUser() {
        auth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Ingreso Exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, ProfileMainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

}