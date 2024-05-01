package com.example.spruce.repos

import com.example.spruce.models.CategoryModel

interface CategoryRepository {

    suspend fun getCategories(): Resource<List<CategoryModel>>

}