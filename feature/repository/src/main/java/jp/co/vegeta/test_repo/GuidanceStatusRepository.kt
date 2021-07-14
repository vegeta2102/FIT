package jp.co.vegeta.test_repo

import kotlinx.coroutines.flow.Flow

/**
 * Created by vegeta on 2021/07/08.
 */
interface GuidanceStatusRepository {
    val guidance: Flow<Boolean>
}