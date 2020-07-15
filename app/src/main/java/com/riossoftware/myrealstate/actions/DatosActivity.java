package com.riossoftware.myrealstate.actions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
import com.riossoftware.myrealstate.listView.Propiedad;
import com.riossoftware.myrealstate.pojo.Casa;
import com.riossoftware.myrealstate.pojo.Garantia;
import com.riossoftware.myrealstate.pojo.Hipoteca;
import com.riossoftware.myrealstate.pojo.Pagare;

public class DatosActivity extends AppCompatActivity {

    TextView txtT,ltipo,ltag,l1,l2,l3,l4,l5,l6,l7,l8;

    FirebaseAuth auth;
    DatabaseReference db,propiedades;
    String id,tag;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        builder = new AlertDialog.Builder(this);


        tag=getIntent().getStringExtra("tag");

        auth = FirebaseAuth.getInstance();
        id=auth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference("Users").child(id);
        propiedades=db.child("PROPIEDADES");

        txtT=(TextView)findViewById(R.id.txtData);
        ltag=(TextView)findViewById(R.id.txtTag);
        ltipo=(TextView)findViewById(R.id.txtTipo);
        ltag=(TextView)findViewById(R.id.txtTag);
        l1=(TextView)findViewById(R.id.txt1);
        l2=(TextView)findViewById(R.id.txt2);
        l3=(TextView)findViewById(R.id.txt3);
        l4=(TextView)findViewById(R.id.txt4);
        l5=(TextView)findViewById(R.id.txt5);
        l6=(TextView)findViewById(R.id.txt6);
        l7=(TextView)findViewById(R.id.txt7);
        l8=(TextView)findViewById(R.id.txt8);

        getData();

    }

    public void getData(){

        propiedades.child(tag).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<Propiedad> user = new GenericTypeIndicator<Propiedad>() {};
                Propiedad propiedad = snapshot.getValue(user);


                txtT.setText(propiedad.getTag());
                ltag.setText("Tag: "+propiedad.getTag());
                ltipo.setText("Tipo: "+propiedad.getTipo());

                switch (propiedad.getTipo()){
                    case "casa":
                        GenericTypeIndicator<Casa> getCasa = new GenericTypeIndicator<Casa>() {};
                        Casa casa=snapshot.getValue(getCasa);
                        setCasa(casa);
                        break;
                    case "hipoteca":
                        GenericTypeIndicator<Hipoteca> getHipoteca = new GenericTypeIndicator<Hipoteca>() {};
                        Hipoteca hipoteca=snapshot.getValue(getHipoteca);
                        setHipoteca(hipoteca);
                        break;
                    case "pagaré":
                        GenericTypeIndicator<Pagare> getPagare = new GenericTypeIndicator<Pagare>() {};
                        Pagare pagare=snapshot.getValue(getPagare);
                        setPagare(pagare);
                        break;
                    case "garantia":
                        GenericTypeIndicator<Garantia> getGarantia = new GenericTypeIndicator<Garantia>() {};
                        Garantia garantia=snapshot.getValue(getGarantia);
                        setGarantia(garantia);
                        break;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setCasa(Casa c){
        l7.setVisibility(View.INVISIBLE);
        l8.setVisibility(View.INVISIBLE);

        String rent;
        if(c.isRentada()){
            rent="Rentada";
            l5.setVisibility(View.VISIBLE);
            l6.setVisibility(View.VISIBLE);
            l5.setText("Nombre: "+c.getNombre());
            l6.setText("Telefono: "+c.getTelefono());
        }else{
            rent="No rentada";
            l5.setVisibility(View.INVISIBLE);
            l6.setVisibility(View.INVISIBLE);
        }

        l1.setText("Dirección: "+c.getDireccion());
        l2.setText("Fecha arrendamiento: "+c.getFecha());
        l3.setText(rent);
        l4.setText("Canon: $"+c.getValor());

    }

    private  void setHipoteca(Hipoteca h){
        l1.setText("Avaluo propiedad: "+h.getAvaluo());
        l2.setText("Dirección: "+h.getDireccion());
        l3.setText("Fecha prestamo: "+h.getFecha());
        l4.setText("Interes: $"+h.getInteres());
        l5.setText("Nombre: "+h.getNombre());
        l6.setText("Telefono: "+h.getTelefono());
        l7.setText("Tiempo prestamo: "+h.getTiempo());
        l8.setText("Valor prestamo: "+h.getValor());
    }

    private void setPagare(Pagare p){
        l1.setText("Fecha prestamo: "+p.getFechaprestamo());
        l2.setText("Fecha vencimiento: "+p.getFechavencimiento());
        l3.setText("Interes : $"+p.getInteres());
        l4.setText("Nombre: "+p.getNombre());
        l5.setText("Telefono: "+p.getTelefono());
        l6.setText("Tiempo prestamo: "+p.getTiempo());
        l7.setText("Valor prestamo: "+p.getValor());
        l8.setVisibility(View.INVISIBLE);
    }

    private  void setGarantia(Garantia g){
        l1.setText("Fecha prestamo: "+g.getFechaprestamo());
        l2.setText("Fecha vencimiento: "+g.getFechavencimiento());
        l3.setText("Interes: $"+g.getInteres());
        l4.setText("Nombre: "+g.getNombre());
        l5.setText("Telefono: "+g.getTelefono());
        l6.setText("Tiempo prestamo: "+g.getTiempo());
        l7.setText("Tipo de garantia: "+g.getTipoGarantia());
        l8.setText("Valor prestamo: "+g.getValor());
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
                                Intent intent=new Intent(DatosActivity.this, LoginActivity.class);
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