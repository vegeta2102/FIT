package jp.co.vegeta.usecase

import kotlinx.coroutines.flow.Flow

/**
 * Created by vegeta on 2021/07/08.
 */
interface UseCaseCheckGuidance {
    fun execute() : Flow<String>
}