package com.riossoftware.myrealstate.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.riossoftware.myrealstate.R;

import java.util.ArrayList;

public class CustomAdapterGastos extends BaseAdapter {

    Context c;
    ArrayList<Gasto> gastos;

    public CustomAdapterGastos(Context c, ArrayList<Gasto> gastos) {
        this.c = c;
        this.gastos = gastos;
    }

    @Override
    public int getCount() {
        return gastos.size();
    }

    @Override
    public Object getItem(int i) {
        return gastos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.list_historico, parent, false);
        }

        TextView fecha=convertView.findViewById(R.id.txtDate);
        TextView valor=convertView.findViewById(R.id.txtValor);
        TextView lugar=convertView.findViewById(R.id.txtLugar);


        final Gasto s = (Gasto) this.getItem(position);

        fecha.setText(s.getFecha());
        valor.setText(s.getValor());
        lugar.setText(s.getDescription());

        //ONITEMCLICK


        return convertView;
    }
}
