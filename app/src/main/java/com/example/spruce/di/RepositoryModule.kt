package com.example.spruce.di

import com.example.spruce.repos.CategoryRepository
import com.example.spruce.repos.CategoryRepositoryImpl
import com.example.spruce.repos.PostRepository
import com.example.spruce.repos.PostRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository = impl

    @Provides
    fun providePostRepository(impl: PostRepositoryImpl): PostRepository = impl

}