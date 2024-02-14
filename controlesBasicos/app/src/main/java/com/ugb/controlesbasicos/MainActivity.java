package com.ugb.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TabHost tbh;
    TextView tempVal;
    Button btn;
    Spinner spn;
    conversores objConversor = new conversores();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbh = findViewById(R.id.tbhConversores);
        tbh.setup();

        tbh.addTab(tbh.newTabSpec("LONGITUD").setContent(R.id.tabLongitud).setIndicator("LONGITUD"));
        tbh.addTab(tbh.newTabSpec("ALMACENAMIENTO").setContent(R.id.tabAlmacenamiento).setIndicator("ALMACENAMIENTO"));
        tbh.addTab(tbh.newTabSpec("MONEDAS").setContent(R.id.tabMonedas).setIndicator("MONEDAS"));
        tbh.addTab(tbh.newTabSpec("MASA").setContent(R.id.tabMasa).setIndicator("Masa"));
        tbh.addTab(tbh.newTabSpec("VOLUMEN").setContent(R.id.tabVolumen).setIndicator("Volumen"));
        tbh.addTab(tbh.newTabSpec("TIEMPO").setContent(R.id.tabTiempo).setIndicator("Tiempo"));
        tbh.addTab(tbh.newTabSpec("transferencia").setIndicator("TRANSFERENCIA DE DATOS").setContent(R.id.tabTransferencia));

        btn = findViewById(R.id.btnLongitudConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spn = findViewById(R.id.spnLongitudDe);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnLongitudA);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtLongitudCantidad);
                double cantidad = Double.parseDouble(tempVal.getText().toString());

                double resp = objConversor.convertir(0, de, a, cantidad);
                Toast.makeText(getApplicationContext(),"Respuesta: "+
                        resp, Toast.LENGTH_LONG).show();
            }
        });
        btn = findViewById(R.id.btnAlmacenamientoConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spn = findViewById(R.id.spnAlmacenamientoDe);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnAlmacenamientoA);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtAlmacenamientoCantidad);
                double cantidad = Double.parseDouble(tempVal.getText().toString());

                double resp = 1/objConversor.convertir(1, de, a, cantidad);
                Toast.makeText(getApplicationContext(),"Respuesta: "+
                        resp, Toast.LENGTH_LONG).show();
            }
        });
        btn = findViewById(R.id.btnMonedasConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spn = findViewById(R.id.spnMonedasDe);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnMonedasA);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtMonedasCantidad);
                double cantidad = Double.parseDouble(tempVal.getText().toString());

                double resp = objConversor.convertir(2, de, a, cantidad);
                Toast.makeText(getApplicationContext(),"Respuesta: "+
                        resp, Toast.LENGTH_LONG).show();
            }
        });
        //Masa
        btn = findViewById(R.id.btnMasaConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spn = findViewById(R.id.spnMasaDe);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnMasaA);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtMasa);
                double cantidad = Double.parseDouble(tempVal.getText().toString());

                double resp = objConversor.convertir(3, de, a, cantidad);
                Toast.makeText(getApplicationContext(),"Respuesta: "+
                        resp, Toast.LENGTH_LONG).show();
            }

        });
        //Volumen
        btn = findViewById(R.id.btnVolumenConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spn = findViewById(R.id.spnVolumenDe);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnVolumenA);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtVolumen);
                double cantidad = Double.parseDouble(tempVal.getText().toString());

                double resp = objConversor.convertir(4, de, a, cantidad);
                Toast.makeText(getApplicationContext(),"Respuesta: "+
                        resp, Toast.LENGTH_LONG).show();
            }

        });
        btn = findViewById(R.id.btnTiempoConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spn = findViewById(R.id.spnTiempoDe);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnTiempoA);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtTiempoCantidad);
                double cantidad = Double.parseDouble(tempVal.getText().toString());

                double resp = objConversor.convertir(5, de, a, cantidad);
                Toast.makeText(getApplicationContext(),"Respuesta: "+
                        resp, Toast.LENGTH_LONG).show();
            }

        });
        btn = findViewById(R.id.btnTransferenciaConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spn = findViewById(R.id.spnTransferenciaDe);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnTransferenciaA);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtTransferencia);
                double cantidad = Double.parseDouble(tempVal.getText().toString());

                double resp = objConversor.convertir(6, de, a, cantidad);
                Toast.makeText(getApplicationContext(),"Respuesta: "+
                        resp, Toast.LENGTH_LONG).show();
            }

        });




    }
}
class conversores{
    double[][] valores = {
            //Longitud Listo
            {1, 100, 39.3701, 3.280841666667, 1.193, 1.0936138888889999077, 0.001, 0.000621371, 0.001, 0.000001, 0.000000001},

            //Almacenamiento Listo
            {1, 8, 1000*8, Math.pow(1000,2)*8, Math.pow(1000,3)*8, Math.pow(1000,4)*8, Math.pow(1000,5)*8,Math.pow(1000,6)*8,Math.pow(1000,7)*8,
                    1024*8, Math.pow(1024,2)*8, Math.pow(1024,3)*8, Math.pow(1024,4)*8, Math.pow(1024,5)*8,Math.pow(1024,6)*8,Math.pow(1024,7)*8,},
            //Monedas Listo
            {1,0.93,7.81,17.14,149.27,0.79,24.73,36.78,1.35,3946.75,965.92,830.67,8.76},
            //Masa Listo
            {1, 0.453592, 453.592, 0.000453592, 453592, 453600000, 0.000446429, 0.0005, 0.0714286, 16},
            //Volumen
            {1, 0.264172, 1.05669, 2.11338, 4.16667, 33.814, 67.628, 202.884, 0.001, 1000},
            //Tiempo
            {1, 60, 0.0166667, 0.000694444, 0.000099206, 0.000022831, 0.0000019026, 0.00000019026, 0.00000001903, 60000},
            //Transferencia de datos
            {1, 8, 7.62939, 8e+6, 8000, 1000, 7812.5, 0.008, 0.001, 0.00745058, 8e-6, 1e-6, 7.276e-6}
    };
    public double convertir(int opcion, int de, int a, double cantidad){
        return valores[opcion][a] / valores[opcion][de] * cantidad;
    }
}