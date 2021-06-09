package com.madmuv.maesua_win_number

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.madmuv.maesua_win_number.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var inputNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupButton()
        observeData()
    }

    fun setupButton() {
        binding.submitButton.isEnabled = false
        binding.copyButton.isEnabled = false
        binding.submitButton.setOnClickListener {
            calculateNumber(inputNumber)
        }
    }

    fun observeData() {
        binding.inputNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length >= 3) {
                    binding.submitButton.isEnabled = true
                    inputNumber = s.toString()
                }
            }

        })
    }

    fun calculateNumber(number: String) {
        val loopNumber = number.length
        val arrayNumber = arrayListOf<String>()
        val arrayCouple = arrayListOf<String>()
        val arrayTriple = arrayListOf<String>()
        number.forEach {
            arrayNumber.add(it.toString())
        }

        arrayNumber.forEachIndexed { index, s ->
            if (index + 1 < loopNumber) {
                arrayCouple.add(s+arrayNumber[index + 1])
                if (index + 2 < loopNumber) {
                    arrayCouple.add(s+arrayNumber[index + 2])
                    arrayTriple.add(s+arrayNumber[index + 1]+arrayNumber[index + 2])
                    if (index + 3 < loopNumber) {
                        arrayCouple.add(s+arrayNumber[index + 3])
                        arrayTriple.add(s+arrayNumber[index + 1]+arrayNumber[index + 3])
                        arrayTriple.add(s+arrayNumber[index + 2]+arrayNumber[index + 3])
                        if (index + 4 < loopNumber) {
                            arrayCouple.add(s+arrayNumber[index + 4])
                            arrayTriple.add(s+arrayNumber[index + 1]+arrayNumber[index + 4])
                            arrayTriple.add(s+arrayNumber[index + 2]+arrayNumber[index + 4])
                            arrayTriple.add(s+arrayNumber[index + 3]+arrayNumber[index + 4])
                        }
                    }
                }
            }
        }

        arrayCouple.sort()
        arrayTriple.sort()


        val firstLine = arrayCouple.subList(0,5).joinToString("  ")
        val secondLine = arrayCouple.subList(5,10).joinToString("  ")

        val thirdLine = arrayTriple.subList(0,5).joinToString("  ")
        val forthLine = arrayTriple.subList(5,10).joinToString("  ")

        val result = firstLine
            .plus("\n")
            .plus(secondLine)
            .plus("\n")
            .plus("\n")
            .plus(thirdLine)
            .plus("\n")
            .plus(forthLine)
        displayResult(result)

        if (result.isNotBlank()) {
            binding.copyButton.isEnabled = true
        }
    }

    private fun displayResult(result: String) {
        binding.resultNumber.text = result
        binding.copyButton.setOnClickListener {
            val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", result)
            clipBoard.setPrimaryClip(clip)
        }
    }

}