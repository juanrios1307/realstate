package com.riossoftware.myrealstate.pojo;

public class Casa {

    String direccion,fecha,nombre,tag,telefono,tipo,valor, fmi,valor_predial,fecha_vencimiento_predial,arriendos_atrasados,avaluo;
    boolean rentada;

    public String getAvaluo() {
        return avaluo;
    }

    public void setAvaluo(String avaluo) {
        this.avaluo = avaluo;
    }

    public String getFmi() {
        return fmi;
    }

    public void setFmi(String fmi) {
        this.fmi = fmi;
    }

    public String getValor_predial() {
        return valor_predial;
    }

    public void setValor_predial(String valor_predial) {
        this.valor_predial = valor_predial;
    }

    public String getFecha_vencimiento_predial() {
        return fecha_vencimiento_predial;
    }

    public void setFecha_vencimiento_predial(String fecha_vencimiento_predial) {
        this.fecha_vencimiento_predial = fecha_vencimiento_predial;
    }

    public String getArriendos_atrasados() {
        return arriendos_atrasados;
    }

    public void setArriendos_atrasados(String arriendos_atrasados) {
        this.arriendos_atrasados = arriendos_atrasados;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isRentada() {
        return rentada;
    }

    public void setRentada(boolean rentada) {
        this.rentada = rentada;
    }

    @Override
    public String toString() {
        return "Casa{" +
                "direccion='" + direccion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tag='" + tag + '\'' +
                ", telefono='" + telefono + '\'' +
                ", tipo='" + tipo + '\'' +
                ", valor='" + valor + '\'' +
                ", rentada=" + rentada +
                '}';
    }
}
