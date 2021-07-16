package jp.co.vegeta.fit

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.fit.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by activityViewModels()

    private val navigation: NavController by lazy {
        findNavController()
    }

    override fun onStart() {
        super.onStart()
        requestPermissionWithPermissionCheck()
    }

    @NeedsPermission(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun requestPermission() {
        // パーミッションが許可されていれば起動処理に遷移
        // initViewModel()
    }

    @OnPermissionDenied(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun onPermissionDenied() {
        // 拒否された場合は再度パーミッション要求する
        // DeviceOwnerがかかっていると権限ダイアログが表示されないため実質開発向け
        requestPermissionWithPermissionCheck()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentMainBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        lifecycleScope.launchWhenCreated {
            excel_reading.setOnClickListener {
                //viewModel.showSnackBarTest()
                navigation.navigate(R.id.main_to_excel_reading)
            }
            car.setOnClickListener {
                // viewModel.showSnackBar()
                navigation.navigate(R.id.main_to_paint)
            }
            flow.setOnClickListener {
                navigation.navigate(R.id.action_global_medium)
            }
            progressbar.setOnClickListener {
                navigation.navigate(R.id.main_to_progressbar)
            }
        }
    }
}