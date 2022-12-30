package com.example.todolist.di

import com.example.todolist.data.db.AppDatabase
import com.example.todolist.domain.interactor.AddInteractor
import com.example.todolist.domain.interactor.AdditionalInteractor
import com.example.todolist.domain.interactor.MainInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideMainInteractor(db: AppDatabase): MainInteractor = MainInteractor.Base(db = db)

    @Provides
    fun provideAddInteractor(db: AppDatabase): AddInteractor = AddInteractor.Base(db = db)

    @Provides
    fun provideAdditionalInteractor(db: AppDatabase): AdditionalInteractor =
        AdditionalInteractor.Base(db = db)
}