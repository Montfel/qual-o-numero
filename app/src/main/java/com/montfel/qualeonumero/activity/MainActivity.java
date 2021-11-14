package com.montfel.qualeonumero.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.montfel.qualeonumero.R;
import com.montfel.qualeonumero.model.Numero;
import com.montfel.qualeonumero.service.HttpService;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private Button btnEnviar, btnNovaPartida;
    private TextView tvQtdNumeros, tvStatus, tvNumero;
    private EditText etPalpite;
    private int num;
    private int palpite;
    private Slider sliderTamanhoTexto;
    private View included1, included2, included3;
    private ImageView top, top_left, top_right, middle, bottom_left, bottom_right, bottom;
    private ImageView top2, top_left2, top_right2, middle2, bottom_left2, bottom_right2, bottom2;
    private ImageView top3, top_left3, top_right3, middle3, bottom_left3, bottom_right3, bottom3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.qual_numero));
//        LedSeteSegmentos led = new LedSeteSegmentos(this);
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
        tvStatus = findViewById(R.id.tvStatus);
//        tvNumero = findViewById(R.id.tvNumero);
        tvQtdNumeros = findViewById(R.id.tvQtdNumeros);
        etPalpite = findViewById(R.id.etPalpite);
        sliderTamanhoTexto = findViewById(R.id.sliderTamanhoTexto);
        included1 = findViewById(R.id.included1);
        included2 = findViewById(R.id.included2);
        included3 = findViewById(R.id.included3);

        top = findViewById(R.id.top);
        top_left = findViewById(R.id.top_left);
        top_right = findViewById(R.id.top_right);
        middle = findViewById(R.id.middle);
        bottom_left = findViewById(R.id.bottom_left);
        bottom_right = findViewById(R.id.bottom_right);
        bottom = findViewById(R.id.bottom);

        top2 = findViewById(R.id.top2);
        top_left2 = findViewById(R.id.top_left2);
        top_right2 = findViewById(R.id.top_right2);
        middle2 = findViewById(R.id.middle2);
        bottom_left2 = findViewById(R.id.bottom_left2);
        bottom_right2 = findViewById(R.id.bottom_right2);
        bottom2 = findViewById(R.id.bottom2);

        top3= findViewById(R.id.top3);
        top_left3 = findViewById(R.id.top_left3);
        top_right3 = findViewById(R.id.top_right3);
        middle3 = findViewById(R.id.middle3);
        bottom_left3 = findViewById(R.id.bottom_left3);
        bottom_right3 = findViewById(R.id.bottom_right3);
        bottom3 = findViewById(R.id.bottom3);

        sliderTamanhoTexto.setValue(3);
        tvStatus.setVisibility(View.INVISIBLE);
        btnNovaPartida.setVisibility(View.INVISIBLE);
        included1.setVisibility(View.GONE);
        included2.setVisibility(View.GONE);

        middle3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
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
            pintaLed(palpite);
//            tvNumero.setText(String.valueOf(palpite));
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
//            tvNumero.setText(numero.getValue());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void pintaLed(int numero) {
        top.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        top_left.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        top_right.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        middle.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom_left.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom_right.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        top2.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        top_left2.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        top_right2.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        middle2.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom_left2.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom_right2.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom2.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        top3.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        top_left3.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        top_right3.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        middle3.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom_left3.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom_right3.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom3.setColorFilter(ContextCompat.getColor(this, R.color.rosa_claro));

        included1.setVisibility(View.VISIBLE);
        included2.setVisibility(View.VISIBLE);
        included2.setVisibility(View.VISIBLE);

        String nume = String.valueOf(numero);

        if (numero < 10) {
            included1.setVisibility(View.GONE);
            included2.setVisibility(View.GONE);
            switchLed3(nume.substring(nume.length() - 1));
        } else if (numero < 100) {
            included1.setVisibility(View.GONE);
            switchLed2(nume.substring(nume.length() - 2, nume.length() - 1));
            switchLed3(nume.substring(nume.length() - 1));
        } else {
            switchLed1(nume.substring(nume.length() - 3,nume.length() - 2));
            switchLed2(nume.substring(nume.length() - 2,nume.length() - 1));
            switchLed3(nume.substring(nume.length() - 1));
        }
    }

    private void switchLed1(String numero) {
        switch (numero) {
            case "1":
                top.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                top_left.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                middle.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "2":
                top_left.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_right.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "3":
                top_left.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "4":
                top.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "5":
                top_right.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "6":
                top_right.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "7":
                top_left.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                middle.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "8":
                break;
            case "9":
                bottom_left.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "0":
                middle.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
        }
    }
    private void switchLed2(String numero) {
        switch (numero) {
            case "1":
                top2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                top_left2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                middle2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "2":
                top_left2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_right2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "3":
                top_left2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "4":
                top2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "5":
                top_right2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "6":
                top_right2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "7":
                top_left2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                middle2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "8":
                break;
            case "9":
                bottom_left2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "0":
                middle2.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
        }
    }
    private void switchLed3(String numero) {
        switch (numero) {
            case "1":
                top3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                top_left3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                middle3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "2":
                top_left3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_right3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "3":
                top_left3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "4":
                top3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "5":
                top_right3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "6":
                top_right3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "7":
                top_left3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                middle3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "8":
                break;
            case "9":
                bottom_left3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "0":
                middle3.setColorFilter(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
        }
    }
}