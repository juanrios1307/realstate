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
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.riossoftware.myrealstate.LoginActivity;
import com.riossoftware.myrealstate.R;
import com.riossoftware.myrealstate.listView.Propiedad;
import com.riossoftware.myrealstate.pojo.Casa;
import com.riossoftware.myrealstate.pojo.Garantia;
import com.riossoftware.myrealstate.pojo.Hipoteca;
import com.riossoftware.myrealstate.pojo.Pagare;

public class DatosActivity extends AppCompatActivity {

    TextView txtT,ltipo,ltag,l1,l2,l3,l4,l5,l6,l7,l8,l9,l10;
    Button btnAddPago,btnPagosAtrasados,btnHistory,btnGasto,btnProcesoJudicial,btnAux;

    FirebaseAuth auth;
    DatabaseReference db,propiedades;
    String id,tag;
    boolean tipo;

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

        declareAndSet();
        getData();

    }



    public void getData(){

        propiedades.child(tag).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<Propiedad> user = new GenericTypeIndicator<Propiedad>() {};
                Propiedad propiedad = snapshot.getValue(user);

                tipo=propiedad.getTipo().equals("casa")?true:false;

                if(tipo)
                    btnAux.setText("Mantenimientos");
                else
                    btnAux.setText("Pago Total");


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

    public void declareAndSet(){
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
        l9=(TextView)findViewById(R.id.txt9);
        l10=(TextView)findViewById(R.id.txt10);

        btnAddPago=(Button) findViewById(R.id.btnAddPago);
        btnPagosAtrasados=(Button) findViewById(R.id.btnPagosAtrasados);
        btnHistory=(Button) findViewById(R.id.btnHistory);
        btnGasto=(Button) findViewById(R.id.btnAddGasto);
        btnProcesoJudicial=(Button) findViewById(R.id.btnProcesoJudicial);
        btnAux=(Button) findViewById(R.id.btnAux);


        btnAddPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DatosActivity.this,AddPagoActivity.class);
                i.putExtra("tag",tag);
                startActivity(i);
                //finish();
            }
        });

        btnPagosAtrasados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DatosActivity.this,PagosAtrasadosActivity.class);
                i.putExtra("tag",tag);
                startActivity(i);
                //finish();
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DatosActivity.this,HistoricoActivity.class);
                i.putExtra("tag",tag);
                startActivity(i);
                //finish();
            }
        });

        btnGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DatosActivity.this,GastoActivity.class);
                i.putExtra("tag",tag);
                startActivity(i);
                //finish();
            }
        });

        btnProcesoJudicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DatosActivity.this,ProcesoJudicialActivity.class);
                i.putExtra("tag",tag);
                startActivity(i);
                //finish();
            }
        });

        btnAux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tipo){
                    System.out.println("true");
                    Intent i=new Intent(DatosActivity.this,MantenimientosActivity.class);
                    i.putExtra("tag",tag);
                    startActivity(i);
                }else{
                    System.out.println("true 1");
                    Intent i=new Intent(DatosActivity.this,PagoTotalActivity.class);
                    i.putExtra("tag",tag);
                    startActivity(i);
                }

            }
        });
    }

    private void setCasa(Casa c){

        String rent;
        if(c.isRentada()){
            rent="Rentada";
            l8.setVisibility(View.VISIBLE);
            l9.setVisibility(View.VISIBLE);
            l10.setVisibility(View.VISIBLE);
            l8.setText("Nombre: "+c.getNombre());
            l9.setText("Telefono: "+c.getTelefono());
            l10.setText(Integer.parseInt(c.getArriendos_atrasados())!=0?"Pagos Atrasados: "+c.getArriendos_atrasados():"Se encuentra al dia con el canon");
        }else{
            rent="No rentada";
            l8.setVisibility(View.INVISIBLE);
            l9.setVisibility(View.INVISIBLE);
            l10.setVisibility(View.INVISIBLE);
        }

        l1.setText("Dirección: "+c.getDireccion());
        l2.setText("FMI: "+c.getFmi());
        l3.setText("Avaluo: "+c.getAvaluo());
        l4.setText("Predial ultimo año: "+c.getValor_predial());
        l5.setText("Fecha vencimiento predial: "+c.getFecha_vencimiento_predial());
        l6.setText(rent);
        l7.setText("Canon : "+c.getValor());

    }

    private  void setHipoteca(Hipoteca h){
        l1.setText("Avaluo propiedad: "+h.getAvaluo());
        l2.setText("Dirección: "+h.getDireccion());
        l3.setText("Fecha prestamo: "+h.getFecha());
        l4.setText("Fecha vencimiento: "+h.getFecha_vencimiento());
        l5.setText("Interes: $"+h.getInteres());
        l6.setText("Nombre: "+h.getNombre());
        l7.setText("Telefono: "+h.getTelefono());
        l8.setText("Tiempo prestamo: "+h.getTiempo());
        l9.setText("Valor prestamo: "+h.getValor());
        l10.setText(Integer.parseInt(h.getPagos_atrasados())!=0?"Pagos Atrasados: "+h.getPagos_atrasados():"Se encuentra al dia con el interes");
    }

    private void setPagare(Pagare p){
        l1.setText("Fecha prestamo: "+p.getFechaprestamo());
        l2.setText("Fecha vencimiento: "+p.getFechavencimiento());
        l3.setText("Interes : $"+p.getInteres());
        l4.setText("Nombre: "+p.getNombre());
        l5.setText("Telefono: "+p.getTelefono());
        l6.setText("Tiempo prestamo: "+p.getTiempo());
        l7.setText("Valor prestamo: "+p.getValor());
        l7.setText("Valor prestamo: "+p.getValor());
        l8.setText(Integer.parseInt(p.getPagos_atrasados())!=0?"Pagos Atrasados: "+p.getPagos_atrasados():"Se encuentra al dia con el interes");
        if(p.getHipoteca() !=null){
            l9.setText("Hipoteca: "+p.getHipoteca());
        }else{
            l9.setVisibility(View.INVISIBLE);
        }
        l10.setVisibility(View.INVISIBLE);
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
        l9.setText(Integer.parseInt(g.getPagos_atrasados())!=0?"Pagos Atrasados: "+g.getPagos_atrasados():"Se encuentra al dia con el interes");
        l10.setVisibility(View.INVISIBLE);
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