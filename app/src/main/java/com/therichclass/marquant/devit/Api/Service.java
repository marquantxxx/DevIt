package com.therichclass.marquant.devit.Api;

import com.therichclass.marquant.devit.Model.ItemResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by marquant on 8/28/2017.
 */

public interface Service {
    @GET("/search/users?q=language:java+location:lagos")
    Call<ItemResponse> getItems(@QueryMap Map<String, String> page);

}
