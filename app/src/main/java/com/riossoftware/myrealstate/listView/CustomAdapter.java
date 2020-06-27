package com.riossoftware.myrealstate.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.riossoftware.myrealstate.R;

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
    public Object getItem(int position) {
        return propers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.list_item, parent, false);
        }

        TextView tag=convertView.findViewById(R.id.txtTag);
        TextView name=convertView.findViewById(R.id.txtname);
        TextView tel=convertView.findViewById(R.id.txttel);
        TextView valor=convertView.findViewById(R.id.txtaux);

        final Propiedad s = (Propiedad) this.getItem(position);

        tag.setText(s.getTag());
        name.setText(s.getNombre());
        tel.setText(Long.toString(s.getTelefono()));
        valor.setText(Long.toString(s.getValor()));

        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, s.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}