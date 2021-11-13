package com.montfel.qualeonumero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private Button btnEnviar, btnNovaPartida;
    private TextView tvQtdNumeros, tvStatus, tvNumero;
    private EditText etPalpite;
    private int num;
    private int palpite;
    private Slider sliderTamanhoTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.qual_numero));

        inicializaComponentes();
        fazRequisicaoDeNumeroAleatorio();
        trataInput();
        realizaPalpite();
        jogaNovamente();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_configuracao_texto, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuTamanhoTexto:
                if (sliderTamanhoTexto.getVisibility() == View.VISIBLE) {
                    sliderTamanhoTexto.setVisibility(View.INVISIBLE);
                } else {
                    sliderTamanhoTexto.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Clique novamente para fechar", Toast.LENGTH_SHORT).show();
                }

                sliderTamanhoTexto.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
                    @Override
                    public void onStartTrackingTouch(@NonNull Slider slider) {}

                    @Override
                    public void onStopTrackingTouch(@NonNull Slider slider) {
                        tvNumero.setTextSize((slider.getValue() * 20) + 40);
                    }
                });

                break;
            case R.id.menuCorTexto:
                Toast.makeText(getApplicationContext(), "Cor", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void inicializaComponentes() {
        btnEnviar = findViewById(R.id.btnEnviar);
        btnNovaPartida = findViewById(R.id.btnNovaPartida);
        tvNumero = findViewById(R.id.tvNumero);
        tvStatus = findViewById(R.id.tvStatus);
        tvNumero = findViewById(R.id.tvNumero);
        tvQtdNumeros = findViewById(R.id.tvQtdNumeros);
        etPalpite = findViewById(R.id.etPalpite);
        sliderTamanhoTexto = findViewById(R.id.sliderTamanhoTexto);
        sliderTamanhoTexto.setValue(3);
    }

    private void trataInput() {
        etPalpite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String tamanho = charSequence.length() + "/3";
                tvQtdNumeros.setText(tamanho);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void jogaNovamente() {
        btnNovaPartida.setOnClickListener(view -> {
            btnNovaPartida.setVisibility(View.INVISIBLE);
            btnEnviar.setEnabled(true);
            fazRequisicaoDeNumeroAleatorio();
        });
    }

    private void realizaPalpite() {
        btnEnviar.setOnClickListener(view -> {
//            fazRequisicaoDeNumeroAleatorio();
            palpite = Integer.parseInt(etPalpite.getText().toString());
            tvStatus.setVisibility(View.VISIBLE);
            tvNumero.setText(String.valueOf(palpite));
            if (num < palpite) {
                tvStatus.setText(R.string.menor);
            } else if (num > palpite) {
                tvStatus.setText(R.string.maior);
            } else {
                tvStatus.setText(R.string.acertou);
                btnNovaPartida.setVisibility(View.VISIBLE);
                btnEnviar.setEnabled(false);
            }
        });
    }

    private void fazRequisicaoDeNumeroAleatorio() {
        try {
            Numero numero = new HttpService().execute().get();
            num = Integer.parseInt(numero.getValue());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}