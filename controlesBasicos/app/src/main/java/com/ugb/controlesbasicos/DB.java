package com.ugb.controlesbasicos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    private static final String dbname = "db_Productos";
    private static final int v=1;
    private static final String SQldb = "CREATE TABLE Productos(codigoProducto integer primary key autoincrement, nombre text, descripcion text, marca text, presentacion text, precio real,foto text)";
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, v);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQldb);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        //para hacer la actualizacion de la BD
    }
    public String administrar_Productos(String accion, String[] datos){
        try {
            ContentValues valoresProducto = new ContentValues();
            String[] whereArgs={String.valueOf(datos[0])};
            String whereClause="codigoProducto = ?";
            String miTabla = "Productos";
            if (datos.length>1) {
                valoresProducto.put("nombre", datos[1]);
                valoresProducto.put("descripcion", datos[2]);
                valoresProducto.put("marca", datos[3]);
                valoresProducto.put("presentacion", datos[4]);
                valoresProducto.put("precio", datos[5]);
                valoresProducto.put("foto", datos[6]);
            }
            SQLiteDatabase db = getWritableDatabase();
            switch (accion){
                case "nuevo":
                    this.getWritableDatabase().insert(miTabla,null,valoresProducto);
                    break;
                case "modificar":
                    this.getWritableDatabase().update(miTabla,valoresProducto,whereClause,whereArgs);
                    break;
                case "eliminar":
                    this.getWritableDatabase().delete(miTabla,whereClause,whereArgs);
                    break;
            }
            return "ok";
        }catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }
    public Cursor consultar_Productos(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Productos ORDER BY nombre", null);
        return cursor;

    }
}
