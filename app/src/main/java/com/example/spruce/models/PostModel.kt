package com.example.spruce.models

import com.google.gson.annotations.SerializedName

data class PostModel(
    val id: Int = 0,
    val date: String = "",
    val title: PostTitle = PostTitle(),
    val content: PostContent = PostContent(),
    val featured_media: Int = 0,
    val categories: List<Int> = emptyList(),
    val tags: List<Int> = emptyList(),
    val _links: PostLinks = PostLinks(),
    var cateResult: List<CategoryModel> = emptyList(),
    var tagsResult: List<CategoryModel> = emptyList(),
    var imageResult: PostImageModel = PostImageModel()
)

data class PostLinks(
    val author: List<PostsLink> = emptyList(),
    @SerializedName("wp:featuredmedia") val imageMediaLink: List<PostsLink> = emptyList(),
    @SerializedName("wp:term") val catTagLinks: List<PostsLink> = emptyList()
)

data class PostTitle(val rendered: String = "")

data class PostContent(var rendered: String = "")
