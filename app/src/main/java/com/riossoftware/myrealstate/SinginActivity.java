package com.riossoftware.myrealstate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;

public class SinginActivity extends AppCompatActivity {

    private EditText txtname, txtpwd, txtemail, txtcpwd;
    private Button btnRegistro;

    //Datos a registrar
    private String name = "";
    private String pwd = "";
    private String email = "";
    private String cpwd = "";
    private String token="";

    AlertDialog.Builder builder;

    Intent intent;

    // Variables firebase
    FirebaseAuth auth;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        txtemail = (EditText) findViewById(R.id.email);
        txtpwd = (EditText) findViewById(R.id.pwd);
        txtcpwd = (EditText) findViewById(R.id.pwd);
        txtname = (EditText) findViewById(R.id.txtname);
        btnRegistro=(Button) findViewById(R.id.btnsignin);


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=txtname.getText().toString();
                email=txtemail.getText().toString();
                pwd=txtpwd.getText().toString();
                cpwd=txtcpwd.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !pwd.isEmpty() && !cpwd.isEmpty()) {
                    if (pwd.length() >= 6) {
                        if (pwd.equals(cpwd)) {

                            //Ingreso a db
                            FirebaseInstanceId.getInstance().getInstanceId()
                                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                            if (!task.isSuccessful()) {
                                                Log.w("Token", "getInstanceId failed", task.getException());
                                                return;
                                            }

                                            // Get new Instance ID token
                                            token = task.getResult().getToken();

                                            // Log and toast
                                            String msg ="Token: "+token;
                                            Log.d("Token", msg);
                                        }
                                    });

                            registerUser();

                        } else {
                            Toast.makeText(SinginActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(SinginActivity.this, "La contraseña debe contener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SinginActivity.this, "Debes completar los campos", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
    private void registerUser() {
        auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    System.out.println("index registro fase 1 bien");
                    //Lista con valores para add to firebase
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("pwd", pwd);
                    map.put("token",token);

                    final String id = auth.getCurrentUser().getUid();

                    System.out.println("ID EMAIL"+id);

                    db.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {

                                System.out.println("index registro fase 2 bien");
                                Toast.makeText(SinginActivity.this, "Datos subidos correctamente", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SinginActivity.this, ProfileMainActivity.class));
                                finish();

                            } else {
                                Toast.makeText(SinginActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(SinginActivity.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}