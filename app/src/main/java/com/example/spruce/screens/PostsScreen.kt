package com.example.spruce.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spruce.models.PostModel
import com.example.spruce.repos.DataManager

@Composable
fun PostsScreen(
    allPosts: List<PostModel> = DataManager.allPosts,
    postDetail: (postmodel: PostModel) -> Unit
) {

    LazyColumn {
        items(allPosts.size) {
            FeaturedPostCard(modifier = Modifier.padding(16.dp), postModel = allPosts[it]) {
                postDetail(it)
            }
        }
    }

}