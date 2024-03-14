package com.ugb.controlesbasicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class lista_producto extends AppCompatActivity {
    Bundle parametros = new Bundle();
    FloatingActionButton btn;
    ListView lts;
    Cursor cProducto;
    DB db_producto;
    producto misProductos;
    final ArrayList<producto> alProducto=new ArrayList<producto>();
    final ArrayList<producto> alProductoCopy=new ArrayList<producto>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_producto);

        btn = findViewById(R.id.btnAbrirNuevosProductos);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion", "nuevo");
                abrirActividad(parametros);
            }
        });
        obtenerProducto();
        buscarProducto();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);

        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo)menuInfo;
        cProducto.moveToPosition(info.position);
        menu.setHeaderTitle(cProducto.getString(1));//1 es el nombre del Producto
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.mnxAgregar:
                    parametros.putString("accion", "nuevo");
                    abrirActividad(parametros);
                    break;

                case R.id.mnxModificar:
                    String producto[] = {
                            cProducto.getString(0), //idProducto
                            cProducto.getString(1), //codigo
                            cProducto.getString(2), //descripcion
                            cProducto.getString(3), //marca
                            cProducto.getString(4), //presentacion
                            cProducto.getString(5),//precio
                            cProducto.getString(6) //foto
                    };
                    parametros.putString("accion", "modificar");
                    parametros.putStringArray("producto", producto);
                    abrirActividad(parametros);
                    break;

                case R.id.mnxEliminar:
                    eliminarProducto();
                    break;
            }

            return true;
        } catch (Exception e) {
            mostrarMsg("Error en menu: " + e.getMessage());
            return super.onContextItemSelected(item);
        }
    }
    private void eliminarProducto(){
        try {
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(lista_producto.this);
            confirmacion.setTitle("Estas seguro de eliminar el producto: ");
            confirmacion.setMessage(cProducto.getString(3));
            confirmacion.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String respuesta = db_producto.administrar_Productos("eliminar", new String[]{cProducto.getString(0)});
                    if (respuesta.equals("ok")) {
                        mostrarMsg("Producto eliminado con exito.");
                        obtenerProducto();
                    } else {
                        mostrarMsg("Error al producto amigo: " + respuesta);
                    }
                }
            });
            confirmacion.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            confirmacion.create().show();
        }catch (Exception e){
            mostrarMsg("Error al eliminar: "+ e.getMessage());
        }
    }




    private void abrirActividad(Bundle parametros){
        Intent abriVentana = new Intent(getApplicationContext(), MainActivity.class);
        abriVentana.putExtras(parametros);
        startActivity(abriVentana);
    }
    private void obtenerProducto(){
        try{
            alProducto.clear();
            alProductoCopy.clear();

            db_producto = new DB(lista_producto.this, "", null, 1);
            cProducto = db_producto.consultar_Productos();

            if ( cProducto.moveToFirst() ){
                lts = findViewById(R.id.ltsProducto);
                do{
                    misProductos = new producto(
                            cProducto.getString(0),//idProducto
                            cProducto.getString(1),//codigo
                            cProducto.getString(2),//descripcion
                            cProducto.getString(3),//marca
                            cProducto.getString(4),//presentacion
                            cProducto.getString(5),//precio
                            cProducto.getString(6)//foto
                    );
                    alProducto.add(misProductos);
                }while(cProducto.moveToNext());
                alProductoCopy.addAll(alProducto);

                adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(), alProducto);
                lts.setAdapter(adImagenes);

                registerForContextMenu(lts);
            }else{
                mostrarMsg("No hay productos que mostrar");
            }
        }catch (Exception e){
            mostrarMsg("Error al obtener productos: "+ e.getMessage());
        }
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
    private void buscarProducto(){
        TextView tempVal;
        tempVal = findViewById(R.id.txtBuscarProducto);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    alProducto.clear();
                    String valor = tempVal.getText().toString().trim().toLowerCase();
                    if( valor.length()<=0 ){
                        alProducto.addAll(alProductoCopy);
                    }else{
                        for( producto Producto : alProductoCopy ){
                            String nombre = Producto.getMarca();
                            String descripcion = Producto.getDescripcion();
                            String marca = Producto.getMarca();
                            String presentacion = Producto.getPresentacion();
                            if( nombre.toLowerCase().trim().contains(valor) ||
                                    descripcion.toLowerCase().trim().contains(valor) ||
                                    marca.trim().contains(valor) ||
                                    presentacion.trim().toLowerCase().contains(valor) ){
                                alProducto.add(Producto);
                            }
                        }
                        adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(), alProducto);
                        lts.setAdapter(adImagenes);
                    }
                }catch (Exception e){
                    mostrarMsg("Error al buscar: "+e.getMessage() );
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}