package com.binar.movieapp.di

import com.binar.movieapp.data.repository.MovieRepository
import com.binar.movieapp.data.repository.MovieRepositoryImpl
import com.binar.movieapp.data.repository.UserRepository
import com.binar.movieapp.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}