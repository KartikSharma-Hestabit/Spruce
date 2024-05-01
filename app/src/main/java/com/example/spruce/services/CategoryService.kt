package com.example.spruce.services

import com.example.spruce.models.CategoryModel
import retrofit2.http.GET

interface CategoryService {

    @GET("wp-json/wp/v2/categories")
    suspend fun getCategories(): List<CategoryModel>

}