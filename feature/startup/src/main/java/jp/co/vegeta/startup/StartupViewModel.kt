package jp.co.vegeta.startup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.vegeta.user.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by vegeta on 2021/01/23.
 */
@HiltViewModel
class StartupViewModel @Inject constructor(
    private val userRepository: UserRepository
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
