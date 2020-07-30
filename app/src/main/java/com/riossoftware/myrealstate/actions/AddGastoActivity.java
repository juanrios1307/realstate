package com.riossoftware.myrealstate.actions;

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
import com.riossoftware.myrealstate.R;
import com.riossoftware.myrealstate.adds.DatePickerFragment;

import java.util.HashMap;
import java.util.Map;

public class AddGastoActivity extends AppCompatActivity {

    TextView txtValor,txtFecha,txtWelcome,txtDescription;
    Button btnAdd;

    FirebaseAuth auth;
    DatabaseReference db,propiedades;
    String id,tag;

    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gasto);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        builder = new AlertDialog.Builder(this);

        auth = FirebaseAuth.getInstance();
        id=auth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference("Users").child(id);
        propiedades=db.child("PROPIEDADES");

        tag=getIntent().getStringExtra("tag");

        txtFecha=(TextView)findViewById(R.id.txtFecha);
        txtValor=(TextView) findViewById(R.id.txtValor);
        txtWelcome=(TextView)findViewById(R.id.txtWelcome);
        txtDescription= (TextView) findViewById(R.id.txtDescription);
        btnAdd=(Button) findViewById(R.id.btnAdd);

        //mostramos en el textview
        txtWelcome.setText(getString(R.string.add_gasto,tag));

        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                guardarData(txtValor.getText().toString(),txtFecha.getText().toString(),txtDescription.getText().toString());

            }
        });
    }

    public void guardarData(String valor,String fecha,String description){
        if(!valor.isEmpty() && !fecha.isEmpty() && !description.isEmpty()) {
            Map<String,Object> data=new HashMap<>();

            data.put("valor",valor);
            data.put("fecha",fecha);
            data.put("description",description);

            propiedades.child(tag).child("GASTOS").child(description+fecha).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {

                        Toast.makeText(AddGastoActivity.this,"Datos subidos",Toast.LENGTH_LONG).show();
                        Intent intent =new Intent(AddGastoActivity.this, GastoActivity.class);
                        intent.putExtra("tag",tag);
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(AddGastoActivity.this,"Los datos NO puedieron ser subidos",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            Toast.makeText(AddGastoActivity.this,"Complete todos los datos",Toast.LENGTH_LONG).show();
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "-" + (month+1) + "-" + year;
                txtFecha.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
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
                                Intent intent=new Intent(AddGastoActivity.this, LoginActivity.class);
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