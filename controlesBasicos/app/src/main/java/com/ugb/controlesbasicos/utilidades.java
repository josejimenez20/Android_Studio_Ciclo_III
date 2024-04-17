package com.ugb.controlesbasicos;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;

public class utilidades {
    static String urlConsulta = "http://192.168.0.2:5984/tienda/_design/tienda/_view/tienda";
    static String urlMto = "http://192.168.0.2:5984/tienda";
    static String user = "admin";
    static String passwd = "jose2023yt";
    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user + ":" + passwd).getBytes());

    public String generarIdUnico() {
        return java.util.UUID.randomUUID().toString();
    }
}