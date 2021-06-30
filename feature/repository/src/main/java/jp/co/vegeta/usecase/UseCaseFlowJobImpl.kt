package jp.co.vegeta.usecase

import jp.co.vegeta.test_repo.FlowGetJobRepository
import jp.co.vegeta.test_repo.FlowShowJobRepository
import javax.inject.Inject

/**
 * Created by vegeta on 2021/06/30.
 */
class UseCaseFlowJobImpl @Inject constructor(
    private val flowGetJobRepository: FlowGetJobRepository,
    private val flowShowJobRepository: FlowShowJobRepository
) : UseCaseFlowJob {
    override suspend fun execute() {
        flowGetJobRepository.get()
        flowShowJobRepository.show()
    }
}