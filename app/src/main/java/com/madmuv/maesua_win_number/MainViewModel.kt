package com.madmuv.maesua_win_number

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val enableCopyButton = SingleLiveEvent<Unit>()
    val resultNumber = MutableLiveData<String>()

    fun calculateNumber(number: String) {
        viewModelScope.launch {
            val arrayCouple = arrayListOf<String>()
            val arrayTriple = arrayListOf<String>()

            number.forEachIndexed { i1, s1 ->
                number.forEachIndexed { i2, s2 ->

                    if (i1 != i2 && i2 > i1 && i2 != 0) {
                        arrayCouple.add(s1.toString() + s2.toString())
                    }

                    number.forEachIndexed { i3, s3 ->
                        if (i1 != i2 && i1 != i3 && i2 != i3 && i1 < i2 && i1 < i3 && i2 < i3) {
                            arrayTriple.add(s1.toString() + s2.toString() + s3.toString())
                        }
                    }
                }
            }

            arrayCouple.sort()
            arrayTriple.sort()

            arrayCouple.toMutableList().mapIndexed { index, s ->
                if (index % 6 == 0) {
                    arrayCouple.add(index, "\n")
                }
            }

            arrayTriple.toMutableList().mapIndexed { index, s ->
                if (index % 6 == 0) {
                    arrayTriple.add(index, "\n")
                }
            }

            resultNumber.value = arrayCouple.joinToString(" ") + "\n" + arrayTriple.joinToString(" ")
            checkEnableCopyButton()
        }
    }

    fun getResultNumber(): String {
        return resultNumber.value ?:""
    }

    fun checkEnableCopyButton() {
        if (resultNumber.value != "" || resultNumber.value != null) {
            enableCopyButton.value = Unit
        }
    }
}