package com.riossoftware.myrealstate.adds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.riossoftware.myrealstate.LoginActivity;
import com.riossoftware.myrealstate.ProfileMainActivity;
import com.riossoftware.myrealstate.R;

import java.util.HashMap;
import java.util.Map;

public class AddPagareActivity extends AppCompatActivity {

    TextView txtTag,txtValor,txtFecha,txtFechaVenc,txtTiempo,txtNombre,txtTelefono,txtInteres,txtPagosAtrasados;
    Button btnGuardar;

    FirebaseAuth auth;
    DatabaseReference db,propiedades;
    String id,tagHipoteca;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pagare);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        builder = new AlertDialog.Builder(this);

        auth = FirebaseAuth.getInstance();
        id=auth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference("Users").child(id);
        propiedades=db.child("PROPIEDADES");


        tagHipoteca=getIntent().getStringExtra("tag");


        txtTag=(TextView) findViewById(R.id.txtTag);
        txtValor=(TextView)findViewById(R.id.txtValor);
        txtInteres=(TextView)findViewById(R.id.txtInteres);
        txtFecha=(TextView)findViewById(R.id.txtFechaPrestamo);
        txtFechaVenc=(TextView)findViewById(R.id.txtFechaVencimiento);
        txtTiempo=(TextView)findViewById(R.id.txtTiempo);
        txtNombre=(TextView)findViewById(R.id.txtName);
        txtTelefono=(TextView) findViewById(R.id.txtPhone);
        txtPagosAtrasados=(TextView) findViewById(R.id.txtPagosAtrasados);

        btnGuardar=(Button) findViewById(R.id.btnAdd);

        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        txtFechaVenc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog2();
            }
        });

        if(!tagHipoteca.isEmpty() || tagHipoteca!=null){
            txtTag.setText(tagHipoteca);
            txtTag.setFocusable(false);
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                guardarData(txtTag.getText().toString(),txtValor.getText().toString(), txtInteres.getText().toString(),
                        txtFecha.getText().toString(),
                        txtFechaVenc.getText().toString(),txtTiempo.getText().toString(),
                        txtNombre.getText().toString(),txtTelefono.getText().toString(),txtPagosAtrasados.getText().toString(),tagHipoteca.isEmpty());

            }
        });


    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                txtFecha.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    private void showDatePickerDialog2() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                txtFechaVenc.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    private void guardarData(String tag, String valor,String interes, String fecha, String fechaVenc,
                             String tiempo,String nombre, String telefono,String pagosAtrasados,boolean intent){

        if(!tag.isEmpty() && !valor.isEmpty() && !interes.isEmpty() && !fechaVenc.isEmpty() &&
                !fecha.isEmpty() && !tiempo.isEmpty() && !nombre.isEmpty() && !telefono.isEmpty() && !pagosAtrasados.isEmpty()){
            Map<String,Object> data=new HashMap<>();

            data.put("tag",tag);
            data.put("tipo","pagaré");
            data.put("valor",valor);
            data.put("interes",interes);
            data.put("fechaprestamo",fecha);
            data.put("fechavencimiento",fechaVenc);
            data.put("tiempo",tiempo+" años");
            data.put("nombre",nombre);
            data.put("telefono",telefono);
            data.put("pagos_atrasados",pagosAtrasados);

            if(intent) {
                propiedades.child(tag).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(AddPagareActivity.this, "Datos subidos", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddPagareActivity.this, ProfileMainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(AddPagareActivity.this, "Los datos NO puedieron ser subidos", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }else{
                data.put("hipoteca",tag);

                propiedades.child(tagHipoteca).child("PAGARES").child(tag).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(AddPagareActivity.this, "Datos subidos", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddPagareActivity.this, ProfileMainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(AddPagareActivity.this, "Los datos NO puedieron ser subidos", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        }else{
            Toast.makeText(AddPagareActivity.this,"Por favor complete todos los datos",Toast.LENGTH_LONG).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.SingOut:
                builder.setMessage("¿Estas seguro de cerrar sesión?")
                        .setCancelable(true)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent=new Intent(AddPagareActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("My Real State");
                alert.setIcon(R.mipmap.ic_launcher);
                alert.show();
                break;
        }
        return true;
    }

}