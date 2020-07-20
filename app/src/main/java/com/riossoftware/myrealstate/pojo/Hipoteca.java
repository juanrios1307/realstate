package com.riossoftware.myrealstate.pojo;

public class Hipoteca {

    String avaluo;
    String direccion;
    String fecha;
    String interes;
    String nombre;
    String tag;
    String telefono;
    String tiempo;
    String tipo;
    String valor;
    String fecha_vencimiento;
    String pagos_atrasados;

    public String getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(String fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public String getPagos_atrasados() {
        return pagos_atrasados;
    }

    public void setPagos_atrasados(String pagos_atrasados) {
        this.pagos_atrasados = pagos_atrasados;
    }



    public String getAvaluo() {
        return avaluo;
    }

    public void setAvaluo(String avaluo) {
        this.avaluo = avaluo;
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

    public String getInteres() {
        return interes;
    }

    public void setInteres(String interes) {
        this.interes = interes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String ombre) {
        this.nombre = ombre;
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

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
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

    @Override
    public String toString() {
        return "Hipoteca{" +
                "avaluo='" + avaluo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", interes='" + interes + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tag='" + tag + '\'' +
                ", telefono='" + telefono + '\'' +
                ", tiempo='" + tiempo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
