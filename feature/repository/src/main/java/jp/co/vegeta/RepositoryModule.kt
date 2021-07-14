package jp.co.vegeta

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.vegeta.dialog.DialogMessageRepository
import jp.co.vegeta.dialog.DialogMessageRepositoryImpl
import jp.co.vegeta.medium.MediumRepository
import jp.co.vegeta.medium.MediumRepositoryImpl
import jp.co.vegeta.progress.ProgressBarRepository
import jp.co.vegeta.progress.ProgressBarRepositoryImpl
import jp.co.vegeta.snackbar.SnackBarRepository
import jp.co.vegeta.snackbar.SnackBarRepositoryImpl
import jp.co.vegeta.test_repo.*
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
        fun provideDialogMessageRepository(): DialogMessageRepository {
            return DialogMessageRepositoryImpl()
        }

        @Provides
        @Singleton
        fun provideProgressBarRepository(): ProgressBarRepository {
            return ProgressBarRepositoryImpl()
        }

        @Provides
        @Singleton
        fun provideGetJobRepository(): FlowGetJobRepository {
            return FlowGetJobRepositoryImpl()
        }

        @Provides
        @Singleton
        fun provideShowJobRepository(): FlowShowJobRepository {
            return FlowShowJobRepositoryImpl()
        }

        @Provides
        @Singleton
        fun provideGuidanceStatusRepository(): GuidanceStatusRepository {
            return GuidanceStatusRepositoryImpl()
        }
    }
}