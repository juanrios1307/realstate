package com.riossoftware.myrealstate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import com.riossoftware.myrealstate.actions.*;
import com.riossoftware.myrealstate.adds.*;
import com.riossoftware.myrealstate.listView.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileMainActivity extends AppCompatActivity {


    private TextView txtWelcome,txtCasa,txtHipoteca, txtPagare,txtGarantia;
    private ListView listView;
    private FloatingActionButton btnAdd, btnAddPropiedad, btnAddHipoteca, btnAddPagare, btnAddGarantia;
    private View addPropiedad,addHipoteca,addPagare,addGarantia;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;


    ArrayList<Propiedad> propers=new ArrayList<Propiedad>();
    Propiedad propiedadAux;

    MenuItem pagoTotal,mantenimiento,gastos;

    String token="";
    FirebaseAuth auth;
    DatabaseReference db;
    String id;
    boolean isFABOpen=false;

    //instance fields

    //CustomAdapter adapter;

    AlertDialog.Builder builder;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_main);

        txtWelcome = (TextView) findViewById(R.id.lblWelcome);
        listView= (ListView) findViewById(R.id.listView);
        registerForContextMenu(listView);
        listView.setLongClickable(true);

        btnAdd =(FloatingActionButton)findViewById(R.id.btnAdd);


        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        id = auth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference("Users").child(id);

        firebasehelper();
        builder = new AlertDialog.Builder(this);

        declareAndSet();
        closeFABMenu();

    }


    private void showFABMenu() {
        isFABOpen = true;
       /* addPropiedad.animate().translationY(-getResources().getDimension(R.dimen.standard_60));
        addHipoteca.animate().translationY(-getResources().getDimension(R.dimen.standard_120));
        addPagare.animate().translationY(-getResources().getDimension(R.dimen.standard_180));
        addGarantia.animate().translationY(-getResources().getDimension(R.dimen.standard_240));*/

        addPropiedad.setVisibility(View.VISIBLE);
        addHipoteca.setVisibility(View.VISIBLE);
        addPagare.setVisibility(View.VISIBLE);
        addGarantia.setVisibility(View.VISIBLE);

        txtCasa.setVisibility(View.VISIBLE);
        txtHipoteca.setVisibility(View.VISIBLE);
        txtPagare.setVisibility(View.VISIBLE);
        txtGarantia.setVisibility(View.VISIBLE);


        btnAddPropiedad.startAnimation(fab_open);
        btnAddHipoteca.startAnimation(fab_open);
        btnAddPagare.startAnimation(fab_open);
        btnAddGarantia.startAnimation(fab_open);
        btnAdd.startAnimation(fab_clock);

        btnAddPropiedad.setClickable(true);
        btnAddHipoteca.setClickable(true);
        btnAddPagare.setClickable(true);
        btnAddGarantia.setClickable(true);
    }

    private void closeFABMenu(){
        isFABOpen=false;

        addPropiedad.setVisibility(View.INVISIBLE);
        addHipoteca.setVisibility(View.INVISIBLE);
        addPagare.setVisibility(View.INVISIBLE);
        addGarantia.setVisibility(View.INVISIBLE);


        txtCasa.setVisibility(View.INVISIBLE);
        txtHipoteca.setVisibility(View.INVISIBLE);
        txtPagare.setVisibility(View.INVISIBLE);
        txtGarantia.setVisibility(View.INVISIBLE);


        btnAddPropiedad.startAnimation(fab_close);
        btnAddHipoteca.startAnimation(fab_close);
        btnAddPagare.startAnimation(fab_close);
        btnAddGarantia.startAnimation(fab_close);
        btnAdd.startAnimation(fab_anticlock);

        btnAddPropiedad.setClickable(false);
        btnAddHipoteca.setClickable(false);
        btnAddPagare.setClickable(false);
        btnAddGarantia.setClickable(false);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        android.view.MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_listview, menu);



    }

    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent intent;
        Propiedad pAux = (Propiedad) listView.getItemAtPosition(info.position);
        String tag=pAux.getTag();


        switch (item.getItemId()) {
            case R.id.Datos:
                intent=new Intent(ProfileMainActivity.this, DatosActivity.class);
                intent.putExtra("tag",tag);
                startActivity(intent);

                return true;
            case R.id.Pago:
                intent=new Intent(ProfileMainActivity.this, AddPagoActivity.class);
                intent.putExtra("tag",tag);
                startActivity(intent);

                return true;
            case R.id.Atraso:
                intent=new Intent(ProfileMainActivity.this, PagosAtrasadosActivity.class);
                intent.putExtra("tag",tag);
                startActivity(intent);

                return true;
            case R.id.ProcesoJudicial:
                intent=new Intent(ProfileMainActivity.this, ProcesoJudicialActivity.class);
                intent.putExtra("tag",tag);
                startActivity(intent);

                return true;
            default:
                return super.onContextItemSelected((android.view.MenuItem) item);
        }
    }

    private void firebasehelper(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Token", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();

                    }
                });

        //read firebase database (real time)
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<UserPojo> user=new GenericTypeIndicator<UserPojo>() {};
                UserPojo userPojo=dataSnapshot.getValue(user);

                String text=getString(R.string.welcome_messages,userPojo.getName());

                //mostramos en el textview
                txtWelcome.setText(text);

                db.child("PROPIEDADES").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        propers.clear();
                        if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                Propiedad proper = ds.getValue(Propiedad.class);
                                propers.add(proper);

                                if(proper.getTipo().equals("hipoteca")){
                                    db.child("PROPIEDADES").child(proper.getTag()).child("PAGARES").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            System.out.println("HOLA: "+snapshot);
                                            if (snapshot.exists() && snapshot.getChildrenCount() > 0) {

                                                for (DataSnapshot ds : snapshot.getChildren()) {
                                                    Propiedad proper1 = ds.getValue(Propiedad.class);
                                                    System.out.println("HOLA 2: "+proper1);
                                                    propers.add(proper1);
                                                    System.out.println("HOLA 3: "+propers);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(ProfileMainActivity.this, "ERROR " + error.getMessage(), Toast.LENGTH_LONG).show();

                                        }
                                    });

                                    System.out.println("HOLA 4"+propers);
                                }

                            }



                            CustomAdapter adapter = new CustomAdapter(ProfileMainActivity.this, propers);
                            listView.setAdapter(adapter);

                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    listView.smoothScrollToPosition(propers.size());
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(ProfileMainActivity.this, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void declareAndSet(){
        //Floating action button
        btnAdd =(FloatingActionButton)findViewById(R.id.btnAdd);
        btnAddPropiedad =(FloatingActionButton) findViewById(R.id.btnAddPropiedad);
        btnAddHipoteca =(FloatingActionButton) findViewById(R.id.btnAddHipoteca);
        btnAddPagare =(FloatingActionButton) findViewById(R.id.btnAddPagare);
        btnAddGarantia =(FloatingActionButton) findViewById(R.id.btnAddGarantia);


        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_anticlock);


        addPropiedad= (View)findViewById(R.id.addPropiedad);
        addHipoteca= (View)findViewById(R.id.addHipoteca);
        addPagare=(View)findViewById(R.id.addPagare);
        addGarantia=(View)findViewById(R.id.addGarantia);

        txtCasa=(TextView)findViewById(R.id.txtCasa);
        txtHipoteca=(TextView)findViewById(R.id.txtHipoteca);
        txtPagare =(TextView)findViewById(R.id.txtPagare);
        txtGarantia=(TextView)findViewById(R.id.txtGarantia);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();


                }
            }
        });

        btnAddPropiedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileMainActivity.this, AddPropiedadActivity.class);
                startActivity(intent);

            }
        });

        btnAddHipoteca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileMainActivity.this, AddHipotecaActivity.class);
                startActivity(intent);

            }
        });

        btnAddPagare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileMainActivity.this, AddPagareActivity.class);
                startActivity(intent);

            }
        });

        btnAddGarantia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileMainActivity.this, AddGarantiaActivity.class);
                startActivity(intent);

            }
        });
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
                                Intent intent=new Intent(ProfileMainActivity.this,LoginActivity.class);
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