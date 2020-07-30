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
import android.widget.EditText;
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
import com.riossoftware.myrealstate.adds.AddPropiedadActivity;
import com.riossoftware.myrealstate.adds.DatePickerFragment;
import com.riossoftware.myrealstate.pojo.Proceso_Judicial;

import java.util.HashMap;
import java.util.Map;

public class ProcesoJudicialActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference db,propiedad;
    String id,tag;
    boolean process;

    AlertDialog.Builder builder;


    View openProcess,noProcess,createProcess;

    Button btnOpen,btnGuardar;
    TextView txtRadicado,txtFecha,txtName,txtID,txtTelefono,txtJuzgado,txtDemandante;
    EditText etRadicado,etFecha,etName,etID,etTelefono,etJuzgado,etDemandante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceso_judicial);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        builder = new AlertDialog.Builder(this);

        tag=getIntent().getStringExtra("tag");

        auth = FirebaseAuth.getInstance();
        id=auth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference("Users").child(id);
        propiedad=db.child("PROPIEDADES").child(tag);

        openProcess=(View)findViewById(R.id.process_open);
        noProcess=(View)findViewById(R.id.no_process);
        createProcess=(View)findViewById(R.id.create_process);

        process();
        declare();


    }

    private void getData(){
        propiedad.child("PROCESO_JUDICIAL").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<Proceso_Judicial> user = new GenericTypeIndicator<Proceso_Judicial>() {};
                Proceso_Judicial proceso = snapshot.getValue(user);

                txtRadicado.setText("Radicado: "+proceso.getRadicado());
                txtDemandante.setText("Demandante: "+proceso.getDemandante());
                txtFecha.setText("Fecha: "+proceso.getFecha());
                txtID.setText("CC: "+proceso.getCc());
                txtJuzgado.setText("Juzgado: "+proceso.getJuzgado());
                txtName.setText("Damandado: "+proceso.getDemandado());
                txtTelefono.setText("Telefono: "+proceso.getTelefono());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void declare(){
        btnOpen=(Button) findViewById(R.id.btnAddProcess);

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProcess.setVisibility(View.VISIBLE);
                openProcess.setVisibility(View.INVISIBLE);
                noProcess.setVisibility(View.INVISIBLE);
            }
        });

        txtRadicado=(TextView) findViewById(R.id.txtRadicado);
        txtDemandante=(TextView) findViewById(R.id.txtDemandante);
        txtFecha=(TextView) findViewById(R.id.txtFecha);
        txtID=(TextView) findViewById(R.id.txtIDDemandado);
        txtJuzgado=(TextView) findViewById(R.id.txtJuzgado);
        txtName=(TextView) findViewById(R.id.txtDemandado);
        txtTelefono=(TextView) findViewById(R.id.txtTelefono);

        btnGuardar=(Button) findViewById(R.id.btnGuardar);
        etDemandante=(EditText) findViewById(R.id.etDemandante);
        etFecha=(EditText) findViewById(R.id.etFecha);
        etID=(EditText) findViewById(R.id.etCedulaD);
        etJuzgado=(EditText) findViewById(R.id.etJuzgado);
        etName=(EditText) findViewById(R.id.etDemandado);
        etRadicado=(EditText) findViewById(R.id.etRadicado);
        etTelefono=(EditText) findViewById(R.id.etTelefono);

        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData(etRadicado.getText().toString(),etDemandante.getText().toString(),etFecha.getText().toString()
                        ,etID.getText().toString(),etJuzgado.getText().toString(),etName.getText().toString(),etTelefono.getText().toString());
            }
        });
    }

    private void addData(String radicado,String demandante,String fecha,String cc,String juzgado,String nombre,String telefono){
        if(!(radicado.isEmpty() || demandante.isEmpty() || fecha.isEmpty() || cc.isEmpty() || juzgado.isEmpty()
                || nombre.isEmpty() || telefono.isEmpty())){

            Map<String,Object> data=new HashMap<>();
            data.put("radicado",radicado);
            data.put("demandante",demandante);
            data.put("fecha",fecha);
            data.put("cc",cc);
            data.put("juzgado",juzgado);
            data.put("demandado",nombre);
            data.put("telefono",telefono);

            propiedad.child("PROCESO_JUDICIAL").setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {

                        Toast.makeText(ProcesoJudicialActivity.this,"Datos subidos",Toast.LENGTH_LONG).show();
                        createProcess.setVisibility(View.INVISIBLE);
                        openProcess.setVisibility(View.VISIBLE);
                    }else {
                        Toast.makeText(ProcesoJudicialActivity.this,"Los datos NO puedieron ser subidos",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }else{
            Toast.makeText(ProcesoJudicialActivity.this,"Por favor complete todos los datos",Toast.LENGTH_SHORT).show();
        }
    }


    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etFecha.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    private boolean process(){
        propiedad.child("PROCESO_JUDICIAL").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.exists()){
                   openProcess.setVisibility(View.VISIBLE);
                   noProcess.setVisibility(View.INVISIBLE);

                   getData();

               }else{
                   openProcess.setVisibility(View.INVISIBLE);
                   noProcess.setVisibility(View.VISIBLE);
               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return process;
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
                                Intent intent=new Intent(ProcesoJudicialActivity.this, LoginActivity.class);
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