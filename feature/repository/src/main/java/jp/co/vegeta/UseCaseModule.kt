package jp.co.vegeta

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.vegeta.route.RouteRepository

/**
 * Created by vegeta on 2021/12/03.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    companion object {
        @Provides
        fun provideUseCaseRoute(routeRepository: RouteRepository): UseCaseRoute {
            return UseCaseRoute(routeRepository)
        }
    }
}
