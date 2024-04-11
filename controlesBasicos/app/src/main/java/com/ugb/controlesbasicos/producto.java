package com.ugb.controlesbasicos;

import androidx.annotation.NonNull;

public class producto {
    String _id;
    String _rev;
    String codigoProducto;
    String nombre;
    String descripcion;
    String marca;
    String presentacion;
    String costo;
    String ganancia;
    String stock;
    String foto;

    public producto(String idProducto, String nombre, String descripcion, String marca, String presentacion, String costo, String ganancia, String stock, String foto, String _id, String _rev) {
        this._id=_id;
        this._rev= _rev;
        this.codigoProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.presentacion = presentacion;
        this.costo = costo;
        this.ganancia = ganancia;
        this.stock = stock;
        this.foto = foto;
    }
    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public String get_rev() {
        return _rev;
    }
    public void set_rev(String _rev) {
        this._rev = _rev;
    }


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setIdProducto(String idProducto) {
        this.codigoProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String direccion) {
        this.descripcion = direccion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String telefono) {
        this.marca = telefono;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String email) {
        this.presentacion = email;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String dui) {
        this.costo = dui;
    }

    public String getGanancia() {
        return ganancia;
    }

    public void setGanancia(String ganancia) {
        this.ganancia = ganancia;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}

