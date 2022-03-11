package jp.co.vegeta

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.co.vegeta.core.extentions.ResourceProvider
import jp.co.vegeta.core.extentions.ResourceProviderImpl
import jp.co.vegeta.user.UserRepository
import jp.co.vegeta.user.UserRepositoryImpl
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
        fun provideUserRepository(): UserRepository {
            return UserRepositoryImpl()
        }

        @Provides
        @Singleton
        fun provideResourceRepository(@ApplicationContext context: Context): ResourceProvider {
            return ResourceProviderImpl(context)
        }
    }
}
