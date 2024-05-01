package com.example.spruce.repos

import com.example.spruce.models.PostModel

interface PostRepository {

    suspend fun getPost(page: Int = 1, perPage: Int = 5): Resource<List<PostModel>>

}