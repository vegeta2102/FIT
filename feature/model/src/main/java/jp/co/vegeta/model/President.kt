package jp.co.vegeta.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by vegeta on 2022/03/11.
 */
data class President(
    @SerializedName("name")
    @Expose
    val name: String = "",
    @SerializedName("description")
    @Expose
    val description: String = "",
    @SerializedName("birth_death")
    @Expose
    val birthDeath: String = "",
)