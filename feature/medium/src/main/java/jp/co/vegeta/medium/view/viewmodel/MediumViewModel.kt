package jp.co.vegeta.medium.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import jp.co.vegeta.medium.MediumRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

/**
 * Created by vegeta on 2021/03/08.
 */
class MediumViewModel @ViewModelInject constructor(
    private val mediumRepository: MediumRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            mediumRepository.fetch()
        }
    }

    val flowValue: LiveData<String>
        get() = mediumRepository.data.filterNotNull().asLiveData()

}