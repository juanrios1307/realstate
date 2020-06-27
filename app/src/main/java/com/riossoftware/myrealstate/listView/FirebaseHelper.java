package com.riossoftware.myrealstate.listView;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved;
    ArrayList<Propiedad> propers = new ArrayList<>();
    ListView mListView;
    Context c;

    /*
   let's receive a reference to our FirebaseDatabase
   */
    public FirebaseHelper(DatabaseReference db, Context context, ListView mListView) {
        this.db = db;
        this.c = context;
        this.mListView = mListView;
        this.retrieve();
    }

    /*
    let's now write how to save a single Teacher to FirebaseDatabase
     */
    public Boolean save(Propiedad proper) {
        //check if they have passed us a valid proper. If so then return false.
        if (proper == null) {
            saved = false;
        } else {
            //otherwise try to push data to firebase database.
            try {
                //push data to FirebaseDatabase. Table or Child called Teacher will be created.
                db.child("PROPIEDADES").push().setValue(proper);
                saved = true;

            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        //tell them of status of save.
        return saved;
    }

    /*
    Retrieve and Return them clean data in an arraylist so that they just bind it to ListView.
     */
    public ArrayList<Propiedad> retrieve() {
        db.child("PROPIEDADES").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                propers.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Now get Teacher Objects and populate our arraylist.
                        Propiedad proper = ds.getValue(Propiedad.class);
                        propers.add(proper);
                    }
                    System.out.println("props adapter: "+propers);

                    CustomAdapter adapter = new CustomAdapter(c, propers);
                    mListView.setAdapter(adapter);

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mListView.smoothScrollToPosition(propers.size());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("mTAG", databaseError.getMessage());
                Toast.makeText(c, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        return propers;
    }

}