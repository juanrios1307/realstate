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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.riossoftware.myrealstate.LoginActivity;
import com.riossoftware.myrealstate.ProfileMainActivity;
import com.riossoftware.myrealstate.R;
import com.riossoftware.myrealstate.UserPojo;
import com.riossoftware.myrealstate.adds.AddHipotecaActivity;
import com.riossoftware.myrealstate.adds.AddPropiedadActivity;
import com.riossoftware.myrealstate.adds.DatePickerFragment;
import com.riossoftware.myrealstate.listView.Propiedad;

import java.util.HashMap;
import java.util.Map;

public class AddPagoActivity extends AppCompatActivity {

    TextView txtValor,txtFecha,txtWelcome;
    RadioGroup rgPago;
    RadioButton rbParcial,rbCompleto;
    Button btnAdd;
    Spinner lugar;

    FirebaseAuth auth;
    DatabaseReference db,propiedades;
    String id,place,tag,valor="";

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pago);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        builder = new AlertDialog.Builder(this);

        auth = FirebaseAuth.getInstance();
        id=auth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference("Users").child(id);
        propiedades=db.child("PROPIEDADES");

        tag=getIntent().getStringExtra("tag");

        rgPago=(RadioGroup)findViewById(R.id.rgPago);
        rbCompleto=(RadioButton)findViewById(R.id.rbCompleto);
        rbParcial=(RadioButton)findViewById(R.id.rbParcial);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        lugar=(Spinner) findViewById(R.id.spinPago);

        txtFecha=(TextView)findViewById(R.id.txtFecha);
        txtValor=(TextView) findViewById(R.id.txtValor);
        txtWelcome=(TextView)findViewById(R.id.txtWelcome);


        //mostramos en el textview
        txtWelcome.setText(getString(R.string.add_pay,tag));

        rgPago.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.rbCompleto){
                    getData();
                }else if(i==R.id.rbParcial){
                    txtValor.setText("");

                }
            }
        });

        lugar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                place=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                System.out.println("Data: "+txtValor.getText().toString()+" 2: "+txtFecha.getText().toString()+" 3: "+place);
                guardarData(txtValor.getText().toString(),txtFecha.getText().toString(),place);
                Intent intent =new Intent(AddPagoActivity.this, ProfileMainActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

    public void getData(){
       propiedades.child(tag).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               GenericTypeIndicator<Propiedad> user=new GenericTypeIndicator<Propiedad>() {};
               Propiedad propiedad=snapshot.getValue(user);
               System.out.println("Data: "+propiedad);
               txtValor.setText(propiedad.getValor());

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

        System.out.println("Data v: "+valor);
    }

    public void guardarData(String valor,String fecha,String lugar){
        if(!(valor.isEmpty() && fecha.isEmpty() && lugar.isEmpty())) {
            Map<String,Object> data=new HashMap<>();

            data.put("valor",valor);
            data.put("fecha",fecha);
            data.put("lugar",lugar);

            propiedades.child(tag).child(fecha).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {

                        Toast.makeText(AddPagoActivity.this,"Datos subidos",Toast.LENGTH_LONG);

                    }else {
                        Toast.makeText(AddPagoActivity.this,"Los datos NO puedieron ser subidos",Toast.LENGTH_LONG);
                    }
                }
            });
        }else{
            Toast.makeText(AddPagoActivity.this,"Complete todos los datos",Toast.LENGTH_LONG);
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
                                Intent intent=new Intent(AddPagoActivity.this, LoginActivity.class);
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