package jp.co.vegeta.fit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.telephony.TelephonyManager
import app.mobilitytechnologies.lib.map.MapComponent
import app.mobilitytechnologies.lib.map.MapLogger
import app.mobilitytechnologies.lib.map.globalnavi.GNaviAuthParameter
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipInputStream
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume

class MapModuleManager @Inject constructor(
    private val context: Context,
    private val mapComponent: MapComponent,
) {
    private val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.IO

    private val scope = CoroutineScope(coroutineContext)

    companion object {
        const val ACTION_GET_IMEI = "jp.co.vegeta.fit.GET_IMEI"
        const val EXTRA_VALUE = "jp.co.vegeta.fit.VALUE"
        const val EXTRA_RESULT = "jp.co.vegeta.fit.RESULT"
        private const val TIMEOUT = 3000L
    }

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
            personalId = "IMEI:${getImei(context, "jp.co.vegeta.fit")}",
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

    suspend fun getImei(context: Context, packageName: String): String? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            // Q未満の端末ではDeviceOwnerに設定されていなくてもREAD_PHONE権限があればIMEIを取得できる
            runCatching {
                val telephonyManager =
                    context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                telephonyManager.imei
                    ?.let {
                        return it
                    }
            }
        }
        val intent = Intent(ACTION_GET_IMEI).apply {
            `package` = packageName
        }
        return get(context, intent)
    }

    suspend fun get(
        context: Context,
        intent: Intent,
        timeout: Long = TIMEOUT,
    ) = suspendCancellableCoroutine<String?> { continuation ->
        val intentFilter = IntentFilter(intent.action)
        var timeoutJob: Job? = null
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, receiveIntent: Intent) {
                if (intent.action != receiveIntent.action) return
                if (continuation.isActive) {
                    continuation.resume(receiveIntent.getStringExtra(EXTRA_VALUE))
                    if (timeoutJob?.isActive == true) timeoutJob?.cancel()
                }
            }
        }
        scope.launch {
            try {
                withContext(Dispatchers.Default) {
                    context.registerReceiver(receiver, intentFilter)
                    startService(context, intent)
                    timeoutJob = launch {
                        delay(timeout)
                        throw ServiceTimeoutException()
                    }
                }
            } catch (e: ServiceTimeoutException) {
                if (continuation.isActive) continuation.resume(null)
            } catch (e: ServiceNotFoundException) {
                if (continuation.isActive) continuation.resume(null)
            } catch (e: Exception) {
                if (continuation.isActive) continuation.resume(null)
            } finally {
                context.unregisterReceiver(receiver)
            }
        }
    }

    private fun startService(context: Context, intent: Intent) {
        context.startForegroundService(intent)
            ?: throw ServiceNotFoundException(intent.`package`)
    }
}

class ServiceTimeoutException() : Throwable()
class ServiceNotFoundException(val `package`: String? = null) : Throwable()