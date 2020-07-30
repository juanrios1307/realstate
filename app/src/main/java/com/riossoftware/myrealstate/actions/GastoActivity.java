package com.riossoftware.myrealstate.actions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.riossoftware.myrealstate.LoginActivity;
import com.riossoftware.myrealstate.R;
import com.riossoftware.myrealstate.listView.CustomAdapterGastos;
import com.riossoftware.myrealstate.listView.CustomAdapterHistory;
import com.riossoftware.myrealstate.listView.Gasto;
import com.riossoftware.myrealstate.listView.Pago;

import java.util.ArrayList;

public class GastoActivity extends AppCompatActivity {

    private ListView listView;
    private TextView lblGastos;
    private FloatingActionButton fabAddGasto;

    ArrayList<Gasto> gastos =new ArrayList<Gasto>();

    String token="",tag;
    FirebaseAuth auth;
    DatabaseReference db;
    String id;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasto);

        lblGastos = (TextView) findViewById(R.id.lblGasto);
        listView= (ListView) findViewById(R.id.listView);
        fabAddGasto= (FloatingActionButton) findViewById(R.id.fabAddGasto);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


        auth = FirebaseAuth.getInstance();
        id = auth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference("Users").child(id);

        tag=getIntent().getStringExtra("tag");

        String text=getString(R.string.historico_gastos,tag);

        //mostramos en el textview
        lblGastos.setText(text);

        firebasehelper();
        builder = new AlertDialog.Builder(this);

        fabAddGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(GastoActivity.this,AddGastoActivity.class);
                i.putExtra("tag",tag);
                startActivity(i);

            }
        });
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
                        System.out.println("TOPIC token: " + token);

                        // Log and toast
                        String msg = "Token: " + token;
                        Log.d("Token", msg);
                    }
                });

        //read firebase database (real time)
        db.child("PROPIEDADES").child(tag).child("GASTOS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                gastos.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        Gasto gasto = ds.getValue(Gasto.class);
                        gastos.add(gasto);
                    }

                    CustomAdapterGastos adapter = new CustomAdapterGastos(GastoActivity.this, gastos);
                    listView.setAdapter(adapter);

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            listView.smoothScrollToPosition(gastos.size());
                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ERROR FIREBASE", databaseError.getMessage());
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
                                Intent intent=new Intent(GastoActivity.this, LoginActivity.class);
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