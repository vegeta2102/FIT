package jp.co.vegeta.fit

import android.Manifest
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import app.mobilitytechnologies.lib.map.MapComponent
import app.mobilitytechnologies.lib.navi.globalnavi.flow.flowEnd
import app.mobilitytechnologies.lib.navi.globalnavi.flow.flowInit
import app.mobilitytechnologies.lib.navi.model.engine.NaviEngine
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.fit.databinding.ActivityMainBinding
import jp.co.vegeta.startup.StartupViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
@RuntimePermissions
class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.simpleName
    }

    private val viewModel: StartupViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var viewDataBinding: ActivityMainBinding
    /** メインのFragmentを制御するNavController */

    private val navController: NavController by lazy {
        // https://stackoverflow.com/questions/58703451/fragmentcontainerview-as-navhostfragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        ).apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.mainViewModel
        }
        with(viewModel) {
            initFinished.observe(this@MainActivity) {
                Timber.d("Init Finished")
                navController.navigate(R.id.action_to_main)
            }
        }

    }

    @NeedsPermission(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE,
    )
    fun init() {

    }
}
