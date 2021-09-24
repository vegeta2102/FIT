package jp.co.vegeta.fit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import app.mobilitytechnologies.lib.map.model.MapDirection
import app.mobilitytechnologies.lib.map.model.MapScaleFactory
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.fit.databinding.FragmentMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by activityViewModels()

    private val navigation: NavController by lazy {
        findNavController()
    }

    @Inject
    lateinit var mapScaleFactory: MapScaleFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mapbox.getInstance(requireContext(), getString(R.string.mapbox_access_token))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentMainBinding.bind(view).also { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                val mapPresenter = binding.map.initMap()
                mapPresenter.mapDirection = MapDirection.HeadUp
            }
        }
    }
}
