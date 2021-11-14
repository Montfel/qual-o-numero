package com.montfel.qualeonumero.service;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.montfel.qualeonumero.model.Numero;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class HttpService extends AsyncTask<Void, Void, Numero> {
    private URL url;
    private int code;

    @Override
    protected Numero doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
//        String output = "";
        try {
            url = new URL("https://us-central1-ss-devops.cloudfunctions.net/rand?min=1&max=300");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
//            code = connection.getResponseCode();
//            if (code == 502) {
//                Scanner scanner = new Scanner(url.openStream());
//                while (scanner.hasNext()) {
//                    resposta.append(scanner.next());
//                }
//                Gson gson = new Gson();
//                Numero dados = gson.fromJson(resposta.toString(), Numero.class);
//                Log.i("TAG", "dados: " + dados);
//                return dados;
//            }
            Log.i("TAG", "responde code: " + code);
//            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
//            String line;
//            while ((line = br.readLine()) != null) {
//                output += line;
//            }
//            connection.disconnect();
//            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }

            //            Gson gson = new Gson();
//            Numero dados = gson.fromJson(new String(output.getBytes()), Numero.class);
//
//            System.out.println("Valor: " + dados.getValue());

        Log.i("TAG", "doInBackground: " + resposta.toString());
        return new Gson().fromJson(resposta.toString(), Numero.class);
        } catch (IOException e) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(url.openStream());
            } catch (IOException ioException) {
                ioException.printStackTrace();
                Log.i("TAG", "deu ruim: ");
            }
            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }
            Gson gson = new Gson();
            Numero dados = gson.fromJson(resposta.toString(), Numero.class);
            Log.i("TAG", "dados: " + dados);
//            e.printStackTrace();
            Log.i("TAG", "responde code catch: " + code);
            Log.i("TAG", "responde code catch: " + resposta.toString());
            return new Gson().fromJson(resposta.toString(), Numero.class);
        }
        //        Log.i("TAG", "doInBackground: " + resposta.toString());
        //        System.out.println("STATES: " + Arrays.toString(dados.getError()[0]));
//        return new Gson().fromJson(new String(output.getBytes()), Numero.class);
    }
}