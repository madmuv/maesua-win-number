package com.madmuv.maesua_win_number

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.madmuv.maesua_win_number.databinding.ActivityMainBinding
import kotlin.math.sign

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        observeData()
    }

    fun observeData() {
        binding.inputNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length >= 3) {
                    calculateNumber(s.toString())
                }
            }

        })
    }

    fun calculateNumber(number: String) {
        val loopNumber = number.length
        val arrayNumber = arrayListOf<String>()
        val arrayCouple = arrayListOf<String>()
        number.forEach {
            arrayNumber.add(it.toString())
        }

        arrayNumber.forEachIndexed { index, s ->
            if (index + 1 < loopNumber) {
                arrayCouple.add(s+arrayNumber[index + 1])
                if (index + 2 < loopNumber) {
                    arrayCouple.add(s+arrayNumber[index + 2])
                    if (index + 3 < loopNumber) {
                        arrayCouple.add(s+arrayNumber[index + 3])
                        if (index + 4 < loopNumber) {
                            arrayCouple.add(s+arrayNumber[index + 4])
                        }
                    }
                }
            }
        }

        val result = arrayCouple
        displayResult(result.toString())
    }

    private fun couplePair(list: ArrayList<Int>): String {
        return list.toString()
    }

    private fun displayResult(result: String) {
        binding.resultNumber.text = result
    }

}