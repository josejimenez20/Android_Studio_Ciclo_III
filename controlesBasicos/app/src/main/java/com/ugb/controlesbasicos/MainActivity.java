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

public class MainActivity extends AppCompatActivity {
    TabHost tbh;
    Spinner spn;
    TextView tempval;
    Button btn;
    conversores miObj = new conversores();
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbh = findViewById(R.id.tbhConversores);
        tbh.setup();

        tbh.addTab(tbh.newTabSpec("Longuitud").setIndicator("LONGUITUD").setContent(R.id.tabLongitud));
        tbh.addTab(tbh.newTabSpec("Almacenamiento").setIndicator("ALMACENAMIENTO").setContent(R.id.tabAlmacenamiento));
        tbh.addTab(tbh.newTabSpec("Moneda").setIndicator("MONEDA").setContent(R.id.tabMonedas));

        btn = findViewById(R.id.btnLongitudConvertir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                spn = findViewById(R.id.spnLongitudDe);
                int de = spn.getSelectedItemPosition();

            }
        });
    }

    class conversores {
        double [][] valores = {
                {1,100, 39.3701, 3,28084, 1.193, 1.09361, 0,001, 0.000621371}

        };


    }
}