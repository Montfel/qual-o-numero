package com.montfel.qualeonumero.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.montfel.qualeonumero.api.JsonPlaceHolderApi;
import com.montfel.qualeonumero.R;
import com.montfel.qualeonumero.model.Numero;
import com.montfel.qualeonumero.view.LedSeteSegmentos;

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
    private final String BASE_URL = "https://us-central1-ss-devops.cloudfunctions.net/";
    private LedSeteSegmentos ledCentena, ledDezena, ledUnidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.qual_numero));

        inicializaComponentes();
        mostraTamanhoInput();
        configuraRetrofit();
        realizaPalpite();
        jogaNovamente();
    }

    //Exibe os menus na Appbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_configuracao_texto, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Configura ações aos botões ao serem pressionados
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Verifica se o item selecionado foi o menu de alterar texto
        if (item.getItemId() == R.id.menuTamanhoTexto) {
            boolean isSliderVisible = sliderTamanhoTexto.getVisibility() == View.VISIBLE;

            sliderTamanhoTexto.setVisibility(isSliderVisible ? View.INVISIBLE : View.VISIBLE);

            sliderTamanhoTexto.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
                @Override
                public void onStartTrackingTouch(@NonNull Slider slider) {}

                @Override
                public void onStopTrackingTouch(@NonNull Slider slider) {
//                        tvNumero.setTextSize((slider.getValue() * 20) + 40);
                }
            });

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
        ledCentena = findViewById(R.id.ledCentena);
        ledDezena = findViewById(R.id.ledDezena);
        ledUnidade = findViewById(R.id.ledUnidade);

        //Inicia invisível ou Gone elementos que só aparecerão devido a alguma interação do usuário
        tvStatus.setVisibility(View.INVISIBLE);
        btnNovaPartida.setVisibility(View.INVISIBLE);
        ledCentena.setVisibility(View.GONE);
        ledDezena.setVisibility(View.GONE);

        //Inicia o slider com o tamanho médio
        sliderTamanhoTexto.setValue(3);

        //Configura para aparecer o número zero ao iniciar o aplicativo
        ledUnidade.switchColor("0");
    }

    private void mostraTamanhoInput() {
        etPalpite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Muda o textView para indicar o comprimento do input ao passo que o usuário interage com o campo de texto
                String tamanho = charSequence.length() + getString(R.string.slash_3);
                tvQtdNumeros.setText(tamanho);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void configuraRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Numero> call = jsonPlaceHolderApi.getNumero();

        call.enqueue(new Callback<Numero>() {
            @Override
            public void onResponse(@NonNull Call<Numero> call, @NonNull Response<Numero> response) {
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
            public void onFailure(@NonNull Call<Numero> call, @NonNull Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void realizaPalpite() {
        btnEnviar.setOnClickListener(view -> {
            //Verifica se o editText é nulo ao pressionar o botão
            if (!etPalpite.getText().toString().equals("")) {
                palpite = Integer.parseInt(etPalpite.getText().toString());
                tvStatus.setVisibility(View.VISIBLE);
                //Mostra o número do palpite na tela a partir dos leds
                pintaLed(palpite);
                //Muda o status a depender do palpite do usuário
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
            //Realiza outra requisição, esconde o botão de nova partida e desbloqueia o botão de enviar palpite
            btnNovaPartida.setVisibility(View.INVISIBLE);
            btnEnviar.setEnabled(true);
            configuraRetrofit();
        });
    }

    public void pintaLed(int numero) {
        ledCentena.setVisibility(View.VISIBLE);
        ledDezena.setVisibility(View.VISIBLE);

        String nume = String.valueOf(numero);

        if (numero < 10) {
            ledCentena.setVisibility(View.GONE);
            ledDezena.setVisibility(View.GONE);
            ledUnidade.switchColor(nume.substring(nume.length() - 1));
        } else if (numero < 100) {
            ledCentena.setVisibility(View.GONE);
            ledDezena.switchColor(nume.substring(nume.length() - 2, nume.length() - 1));
            ledUnidade.switchColor(nume.substring(nume.length() - 1));
        } else {
            ledCentena.switchColor(nume.substring(nume.length() - 3,nume.length() - 2));
            ledDezena.switchColor(nume.substring(nume.length() - 2, nume.length() - 1));
            ledUnidade.switchColor(nume.substring(nume.length() - 1));
        }
    }
}