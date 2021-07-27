package jp.co.vegeta.usecase

import jp.co.vegeta.medium.MediumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by vegeta on 2021/07/08.
 */
class UseCaseCheckGuidanceImpl @Inject constructor(
    private val mediumRepository: MediumRepository
) : UseCaseCheckGuidance {
    override fun execute(): Flow<String> {
        return mediumRepository.data.filterNotNull().map {
            it
        }
    }
}