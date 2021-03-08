package jp.co.vegeta.snackbar

import jp.co.vegeta.model.SnackBar
import kotlinx.coroutines.flow.Flow

/**
 * Created by vegeta on 2021/02/15.
 */
interface SnackBarRepository {
    val data: Flow<SnackBar?>
    suspend fun push(value: SnackBar)
}