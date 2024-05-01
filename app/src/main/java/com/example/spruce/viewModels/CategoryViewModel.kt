package com.example.spruce.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spruce.models.CategoryModel
import com.example.spruce.repos.CategoryRepositoryImpl
import com.example.spruce.repos.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepositoryImpl) :
    ViewModel() {

    private val _categoriesFlow = MutableStateFlow<Resource<List<CategoryModel>>?>(null)
    val categoryFlow: StateFlow<Resource<List<CategoryModel>>?> = _categoriesFlow

    private fun getCategories() = viewModelScope.launch {
        _categoriesFlow.value = Resource.Loading
        val result = categoryRepository.getCategories()
        _categoriesFlow.value = result
    }

    init {
        getCategories()
    }

}