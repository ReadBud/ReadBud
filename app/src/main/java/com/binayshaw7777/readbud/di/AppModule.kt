package com.binayshaw7777.readbud.di


import android.app.Application
import android.content.Context
import com.binayshaw7777.readbud.data.datastore.DataStoreUtil
import com.binayshaw7777.readbud.data.repository.ScansRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideDataStoreUtil(@ApplicationContext context: Context):DataStoreUtil = DataStoreUtil(context)

    @Provides
    fun provideScansRepository(application: Application): ScansRepository {
        return ScansRepository(application)
    }
}