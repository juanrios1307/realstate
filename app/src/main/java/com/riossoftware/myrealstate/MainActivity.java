package com.riossoftware.myrealstate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    private final int DURACION_SPLASH = 2500; // 3 segundos

    FirebaseAuth auth2;
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Tenemos una plantilla llamada splash.xml donde mostraremos la información que queramos (logotipo, etc.)
        setContentView(R.layout.activity_main);

        auth2=FirebaseAuth.getInstance();



        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                if(auth2.getCurrentUser() != null){
                    FirebaseUser firebaseUser = auth2.getCurrentUser();
                    Intent intent = new Intent(MainActivity.this, ProfileMainActivity.class);
                    startActivity(intent);
                    finish();

                } else{

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            ;
        }, DURACION_SPLASH);
    }

}