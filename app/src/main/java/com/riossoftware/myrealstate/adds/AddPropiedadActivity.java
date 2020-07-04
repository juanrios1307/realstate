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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class AddPropiedadActivity extends AppCompatActivity{



    TextView txtTag,txtNombre,txtTelefono,txtCanon,txtFecha,txtDireccion;
    RadioGroup rgRent;
    RadioButton rbSi,rbNo;
    View rent,noRent;
    Button btnGuardar;


    boolean isRent=true;

    FirebaseAuth auth;
    DatabaseReference db,propiedades;
    String id;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_propiedad);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        builder = new AlertDialog.Builder(this);

        auth = FirebaseAuth.getInstance();
        id=auth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference("Users").child(id);
        propiedades=db.child("PROPIEDADES");


        rgRent=(RadioGroup)findViewById(R.id.rgRent);
        rbSi=(RadioButton)findViewById(R.id.rbSi);
        rbNo=(RadioButton)findViewById(R.id.rbNo);
        btnGuardar=(Button)findViewById(R.id.btnAdd);

        txtTag=(TextView) findViewById(R.id.txtTag);
        txtNombre=(TextView)findViewById(R.id.txtName);
        txtTelefono=(TextView) findViewById(R.id.txtPhone);
        txtCanon=(TextView)findViewById(R.id.txtCanon);
        txtFecha=(TextView)findViewById(R.id.txtFecha);
        txtDireccion=(TextView)findViewById(R.id.txtAdress);


        rent= (View) findViewById(R.id.rent);
        noRent= (View) findViewById(R.id.no_rent);



        rgRent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int check) {
                if(check==R.id.rbSi){
                    isRent=true;
                    rent.setVisibility(View.VISIBLE);
                    noRent.setVisibility(View.INVISIBLE);
                }else if(check==R.id.rbNo){
                    isRent=false;
                    noRent.setVisibility(View.VISIBLE);
                    rent.setVisibility(View.INVISIBLE);

                }
            }
        });


        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre,telefono;
                String fecha;
                if(isRent){
                    nombre=txtNombre.getText().toString();
                    telefono=txtTelefono.getText().toString();
                    fecha=txtFecha.getText().toString();
                }else{
                    nombre="No arrendada";
                    telefono="";
                    fecha=null;
                }

                guardarData(txtTag.getText().toString(),txtDireccion.getText().toString(),isRent,nombre,telefono,txtCanon.getText().toString(),fecha);
                Intent intent=new Intent(AddPropiedadActivity.this, ProfileMainActivity.class);
                startActivity(intent);
                finish();
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

    private void guardarData(String tag, String direccion, boolean isRent, String nombre, String telefono,String canon, String fecha){

        if(!(tag.isEmpty() && direccion.isEmpty() && nombre.isEmpty() && canon.isEmpty())){
            Map<String,Object> data=new HashMap<>();

            data.put("tag",tag);
            data.put("tipo","casa");
            data.put("direccion",direccion);
            data.put("rentada",isRent);
            data.put("nombre",nombre);
            data.put("telefono",telefono);
            data.put("valor",canon);
            data.put("fecha",fecha);


            propiedades.child(tag).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()) {

                        Toast.makeText(AddPropiedadActivity.this,"Datos subidos",Toast.LENGTH_LONG);

                    }else {
                        Toast.makeText(AddPropiedadActivity.this,"Los datos NO puedieron ser subidos",Toast.LENGTH_LONG);
                    }

                }
            });

        }else{
            Toast.makeText(AddPropiedadActivity.this,"Por favor complete todos los datos",Toast.LENGTH_LONG);
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
                                Intent intent=new Intent(AddPropiedadActivity.this, LoginActivity.class);
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