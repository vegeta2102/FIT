package jp.co.vegeta

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by vegeta on 2021/12/03.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    companion object {
        // Provider user case here
    }
}
