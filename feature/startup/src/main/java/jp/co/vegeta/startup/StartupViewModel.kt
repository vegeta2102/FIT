package jp.co.vegeta.startup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.vegeta.core.extentions.DataStoreKey
import jp.co.vegeta.core.extentions.get
import jp.co.vegeta.core.extentions.save
import jp.co.vegeta.datastore.LocalDataStore
import jp.co.vegeta.model.President
import jp.co.vegeta.model.UserItem
import jp.co.vegeta.user.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Created by vegeta on 2021/01/23.
 */
@HiltViewModel
class StartupViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localDataStore: LocalDataStore,
) : ViewModel() {

    companion object {
        val TAG = StartupViewModel::class.simpleName
    }

    private val mutableInitFinished = MutableLiveData<Unit>()
    val initFinished: LiveData<Unit>
        get() = mutableInitFinished

    fun initialize() {
        viewModelScope.launch {
            runCatching {
                localDataStore.dataStore.save(
                    DataStoreKey.KEY_MISYU_FROM_WIFI_METER_RECEIVE,
                    "Hoang"
                )
                localDataStore.dataStore.save(
                    DataStoreKey.KEY_OBJECT_TEST,
                    President(name = "Donal Trump")
                )
                userRepository.fetch()
            }.onSuccess {
                // FIXME
                delay(2000L)
                mutableInitFinished.postValue(Unit)
            }.onFailure {
                Timber.e("Loading failure $it")
            }
        }
    }
}
