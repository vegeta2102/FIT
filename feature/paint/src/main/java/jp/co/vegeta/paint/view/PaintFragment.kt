package jp.co.vegeta.paint.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.paint.R
import jp.co.vegeta.paint.databinding.FragmentPaintBinding
import jp.co.vegeta.paint.view.viewmodel.PaintViewModel

/**
 * Created by vegeta on 2021/07/14.
 */
@AndroidEntryPoint
class PaintFragment : Fragment(R.layout.fragment_paint) {

    private val paintViewModel: PaintViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentPaintBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = paintViewModel
            initView(this)
        }
    }

    private fun initView(binding: FragmentPaintBinding) {
        binding.undo.setOnClickListener {
            binding.drawingView.setErase(isErase = true)
        }
        binding.save.setOnClickListener {
        }
    }

    private fun getBitmapFromView(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}