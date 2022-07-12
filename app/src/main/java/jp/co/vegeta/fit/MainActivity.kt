package jp.co.vegeta.fit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.core.dialog.MessageBoxFragment
import jp.co.vegeta.fit.databinding.ActivityMainBinding
import jp.co.vegeta.startup.StartupViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.simpleName
    }

    private val startupViewModel: StartupViewModel by viewModels()
    private lateinit var viewDataBinding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    private val navController: NavController by lazy {
        // https://stackoverflow.com/questions/58703451/fragmentcontainerview-as-navhostfragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewModel = mainViewModel
        with(startupViewModel) {
            initFinished.observe(this@MainActivity) {
                Timber.d("Init Finished")
                // navController.navigate(R.id.action_to_search)
                navController.navigate(R.id.action_to_suggest_search)
                // navController.navigate(R.id.action_to_voice_recognize)
            }
        }

        with(mainViewModel) {
            showDialog.observe(this@MainActivity) {
                Timber.d("Dialog come here for showing $it")
                it.message?.let {
                    MessageBoxFragment.show(supportFragmentManager, "SHOW", it)
                }
            }
            hideDialog.observe(this@MainActivity) {
                Timber.d("Dialog come here for hidding")
                MessageBoxFragment.hide(supportFragmentManager, "SHOW")
            }
        }
    }
}
