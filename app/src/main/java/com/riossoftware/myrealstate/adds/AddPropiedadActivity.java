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



    TextView txtTag,txtNombre,txtTelefono,txtCanon,txtCanonP,txtFecha,txtDireccion,txtFMI,txtPredial,txtFechaPredial,txtAvaluo,txtArriendosAtrasados;
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
        txtCanonP=(TextView)findViewById(R.id.txtCanonP);
        txtFecha=(TextView)findViewById(R.id.txtFecha);
        txtDireccion=(TextView)findViewById(R.id.txtAdress);
        txtFMI=(TextView)findViewById(R.id.txtFMI);
        txtPredial=(TextView)findViewById(R.id.txtImpuesto);
        txtFechaPredial=(TextView)findViewById(R.id.txtFechaIP);
        txtArriendosAtrasados=(TextView)findViewById(R.id.txtPagosAtrasados);
        txtAvaluo=(TextView)findViewById(R.id.txtAvaluo);


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
        txtFechaPredial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog2();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre,telefono,valor,arriendosAtrasados;
                String fecha;
                if(isRent){
                    nombre=txtNombre.getText().toString();
                    telefono=txtTelefono.getText().toString();
                    fecha=txtFecha.getText().toString();
                    valor=txtCanon.getText().toString();
                    arriendosAtrasados=txtArriendosAtrasados.getText().toString();
                }else{
                    nombre="No arrendada";
                    telefono="-";
                    fecha="-";
                    valor=txtCanonP.getText().toString();
                    arriendosAtrasados="-" ;
                }
                guardarData(txtTag.getText().toString(),txtFMI.getText().toString(),txtAvaluo.getText().toString(),txtPredial.getText().toString(),txtFechaPredial.getText().toString()
                        ,txtDireccion.getText().toString(),isRent,nombre,telefono,valor,fecha,arriendosAtrasados);


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
                txtFechaPredial.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    private void guardarData(String tag,String fmi,String avaluo,String impuesto,String fechaImpuesto, String direccion, boolean isRent, String nombre, String telefono,String canon, String fecha,String pagosAtrasados){

        if(!tag.isEmpty() && !direccion.isEmpty() && !nombre.isEmpty() &&
                !telefono.isEmpty() && !canon.isEmpty() && !fecha.isEmpty() && !avaluo.isEmpty() &&
        !fmi.isEmpty() && !impuesto.isEmpty() && !fechaImpuesto.isEmpty() && !pagosAtrasados.isEmpty()){
            Map<String,Object> data=new HashMap<>();

            data.put("tag",tag);
            data.put("tipo","casa");
            data.put("direccion",direccion);
            data.put("fmi",fmi);
            data.put("avaluo",avaluo);
            data.put("valor_predial",impuesto);
            data.put("fecha_vencimiento_predial",fechaImpuesto);
            data.put("rentada",isRent);
            data.put("nombre",nombre);
            data.put("telefono",telefono);
            data.put("valor",canon);
            data.put("fecha",fecha);
            data.put("arriendos_atrasados",pagosAtrasados);


            propiedades.child(tag).child("DATA").setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()) {

                        Toast.makeText(AddPropiedadActivity.this,"Datos subidos",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(AddPropiedadActivity.this, ProfileMainActivity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(AddPropiedadActivity.this,"Los datos NO puedieron ser subidos",Toast.LENGTH_LONG).show();
                    }

                }
            });

        }else{
            Toast.makeText(AddPropiedadActivity.this,"Por favor complete todos los datos",Toast.LENGTH_LONG).show();
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