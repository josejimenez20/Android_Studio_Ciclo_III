package com.ugb.controlesbasicos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView tempVal;
    Button btn;
    FloatingActionButton btnRegresar;
    String accion="nuevo", id="", urlCompletaImg="";
    Intent tomarFotoIntent;
    ImageView img;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imgFoto);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFotoProducto();
            }
        });
        btnRegresar = findViewById(R.id.btnRegresarListaProducto);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresarListaProducto();
            }
        });


        btn = findViewById(R.id.btnGuardarProducto);
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
                String[] datos = new String[]{id,codigo,descripcion,marca,presentacion,precio,urlCompletaImg};
                String respuesta = db.administrar_Productos(accion, datos);
                if( respuesta.equals("ok") ){
                    Toast.makeText(getApplicationContext(), "Producto Registrado con Exito.", Toast.LENGTH_SHORT).show();
                    regresarListaProducto();
                }else{
                    Toast.makeText(getApplicationContext(), "Error:  "+ respuesta, Toast.LENGTH_LONG).show();
                }
            }
        });
        mostrarDatosProducto();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if( requestCode==1 && resultCode==RESULT_OK ){
                Bitmap imagenBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                img.setImageBitmap(imagenBitmap);
            }else{
                mostrarMsg("Se cancelo la toma de la foto");
            }
        }catch (Exception e){
            mostrarMsg("Error al mostrar la camara: "+ e.getMessage());
        }
    }
    private void tomarFotoProducto(){
        tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //if( tomarFotoIntent.resolveActivity(getPackageManager())!=null ){
            File fotoProducto = null;
            try{
                fotoProducto = crearImagenProducto();
                if( fotoProducto!=null ){
                    Uri uriFotoProducto = FileProvider.getUriForFile(MainActivity.this, "com.ugb.controlesbasicos.fileprovider", fotoProducto);
                    tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFotoProducto);
                    startActivityForResult(tomarFotoIntent, 1);
                }else{
                    mostrarMsg("NO pude tomar la foto");
                }
            }catch (Exception e){
                mostrarMsg("Error al tomar la foto: "+ e.getMessage());
            }

        }

    private File crearImagenProduct() throws Exception{
        String fechaHoraMs = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "imagen_"+ fechaHoraMs +"_";
        File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if( !dirAlmacenamiento.exists() ){
            dirAlmacenamiento.mkdirs();
        }
        File image = File.createTempFile(fileName, ".jpg", dirAlmacenamiento);
        urlCompletaImg = image.getAbsolutePath();
        return image;
    }

    private File crearImagenProducto() throws Exception{

        String fechaHoraMs = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "imagen_"+ fechaHoraMs +"_";
        File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if( !dirAlmacenamiento.exists() ){
            dirAlmacenamiento.mkdirs();
        }

        File image = File.createTempFile(fileName, ".jpg", dirAlmacenamiento);
        urlCompletaImg = image.getAbsolutePath();
        return image;
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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

                urlCompletaImg = datos[6];
                Bitmap bitmap = BitmapFactory.decodeFile(urlCompletaImg);
                img.setImageBitmap(bitmap);

            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error al mostrar los datos: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
