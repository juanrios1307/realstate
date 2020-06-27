package com.riossoftware.myrealstate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

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
import com.riossoftware.myrealstate.listView.FirebaseHelper;
import com.riossoftware.myrealstate.listView.Propiedad;

import java.util.ArrayList;

public class ProfileMainActivity extends AppCompatActivity {


    private TextView txtWelcome,txtCasa,txtHipoteca, txtPagare,txtGarantia;
    private ListView listView;
    private FloatingActionButton btnAdd, btnAddPropiedad, btnAddHipoteca, btnAddPagare, btnAddGarantia;
    private View addPropiedad,addHipoteca,addPagare,addGarantia;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;


    ArrayList<Propiedad> propers=new ArrayList<Propiedad>();
    ArrayList<String> tagsPropiedades=new ArrayList<String>();

    String token="";
    FirebaseAuth auth;
    DatabaseReference db;
    String id;
    boolean isFABOpen=false;

    //instance fields
    FirebaseHelper helper;
    //CustomAdapter adapter;

    AlertDialog.Builder builder;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_main);

        txtWelcome = (TextView) findViewById(R.id.lblWelcome);
        listView= (ListView) findViewById(R.id.listView);
        btnAdd =(FloatingActionButton)findViewById(R.id.btnAdd);


        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        id = auth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference("Users").child(id);


        //Get token
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
                        System.out.println("TOPIC token: " + token);

                        // Log and toast
                        String msg = "Token: " + token;
                        Log.d("Token", msg);
                    }
                });

        //read firebase database (real time)
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<UserPojo> user=new GenericTypeIndicator<UserPojo>() {};
                UserPojo userPojo=dataSnapshot.getValue(user);



                System.out.println(userPojo);
                String text=getString(R.string.welcome_messages,userPojo.getName());

                //mostramos en el textview
               txtWelcome.setText(text);

               db.child("PROPIEDADES").addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       for (DataSnapshot prop: snapshot.getChildren()) {
                           tagsPropiedades.add ((String) prop.child("tag").getValue());

                           GenericTypeIndicator<Propiedad> user=new GenericTypeIndicator<Propiedad>() {};
                           propers.add(prop.getValue(user));


                       }

                       System.out.println("props tags: "+tagsPropiedades);
                       System.out.println("props al: "+propers);;
                       //propers=(HashMap<String, Propiedad>) snapshot.getValue(user);


                       System.out.println("props 2:  "+propers);
                       //System.out.println("props 2: "+propers.get("sauces"));
                       System.out.println("props"+snapshot.getValue());
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                       Log.e("ERROR FIREBASE", error.getMessage());
                   }
               });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ERROR FIREBASE", databaseError.getMessage());
            }
        });

        //Adaptador

        helper = new FirebaseHelper(db, this, listView);

        builder = new AlertDialog.Builder(this);

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

        closeFABMenu();

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
     /*   addPropiedad.animate().translationY(0);
        addHipoteca.animate().translationY(0);
        addPagare.animate().translationY(0);
        addGarantia.animate().translationY(0);*/

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


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
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