package com.example.spruce.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spruce.models.PostModel
import com.example.spruce.repos.DataManager
import com.example.spruce.repos.PostRepository
import com.example.spruce.repos.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {

    private val _postDataFlow = MutableStateFlow<Resource<List<PostModel>>?>(null)
    val postDataFlow: StateFlow<Resource<List<PostModel>>?> = _postDataFlow

    var isLastPageReached = false

    var mPage = 1

    private fun getPostData(page: Int = mPage, perPage: Int = 15) = viewModelScope.launch {
        if (!isLastPageReached) {
            _postDataFlow.value = Resource.Loading
            val result: Resource<List<PostModel>> =
                postRepository.getPost(page = page, perPage = perPage)
            _postDataFlow.value = result
        } else {
            _postDataFlow.value = Resource.Failure(Exception("Last Page Reached"))
        }
    }

    init {
        if (_postDataFlow.value == null)
            getPostData()
    }

}