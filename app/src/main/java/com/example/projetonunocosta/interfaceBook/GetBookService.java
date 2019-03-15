package com.example.projetonunocosta.interfaceBook;

import com.example.projetonunocosta.model.Item;
import com.example.projetonunocosta.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetBookService {

    @GET("volumes")
    Call<Result> getResults(@Query("q") String title);


    @GET("volumes/{id}")
    Call<Item> getItemDetail(@Path("id") String id);

 /*       @GET("beers/{id}") // Parametro que passamos para o serviço, {valor} e colocamos o "valor" no metodo
        Call<List<Result>> getBeerDetail(@Path("id") String id); // valor que passamos
*/
}


