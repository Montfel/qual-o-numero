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
import com.montfel.qualeonumero.api.JsonPlaceHolderApi;
import com.montfel.qualeonumero.R;
import com.montfel.qualeonumero.model.Numero;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button btnEnviar, btnNovaPartida;
    private TextView tvQtdNumeros, tvStatus;
    private EditText etPalpite;
    private int numero;
    private int palpite;
    private Slider sliderTamanhoTexto;
    private View included1, included2, included3;
    private View top, top_left, top_right, middle, bottom_left, bottom_right, bottom;
    private View top2, top_left2, top_right2, middle2, bottom_left2, bottom_right2, bottom2;
    private View top3, top_left3, top_right3, middle3, bottom_left3, bottom_right3, bottom3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.qual_numero));

        inicializaComponentes();
        trataInput();
        configuraRetrofit();
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
//                        tvNumero.setTextSize((slider.getValue() * 20) + 40);
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

        middle3.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
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

    private void configuraRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-ss-devops.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Numero> call = jsonPlaceHolderApi.getNumero();

        call.enqueue(new Callback<Numero>() {
            @Override
            public void onResponse(Call<Numero> call, Response<Numero> response) {
                if (!response.isSuccessful()) {
                    tvStatus.setVisibility(View.VISIBLE);
                    tvStatus.setText(R.string.erro);
                    pintaLed(response.code());
                    btnNovaPartida.setVisibility(View.VISIBLE);
                    btnEnviar.setEnabled(false);
                } else {
                    numero = Integer.parseInt(response.body().getValue());
                }
            }

            @Override
            public void onFailure(Call<Numero> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void realizaPalpite() {
        btnEnviar.setOnClickListener(view -> {
            if (!etPalpite.getText().toString().equals("")) {
                palpite = Integer.parseInt(etPalpite.getText().toString());
                tvStatus.setVisibility(View.VISIBLE);
                pintaLed(palpite);
                if (numero < palpite) {
                    tvStatus.setText(R.string.menor);
                } else if (numero > palpite) {
                    tvStatus.setText(R.string.maior);
                } else {
                    tvStatus.setText(R.string.acertou);
                    btnNovaPartida.setVisibility(View.VISIBLE);
                    btnEnviar.setEnabled(false);
                }
            }
        });
    }

    private void jogaNovamente() {
        btnNovaPartida.setOnClickListener(view -> {
            btnNovaPartida.setVisibility(View.INVISIBLE);
            btnEnviar.setEnabled(true);
            configuraRetrofit();
        });
    }

    public void pintaLed(int numero) {
        top.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        top_left.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        top_right.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        middle.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom_left.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom_right.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));

        top2.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        top_left2.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        top_right2.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        middle2.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom_left2.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom_right2.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom2.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));

        top3.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        top_left3.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        top_right3.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        middle3.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom_left3.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom_right3.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));
        bottom3.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa_claro));

        included1.setVisibility(View.VISIBLE);
        included2.setVisibility(View.VISIBLE);
        included2.setVisibility(View.VISIBLE);

        String nume = String.valueOf(numero);

        if (numero < 10) {
            included1.setVisibility(View.GONE);
            included2.setVisibility(View.GONE);
            switchLed(nume.substring(nume.length() - 1), top3, top_left3, top_right3, middle3,
                    bottom_left3, bottom_right3, bottom3);
        } else if (numero < 100) {
            included1.setVisibility(View.GONE);
            switchLed(nume.substring(nume.length() - 2, nume.length() - 1), top2, top_left2,
                    top_right2, middle2, bottom_left2, bottom_right2, bottom2);
            switchLed(nume.substring(nume.length() - 1), top3, top_left3,
                    top_right3, middle3, bottom_left3, bottom_right3, bottom3);
        } else {
            switchLed(nume.substring(nume.length() - 3,nume.length() - 2), top, top_left, top_right,
                    middle, bottom_left, bottom_right, bottom);
            switchLed(nume.substring(nume.length() - 2, nume.length() - 1), top2, top_left2,
                    top_right2, middle2, bottom_left2, bottom_right2, bottom2);
            switchLed(nume.substring(nume.length() - 1), top3, top_left3, top_right3, middle3,
                    bottom_left3, bottom_right3, bottom3);
        }
    }

    private void switchLed(String numero, View top, View top_left, View top_right, View middle,
                            View bottom_left, View bottom_right, View bottom) {
        switch (numero) {
            case "1":
                top.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                top_left.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                middle.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "2":
                top_left.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_right.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "3":
                top_left.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "4":
                top.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "5":
                top_right.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "6":
                top_right.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "7":
                top_left.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                middle.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom_left.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                bottom.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "8":
                break;
            case "9":
                bottom_left.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
            case "0":
                middle.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_claro));
                break;
        }
    }
}