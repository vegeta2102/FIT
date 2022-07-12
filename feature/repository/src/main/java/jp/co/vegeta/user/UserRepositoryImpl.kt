package jp.co.vegeta.user

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jp.co.vegeta.core.extentions.ResourceProvider
import jp.co.vegeta.model.President
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Created by vegeta on 2021/09/04.
 */
class UserRepositoryImpl @Inject constructor(
    private val resourceProvider: ResourceProvider
) : UserRepository {
    private val mutableData = MutableStateFlow(emptyList<President>())
    override val data: Flow<List<President>>
        get() = mutableData

    override suspend fun fetch() {
        val stringJson = resourceProvider.loadJson("president.json")
        val sType = object : TypeToken<List<President>>() {}.type
        val data = Gson().fromJson<List<President>>(stringJson, sType)
        mutableData.value = data
    }
}