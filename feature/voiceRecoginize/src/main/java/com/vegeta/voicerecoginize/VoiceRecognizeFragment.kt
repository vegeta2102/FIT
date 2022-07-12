package com.vegeta.voicerecoginize

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vegeta.voicerecoginize.databinding.FragmentVoiceRecognizeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*

/**
 * Created by vegeta on 2022/06/23.
 */

@AndroidEntryPoint
class VoiceRecognizeFragment : Fragment(R.layout.fragment_voice_recognize) {
    companion object {
        private val TAG = VoiceRecognizeFragment::class.simpleName
    }

    private val viewModel: VoiceRecognizeViewModel by viewModels()
    private var speechRecognizer: SpeechRecognizer? = null

    private var isBeginningOfSpeech: Boolean = false

    /**
     * 合計金額を読み上げる
     */
    private fun play() {
        lifecycleScope.launch {
            val job = this.launch {
                while (isActive) {
                    // Try to get value
                    if (isBeginningOfSpeech) cancel()
                }
            }
            delay(1000)
            job.cancelAndJoin()
            // Do next
        }
    }

    private val recognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {
            Timber.tag(TAG).d("onReadyForSpeech $params")
        }

        override fun onRmsChanged(rmsdB: Float) {
            // Timber.tag(TAG).d("onRmsChanged $rmsdB")
        }

        override fun onBufferReceived(buffer: ByteArray?) {
            Timber.tag(TAG).d("onBufferReceived $buffer")
        }

        override fun onPartialResults(partialResults: Bundle?) {
            val recData =
                partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.joinToString()
                    ?: ""
            Timber.tag(TAG).d("onPartialResults $recData")
        }

        override fun onEvent(eventType: Int, params: Bundle?) {
            Timber.tag(TAG).d("onEvent eventType=$eventType params=$params")
        }

        override fun onBeginningOfSpeech() {
            Timber.tag(TAG).d("onBeginningOfSpeech")
            isBeginningOfSpeech = true
        }

        override fun onEndOfSpeech() {
            Timber.tag(TAG).d("onEndOfSpeech")
            isBeginningOfSpeech = false
        }

        override fun onError(error: Int) {
            Timber.tag(TAG).e("onError $error")
            viewModel.updateMessage("Error $error")
            /*lifecycleScope.launch {
                val job = this.launch {
                    while (isActive) {
                        if (isBeginningOfSpeech) {
                            cancel()
                        }
                    }
                }
                delay(5000L)
                job.cancelAndJoin()
            }*/
        }

        override fun onResults(results: Bundle?) {
            val recData =
                results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.joinToString()
                    ?: ""
            Timber.tag(TAG).d("onResults $recData")
            isBeginningOfSpeech = false
            viewModel.updateMessage("Result: $recData")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentVoiceRecognizeBinding.bind(view).also { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
            setupSpeechRecognizer()
            initView(binding)
        }
    }

    private fun initView(binding: FragmentVoiceRecognizeBinding) {
        binding.start.setOnClickListener {
            startVoiceInput()
        }
        binding.stop.setOnClickListener {
            cancelSpeechRecognizer()
            viewModel.updateMessage("Listening")

            val test1 = "東京,tok,yo"
            val result1 = test1.split(",")[0]
            val result2 = test1.split(",")[1]
            Timber.d("Split 1: $result1 : $result2")
            val test2 = "東京tokyo,"
            val result11 = test2.split(",").run {
                if (this.size > 1) {
                    this[0]
                } else {
                    this
                }
            }
            val result22 = test2.split(",").run {
                if (this.size > 1) {
                    this[1]
                } else {
                    this
                }
            }
            Timber.d("Split 2: $result11 : $result22")
        }
    }

    private fun setupSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())
    }

    private fun startVoiceInput() {
        viewModel.updateMessage("Listening")
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.JAPAN.toString())
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, requireContext().packageName)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
        speechRecognizer?.setRecognitionListener(recognitionListener)
        speechRecognizer?.startListening(intent)
    }

    private fun cancelSpeechRecognizer() {
        speechRecognizer?.stopListening()
        speechRecognizer?.cancel()
        speechRecognizer?.destroy()
    }

    override fun onPause() {
        super.onPause()
        cancelSpeechRecognizer()
    }
}