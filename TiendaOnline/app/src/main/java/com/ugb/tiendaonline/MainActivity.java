package com.ugb.tiendaonline;

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

        btnRegresar = findViewById(R.id.btnRegresarListaProductos);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresarListaProducto();
            }
        });
        btn = findViewById(R.id.btnGuardarProductos);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempVal = findViewById(R.id.txtcodigo );
                String Codigo = tempVal.getText().toString();

                tempVal = findViewById(R.id.txtdescripcion);
                String Descripcion = tempVal.getText().toString();

                tempVal = findViewById(R.id.txtmarca);
                String Marca = tempVal.getText().toString();

                tempVal = findViewById(R.id.txtpresentacion);
                String Presentacion = tempVal.getText().toString();

                tempVal = findViewById(R.id.txtprecio);
                String Precio = tempVal.getText().toString();

                DB_Productos db = new DB_Productos(getApplicationContext(),"", null, 1);
                String[] datos = new String[]{id, Codigo, Descripcion, Marca, Presentacion, Precio};
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
                String[] datos = parametros.getStringArray("Producto");
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