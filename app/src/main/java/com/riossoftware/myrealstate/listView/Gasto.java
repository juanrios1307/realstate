package com.riossoftware.myrealstate.listView;

public class Gasto {

    String fecha,description,valor;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Gasto{" +
                "fecha='" + fecha + '\'' +
                ", description='" + description + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
