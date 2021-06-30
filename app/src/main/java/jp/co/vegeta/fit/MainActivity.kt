package jp.co.vegeta.fit

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.internal.ViewUtils.getContentView
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.car.view.Visibility
import jp.co.vegeta.fit.databinding.ActivityMainBinding
import jp.co.vegeta.startup.StartupViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
                Log.d(TAG, "init event")
                navController.navigate(R.id.action_to_main)
            }
        }
        with(mainViewModel) {
            requestDialogMessage.observe(this@MainActivity) {
                AlertDialog.Builder(this@MainActivity)
                    .setMessage(it)
                    .create()
                    .show()
            }
        }
    }

    private fun setupTextView() {
        /*snack_bar.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.transition_up
            ).apply {
                startOffset = 500L
            }
        )*/
    }
}