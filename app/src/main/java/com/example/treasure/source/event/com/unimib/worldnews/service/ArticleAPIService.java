package com.example.treasure.source.event.com.unimib.worldnews.service;

import static com.unimib.worldnews.util.Constants.*;

import com.unimib.worldnews.model.ArticleAPIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ArticleAPIService {
    @GET(TOP_HEADLINES_ENDPOINT)
    Call<ArticleAPIResponse> getArticles(
            @Query(TOP_HEADLINES_COUNTRY_PARAMETER) String country,
            @Query(TOP_HEADLINES_PAGE_SIZE_PARAMETER) int pageSize,
            @Header("Authorization") String apiKey);
}
