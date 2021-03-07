package jp.co.vegeta.snackbar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by vegeta on 2021/02/15.
 */
@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    companion object {
        @Provides
        @Singleton
        fun provideSnackBarRepository(): SnackBarRepository {
            return SnackBarRepositoryImpl()
        }
    }
}