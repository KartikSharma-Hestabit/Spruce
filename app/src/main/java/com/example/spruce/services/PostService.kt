package com.example.spruce.services

import com.example.spruce.models.CategoryModel
import com.example.spruce.models.PostImageModel
import com.example.spruce.models.PostModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {

    @GET("wp-json/wp/v2/posts")
    suspend fun getPosts(
        @Query("per_page") perPage: Int = 5,
        @Query("page") page: Int = 1
    ): List<PostModel>

    @GET("index.php//wp-json/wp/v2/categories")
    suspend fun getPostCategories(
        @Query("post") postId: Int
    ): List<CategoryModel>

    @GET("index.php/wp-json/wp/v2/tags")
    suspend fun getPostTags(
        @Query("post") postId: Int
    ): List<CategoryModel>

    @GET("index.php/wp-json/wp/v2/media/{id}")
    suspend fun getPostImage(
        @Path("id") mediaId: Int
    ): PostImageModel

}