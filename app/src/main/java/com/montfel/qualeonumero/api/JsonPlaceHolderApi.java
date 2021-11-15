package com.montfel.qualeonumero.api;

import com.montfel.qualeonumero.model.Numero;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("rand?min=1&max=300")
    Call<Numero> getNumero();
}
