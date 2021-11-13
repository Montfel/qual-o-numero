package com.montfel.qualeonumero;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class HttpService extends AsyncTask<Void, Void, Numero> {

    @Override
    protected Numero doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        try {
            URL url = new URL("https://us-central1-ss-devops.cloudfunctions.net/rand?min=1&max=300");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("TAG", "doInBackground: " + resposta.toString());
        return new Gson().fromJson(resposta.toString(), Numero.class);
    }
}
