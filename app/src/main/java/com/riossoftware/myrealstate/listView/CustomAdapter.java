package com.riossoftware.myrealstate.listView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.riossoftware.myrealstate.LoginActivity;
import com.riossoftware.myrealstate.ProfileMainActivity;
import com.riossoftware.myrealstate.R;
import com.riossoftware.myrealstate.actions.DatosActivity;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Propiedad> propers;

    public CustomAdapter(Context c, ArrayList<Propiedad> propers) {
        this.c = c;
        this.propers = propers;
    }

    @Override
    public int getCount() {
        return propers.size();
    }

    @Override
    public Propiedad getItem(int position) {
        return propers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.list_item, parent, false);
        }

        TextView tag=convertView.findViewById(R.id.txtTag);
        TextView name=convertView.findViewById(R.id.txtname);
        TextView tel=convertView.findViewById(R.id.txttel);
        TextView valor=convertView.findViewById(R.id.txtaux);
        TextView tipo=convertView.findViewById(R.id.txtTipo);

        final Propiedad s = (Propiedad) this.getItem(position);

        tag.setText(s.getTag());
        name.setText(s.getNombre());
        tel.setText(s.getTelefono());
        valor.setText(s.getValor());
        tipo.setText(s.getTipo());

        //ONITEMCLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(c, DatosActivity.class);
                intent.putExtra("tag",s.getTag());

                c.startActivity(intent);

            }
        });

        convertView.setLongClickable(true);



        return convertView;
    }



}