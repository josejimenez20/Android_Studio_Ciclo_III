package com.ugb.controlesbasicos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    private static final String dbname = "db_producto";
    private static final int v=1;
    private static final String SQldb = "CREATE TABLE producto(idProducto integer primary key autoincrement, codigo text, descripcion text, marca text, presentacion text, precio text)";
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, v);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQldb);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //para hacer la actualizacion de la BD
    }
    public String administrar_producto(String accion, String[] datos){
        try {
            SQLiteDatabase db = getWritableDatabase();
            if(accion.equals("nuevo")){
                db.execSQL("INSERT INTO producto(codigo,descripcion,marca,presentacion,precio) VALUES('"+ datos[1] +"','"+ datos[2] +"','"+ datos[3] +"','"+ datos[4] +"','"+ datos[5] +"')");
            } else if (accion.equals("modificar")) {
                db.execSQL("UPDATE producto SET codigo='"+ datos[1] +"',descripcion='"+ datos[2] +"',marca='"+ datos[3] +"',presentacion='"+ datos[4] +"',precio='"+ datos[5] +"' WHERE idProducto='"+ datos[0] +"'");
            } else if (accion.equals("eliminar")) {
                db.execSQL("DELETE FROM producto WHERE idProducto='"+ datos[0] +"'");
            }
            return "ok";
        }catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }
    public Cursor consultar_producto(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM producto ORDER BY codigo", null);
        return cursor;

    }
}
