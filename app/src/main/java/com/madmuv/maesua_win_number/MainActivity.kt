package com.madmuv.maesua_win_number

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.madmuv.maesua_win_number.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mViewModel : MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
        subScribeLiveData()
    }

    fun initView() {
        binding.submitButton.isEnabled = false

        binding.resultNumber.isClickable = false

        binding.submitButton.setOnClickListener {
            mViewModel.calculateNumber(binding.inputNumber.text.toString())
        }

        binding.inputNumber.doOnTextChanged { text, _, _, _ ->
            binding.submitButton.isEnabled = (text?.length ?: 0) >= 3
            if (text.isNullOrBlank()) mViewModel.clearText()
        }

        binding.inputNumber.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mViewModel.calculateNumber(binding.inputNumber.text.toString())
            }
            false
        }

        binding.resultNumber.setOnClickListener {
            val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", mViewModel.getResultNumber())
            clipBoard.setPrimaryClip(clip)
            Toast.makeText(this, "copy ผลเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show()
        }
    }

    private fun subScribeLiveData() {
        mViewModel.resultNumber.observe(this) {
            binding.resultNumber.text = it
            val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label",it)
            clipBoard.setPrimaryClip(clip)
            Toast.makeText(this, "copy ผลเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show()
        }

        mViewModel.enableCopyTextView.observe(this) {
            binding.resultNumber.isClickable = true
        }

        mViewModel.clearTextEvent.observe(this) {
            binding.resultNumber.text = ""
        }
    }

}