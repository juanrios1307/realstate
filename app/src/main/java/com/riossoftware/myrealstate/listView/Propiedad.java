package com.riossoftware.myrealstate.listView;

public class Propiedad {

    private String tag, tipo,nombre;
    private long telefono,valor;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getValor() {
        return valor;
    }

    public void setValor(long valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Propiedad{" +
                "tag='" + tag + '\'' +
                ", valor='" + valor + '\'' +
                ", nombre='" + tipo + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
