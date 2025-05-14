package com.hugo.comermelhor.data.services.openfoodapi

import com.hugo.comermelhor.data.services.openfoodapi.responses.ProductSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenFoodApiService {
    @GET("api/v2/search")
    suspend fun searchProductByName(@Query("categories_tags_pt") productCategory: String): ProductSearchResponse
}