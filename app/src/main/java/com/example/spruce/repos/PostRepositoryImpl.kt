package com.example.spruce.repos

import androidx.core.text.HtmlCompat
import com.example.spruce.models.PostModel
import com.example.spruce.services.CategoryService
import com.example.spruce.services.PostService
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postService: PostService,
    private val categoryService: CategoryService
) :
    PostRepository {

    override suspend fun getPost(page: Int, perPage: Int): Resource<List<PostModel>> {
        return try {
            val result: List<PostModel> = postService.getPosts(page = page, perPage = perPage)

            for (i in result.indices) {

                val catResult = postService.getPostCategories(result[i].id)
                val tagResult = postService.getPostTags(result[i].id)
                val imageResult = postService.getPostImage(result[i].featured_media)

                result[i].content.rendered = HtmlCompat.fromHtml(
                    result[i].content.rendered.trim(),
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                ).toString()
                result[i].cateResult = catResult
                result[i].tagsResult = tagResult
                result[i].imageResult = imageResult

            }

            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}