package com.example.spruce.repos

import com.example.spruce.services.CategoryService
import com.example.spruce.models.CategoryModel
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val categoryService: CategoryService) :
    CategoryRepository {

    override suspend fun getCategories(): Resource<List<CategoryModel>> {
        return try {
            val result = categoryService.getCategories()
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

}