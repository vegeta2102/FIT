package jp.co.vegeta.fit

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.vegeta.usecase.UseCaseCheckGuidance
import jp.co.vegeta.usecase.UseCaseCheckGuidanceImpl
import jp.co.vegeta.usecase.UseCaseFlowJob
import jp.co.vegeta.usecase.UseCaseFlowJobImpl

/**
 * Created by vegeta on 2021/06/30.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun provideUseCaseFlowJob(
        impl: UseCaseFlowJobImpl
    ): UseCaseFlowJob

    @Binds
    abstract fun provideUseCaseCheckGuidance(
        impl: UseCaseCheckGuidanceImpl
    ): UseCaseCheckGuidance
}