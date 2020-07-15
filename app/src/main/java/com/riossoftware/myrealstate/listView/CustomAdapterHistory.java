package com.riossoftware.myrealstate.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.riossoftware.myrealstate.R;

import java.util.ArrayList;

public class CustomAdapterHistory extends BaseAdapter {

    Context c;
    ArrayList<Pago> pagos;

    public CustomAdapterHistory(Context c, ArrayList<Pago> pagos) {
        this.c = c;
        this.pagos = pagos;
    }

    @Override
    public int getCount() {
        return pagos.size();
    }

    @Override
    public Object getItem(int i) {
        return pagos.get(i);
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


        final Pago s = (Pago) this.getItem(position);

        fecha.setText(s.getFecha());
        valor.setText(s.getValor());
        lugar.setText(s.getLugar());

        //ONITEMCLICK


        return convertView;
    }
}
