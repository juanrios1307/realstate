package com.riossoftware.myrealstate.pojo;

public class Garantia {

    String fechaprestamo,fechavencimiento,interes,nombre,tag,telefono,tiempo,tipo,tipoGarantia,valor,pagos_atrasados;

    public String getPagos_atrasados() {
        return pagos_atrasados;
    }

    public void setPagos_atrasados(String pagos_atrasados) {
        this.pagos_atrasados = pagos_atrasados;
    }

    public String getFechaprestamo() {
        return fechaprestamo;
    }

    public void setFechaprestamo(String fechaprestamo) {
        this.fechaprestamo = fechaprestamo;
    }

    public String getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(String fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
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

    public String getTipoGarantia() {
        return tipoGarantia;
    }

    public void setTipoGarantia(String tipoGarantia) {
        this.tipoGarantia = tipoGarantia;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Garantia{" +
                "fechaprestamo='" + fechaprestamo + '\'' +
                ", fechavencimiento='" + fechavencimiento + '\'' +
                ", interes='" + interes + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tag='" + tag + '\'' +
                ", telefono='" + telefono + '\'' +
                ", tiempo='" + tiempo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", tipoGarantia='" + tipoGarantia + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
