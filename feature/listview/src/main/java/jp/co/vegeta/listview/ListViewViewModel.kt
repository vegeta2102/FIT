package jp.co.vegeta.listview

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.vegeta.route.RouteRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by vegeta on 2021/09/04.
 */
class ListViewViewModel @ViewModelInject constructor(
    private val routeRepository: RouteRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            routeRepository.data.collect {
                Log.d("ListView", "${it.turnInfo}")
            }
        }
    }

    fun start() {
        viewModelScope.launch {
            routeRepository.start()
        }
    }
}