package com.riossoftware.myrealstate.actions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.riossoftware.myrealstate.R;
import com.riossoftware.myrealstate.listView.Propiedad;

public class DatosActivity extends AppCompatActivity {

    TextView tipo;

    FirebaseAuth auth;
    DatabaseReference db,propiedades;
    String id,tag;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        builder = new AlertDialog.Builder(this);


        tag=getIntent().getStringExtra("tag");

        auth = FirebaseAuth.getInstance();
        id=auth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference("Users").child(id);
        propiedades=db.child("PROPIEDADES").child(tag);

    }

    public void getData(){
        propiedades.child(tag).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<Propiedad> user=new GenericTypeIndicator<Propiedad>() {};
                Propiedad propiedad=snapshot.getValue(user);
                System.out.println("Data: "+propiedad);
                //txtValor.setText(propiedad.getValor());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




}