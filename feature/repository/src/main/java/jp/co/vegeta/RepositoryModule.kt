package jp.co.vegeta

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.vegeta.route.RouteRepository
import jp.co.vegeta.route.RouteRepositoryImpl
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
        fun provideRouteRepository(): RouteRepository {
            return RouteRepositoryImpl()
        }
    }
}
