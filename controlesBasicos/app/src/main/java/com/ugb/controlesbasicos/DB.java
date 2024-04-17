package com.ugb.controlesbasicos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    private static final String dbname = "db_productos";
    private static final int v=1;
    private static final String SQldb = "CREATE TABLE Productos( idProducto txt,codigo text, descripcion text, marca text, presentacion text, precio text,foto text,id text,rev text)";
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
    public void vaciar(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Productos");
    }
    public Boolean hayRegistros(){
        SQLiteDatabase db = getReadableDatabase();
        long cantidadFilas = DatabaseUtils.queryNumEntries(db,"Productos");
        return cantidadFilas!=0;
    }
    public String administrar_Productos(String accion, String[] datos){
        try {
            ContentValues valoresProducto = new ContentValues();
            String[] whereArgs={String.valueOf(datos[0])};
            String whereClause="idProducto = ?";
            String miTabla = "Productos";
            valoresProducto.put("idProducto", datos[0]);
            if (datos.length>1) {

                valoresProducto.put("codigo", datos[1]);
                valoresProducto.put("descripcion", datos[2]);
                valoresProducto.put("marca", datos[3]);
                valoresProducto.put("presentacion", datos[4]);
                valoresProducto.put("precio", datos[5]);
                valoresProducto.put("foto", datos[6]);
                valoresProducto.put("id",datos[7]);
                valoresProducto.put("rev",datos[8]);
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
                    //db.execSQL("DELETE FROM Productos WHERE idProducto= ''"+datos[2]+"'");
                    break;
            }
            return "ok";
        }catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }
    public Cursor consultar_Productos(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Productos ORDER BY codigo", null);
        return cursor;

    }
}

