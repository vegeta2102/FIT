package jp.co.vegeta.fit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.vegeta.dialog.DialogBoxRepository
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

/**
 * Created by vegeta on 2022/07/12.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val dialogBoxRepository: DialogBoxRepository
) : ViewModel() {

    val showDialog = dialogBoxRepository.requestShowDialog.filterNotNull().asLiveData()
    val hideDialog = dialogBoxRepository.requestHideDialog.filterNotNull().asLiveData()
}