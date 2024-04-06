package com.ugb.controlesbasicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

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
    JSONArray datosJSON;
    JSONObject jsonObject;
    obtenerDatosServidor datosServidor;
    DetectarInternet di;
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
        try{
            di = new DetectarInternet(getApplicationContext());
            if(di.hayConexionInternet()){
                obtenerDatosPorductoServidor();
            }else{
                obtenerProductos();//offline
            }
        }catch (Exception e){
            mostrarMsg("Error al detectar si hay conexion "+ e.getMessage());
        }
        buscarProducto();
    }
    private void obtenerDatosPorductoServidor(){
        try{
            datosServidor = new obtenerDatosServidor();
            String data = datosServidor.execute().get();
            jsonObject = new JSONObject(data);
            datosJSON = jsonObject.getJSONArray("rows");
            mostrarDatosProductos();
        }catch (Exception e){
            mostrarMsg("Error al obtener datos desde el servidor: "+ e.getMessage());
        }
    }
    private void mostrarDatosProductos(){
        try{
            if( datosJSON.length()>0 ){
                lts = findViewById(R.id.ltsProducto);

                alProducto.clear();
                alProductoCopy.clear();

                JSONObject misDatosJSONObject;
                for (int i=0; i<datosJSON.length(); i++){
                    misDatosJSONObject = datosJSON.getJSONObject(i).getJSONObject("value");
                    misProductos = new producto(
                            misDatosJSONObject.getString("_id"),
                            misDatosJSONObject.getString("_rev"),
                            misDatosJSONObject.getString("idProducto"),
                            misDatosJSONObject.getString("codigo"),
                            misDatosJSONObject.getString("descripcion"),
                            misDatosJSONObject.getString("marca"),
                            misDatosJSONObject.getString("presentacion"),
                            misDatosJSONObject.getString("precio"),
                            misDatosJSONObject.getString("urlCompletaFoto")
                    );
                    alProducto.add(misProductos);
                }
                alProducto.addAll(alProductoCopy);

                adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(), alProducto);
                lts.setAdapter(adImagenes);

                registerForContextMenu(lts);
            }else{
                mostrarMsg("No hay datos que mostrar.");
            }
        }catch (Exception e){
            mostrarMsg("Error al mostrar los datos: "+e.getMessage());
        }
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);

        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo)menuInfo;
        cProducto.moveToPosition(info.position);
        menu.setHeaderTitle(cProducto.getString(1));//1 es el nombre del amigo
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {
            switch (item.getItemId()){
                case R.id.mnxAgregar:
                    parametros.putString("accion", "nuevo");
                    abrirActividad(parametros);
                    break;
                case R.id.mnxModificar:
                    String producto[] = {
                            cProducto.getString(0),
                            cProducto.getString(1),
                            cProducto.getString(2),
                            cProducto.getString(3),
                            cProducto.getString(4),
                            cProducto.getString(5),
                            cProducto.getString(6)
                    };
                    parametros.putString("accion","modificar");
                    parametros.putStringArray("productos", producto);
                    abrirActividad(parametros);
                    break;
                case R.id.mnxEliminar:
                    eliminarProductos();
                    break;
            }
            return true;
        }catch (Exception e){
            mostrarMsg("Error en menu: "+ e.getMessage());
            return super.onContextItemSelected(item);
        }
    }
    private void eliminarProductos(){
        try {
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(lista_producto.this);
            confirmacion.setTitle("Esta seguro de Eliminar el producto: ");
            confirmacion.setMessage(cProducto.getString(1));
            confirmacion.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String respuesta = db_producto.administrar_Productos("eliminar", new String[]{cProducto.getString(0)});
                    if (respuesta.equals("ok")) {
                        mostrarMsg("eliminado con exito.");
                        obtenerProductos();
                    } else {
                        mostrarMsg("Error al eliminar : " + respuesta);
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
    private void obtenerProductos(){
        try{
            db_producto = new DB(lista_producto.this, "", null, 1);
            cProducto = db_producto.consultar_Productos();
            if ( cProducto.moveToFirst() ){
                datosJSON = new JSONArray();
                do{
                    jsonObject = new JSONObject();
                    JSONObject jsonObjectValue = new JSONObject();
                    jsonObject.put("_id",cProducto.getString(0));
                    jsonObject.put("_rev",cProducto.getString(1));
                    jsonObject.put("idProducto",cProducto.getString(2));
                    jsonObject.put("codigo",cProducto.getString(3));
                    jsonObject.put("descripcion",cProducto.getString(4));
                    jsonObject.put("marca",cProducto.getString(5));
                    jsonObject.put("presentacion",cProducto.getString(6));
                    jsonObject.put("precio",cProducto.getString(7));
                    jsonObject.put("urlfotoCompleta",cProducto.getString(8));

                    jsonObjectValue.put("value", jsonObject);
                    datosJSON.put(jsonObjectValue);
                }while(cProducto.moveToNext());
                mostrarDatosProductos();
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