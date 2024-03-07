package com.ugb.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    TextView tempVal;
    Button btn;
    FloatingActionButton btnRegresar;
    String accion="nuevo", id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegresar = findViewById(R.id.btnRegresarListaAmigos);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresarListaProducto();
            }
        });
        btn = findViewById(R.id.btnGuardarAmigos);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempVal = findViewById(R.id.txtcodigo);
                String codigo = tempVal.getText().toString();

                tempVal = findViewById(R.id.txtdescripcion);
                String descripcion = tempVal.getText().toString();

                tempVal = findViewById(R.id.txtmarca);
                String marca = tempVal.getText().toString();

                tempVal = findViewById(R.id.txtpresentacion);
                String presentacion = tempVal.getText().toString();

                tempVal = findViewById(R.id.txtprecio);
                String precio = tempVal.getText().toString();

                DB db = new DB(getApplicationContext(),"", null, 1);
                String[] datos = new String[]{id,codigo,descripcion,marca,presentacion,precio};
                String respuesta = db.administrar_producto(accion, datos);
                if( respuesta.equals("ok") ){
                    Toast.makeText(getApplicationContext(), "Producto Registrado con Exito.", Toast.LENGTH_SHORT).show();
                    regresarListaProducto();
                }else{
                    Toast.makeText(getApplicationContext(), "Error: "+ respuesta, Toast.LENGTH_LONG).show();
                }
            }
        });
        mostrarDatosProducto();
    }
    private void regresarListaProducto(){
        Intent abrirVentana = new Intent(getApplicationContext(), lista_producto.class);
        startActivity(abrirVentana);
    }
    private void mostrarDatosProducto(){
        try {
            Bundle parametros = getIntent().getExtras();
            accion = parametros.getString("accion");
            if( accion.equals("modificar") ){
                String[] datos = parametros.getStringArray("producto");
                id = datos[0];

                tempVal = findViewById(R.id.txtcodigo);
                tempVal.setText(datos[1]);

                tempVal = findViewById(R.id.txtdescripcion);
                tempVal.setText(datos[2]);

                tempVal = findViewById(R.id.txtmarca);
                tempVal.setText(datos[3]);

                tempVal = findViewById(R.id.txtpresentacion);
                tempVal.setText(datos[4]);

                tempVal = findViewById(R.id.txtprecio);
                tempVal.setText(datos[5]);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error al mostrar los datos: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}