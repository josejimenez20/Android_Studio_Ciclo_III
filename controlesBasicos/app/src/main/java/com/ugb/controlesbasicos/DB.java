package com.ugb.controlesbasicos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    private static final String dbname = "db_producto";
    private static final int v=1;
    private static final String SQldb = "CREATE TABLE producto(id text, rev text, idProducto text, codigo text, descripcion text, marca text, presentacion text, precio text, foto text)";
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
    public String administrar_Productos(String accion, String[] datos){
        try {
            SQLiteDatabase db = getWritableDatabase();
            if(accion.equals("nuevo")){
                db.execSQL("INSERT INTO productos(id,rev,idProducto,codigo,descripcion,marca,presentacion,precio,foto) VALUES('"+ datos[0] +"', '"+ datos[1] +"', '"+
                        datos[2] +"', '"+ datos[3] +"','"+ datos[4] +"','"+ datos[5] +"','"+ datos[6] +"','"+ datos[7] +"', '"+ datos[8] +"')");
            } else if (accion.equals("modificar")) {
                db.execSQL("UPDATE productos SET id='"+ datos[0] +"',rev='"+ datos[1] +"', codigo='"+ datos[3] +"',descripcion='"+ datos[4] +"',marca='"+
                        datos[5] +"',presentacion='"+ datos[6] +"',precio='"+ datos[7] +"', foto='"+ datos[8] +"' WHERE idProducto='"+ datos[2] +"'");
            } else if (accion.equals("eliminar")) {
                db.execSQL("DELETE FROM productos WHERE idProducto='"+ datos[2] +"'");
            }
            return "ok";
        }catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }
    public Cursor consultar_Productos(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM productos ORDER BY codigo", null);
        return cursor;

    }
}
