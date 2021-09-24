package jp.co.vegeta.mapbox.map

import android.content.Context
import app.mobilitytechnologies.lib.map.MapComponent
import app.mobilitytechnologies.lib.map.MapLogger
import app.mobilitytechnologies.lib.map.globalnavi.GNaviAuthParameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipInputStream
import javax.inject.Inject

class MapModuleManager @Inject constructor(
    private val context: Context,
    private val mapComponent: MapComponent,
) {
    suspend fun initMap() {
        withContext(Dispatchers.IO) {
            // 地図テーマをassetsからストレージへコピー
            val basePath = "${context.filesDir}/mapCustomize"
            copyMapThemeZip("mapCustomize.zip", basePath)
        }

        // OneID認証の情報
        /*
        val clientId = "21mm_mobile_navi_gw"
        val authParameter = GNaviAuthParameter.oneIdAuth("Bearer sgmkt1FsxWkbOVXZhOjwt7Uo-8v5_Z2atZe7TcaKVS8")
        */
        // クライアント認証の情報
        val clientId = "motclient"
        val authParameter = GNaviAuthParameter.clientAuth(
            clientId = "34f3o8kgqm4gvtnjjkhp6pqbrq",
            clientSecret = "o9aenfkbgd4fcunr4s1v0jd0e4d7qk35ca5mch5i7s834s3c76u",
            scope = "comlbsjp-agw-cupm/client_m"
        )

        // 地図の初期化
        // ここはStrict Modeで警告が出るがIOスレッドに変更すると
        // Attempt to read from field 'android.os.MessageQueue android.os.Looper.mQueue' on a null object reference
        // がグロナビSDK内部で発火するのでメインスレッド内で実行する
        mapComponent.init(
            context = context,
            appId = context.packageName,
            customerKey = "612Hp#~96<189$#YI5S_3QjX766C=~",
            apiKey = "9wR5S57Cjb3QfdXKcD1e1caFsjAsiVKasVqrCXV4",
            clientId = clientId,
            sessionId = "11111",
            personalId = "mobilitytechnologies",
            authParameter = authParameter,
            mapLogger = object : MapLogger {
                override fun d(tag: String, message: String) {
                    Timber.tag(tag).d(message)
                }

                override fun e(tag: String, message: String) {
                    Timber.tag(tag).e(message)
                }

                override fun e(tag: String, throwable: Throwable) {
                    Timber.tag(tag).e(throwable)
                }

                override fun i(tag: String, message: String) {
                    Timber.tag(tag).i(message)
                }

                override fun w(tag: String, message: String) {
                    Timber.tag(tag).w(message)
                }
            }
        )
        Timber.d("sdkVersion=${mapComponent.sdkVersion}")
    }

    private fun copyMapThemeZip(zipPath: String, basePath: String) {
        context.assets.open(zipPath).use { input ->
            // 展開先の作成
            val dir = File(basePath)
            if (dir.exists()) {
                dir.delete()
            }
            dir.mkdir()

            ZipInputStream(input).use { zip ->
                while (true) {
                    val zipEntry = zip.nextEntry ?: break
                    val entryFile = File(zipEntry.name)
                    val fileName = "$basePath/${entryFile.path}"

                    if (zipEntry.isDirectory) {
                        File(fileName).mkdir()
                    } else {
                        // Zipの解凍
                        BufferedOutputStream(
                            FileOutputStream(fileName)
                        ).use { output ->
                            while (true) {
                                val buffer = ByteArray(1024)
                                val len = zip.read(buffer)
                                if (len == -1) break
                                output.write(buffer, 0, len)
                            }
                        }
                    }
                }
            }
        }
    }
}
