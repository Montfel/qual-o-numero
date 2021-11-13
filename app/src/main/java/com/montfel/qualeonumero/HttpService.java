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
//        int code = 0;
//        String output = "";
        try {
            URL url = new URL("https://us-central1-ss-devops.cloudfunctions.net/rand?min=1&max=300");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
//            code = connection.getResponseCode();
//            Log.i("TAG", "responde code: " + code);
//            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
//            String line;
//            while ((line = br.readLine()) != null) {
//                output += line;
//            }
//            connection.disconnect();
            connection.setDoOutput(true);
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

        } catch (IOException e) {
            e.printStackTrace();
            //            Log.i("TAG", "responde code catch: " + code);

        }
        //        Log.i("TAG", "doInBackground: " + resposta.toString());

        Log.i("TAG", "doInBackground: " + resposta.toString());
        return new Gson().fromJson(resposta.toString(), Numero.class);
        //        System.out.println("STATES: " + Arrays.toString(dados.getError()[0]));
//        return new Gson().fromJson(new String(output.getBytes()), Numero.class);
    }
}
