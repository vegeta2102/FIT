package jp.co.vegeta

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.vegeta.medium.MediumRepository
import jp.co.vegeta.medium.MediumRepositoryImpl
import jp.co.vegeta.progress.ProgressBarRepository
import jp.co.vegeta.progress.ProgressBarRepositoryImpl
import jp.co.vegeta.snackbar.SnackBarRepository
import jp.co.vegeta.snackbar.SnackBarRepositoryImpl
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

        @Provides
        @Singleton
        fun provideMediumRepository(): MediumRepository {
            return MediumRepositoryImpl()
        }

        @Provides
        @Singleton
        fun provideProgressBarRepository(): ProgressBarRepository {
            return ProgressBarRepositoryImpl()
        }
    }
}