package com.hugo.comermelhor.data.services.openfoodapi.responses

import com.google.gson.annotations.SerializedName

data class ProductSearchResponse(
    val count: Int,
    val page: Int,
    @SerializedName("page_count") val pageCount: Int,
    @SerializedName("page_size") val pageSize: Int,
    val products: List<Product>
)

data class Product(
    @SerializedName("abbreviated_product_name") val abbreviatedProductName: String,
    val code: String,
    @SerializedName("code_tags") val codeTags: String,
    @SerializedName("generic_name") val genericName: String,
    val id: String,
    @SerializedName("product_name") val productName: String,
    @SerializedName("product_name_en") val productNameEn: String,
    @SerializedName("product_quantity") val productQuantity: String,
    @SerializedName("product_name_quantity_unit") val productNameQuantityUnit: String,
    @SerializedName("serving_quantity") val servingQuantity: String,
    @SerializedName("serving_quantity_unit") val servingQuantityUnit: String,
    val quantity: String,
    val nutriments: Nutriments
)

data class Nutriments(
    @SerializedName("energy-kcal") val energyKcal: String?
)