package com.example.letterlab.ui.deletealllearned

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.letterlab.data.WordDao
import com.example.letterlab.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteLearnedViewModel @ViewModelInject constructor(
    private val wordDao: WordDao,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {
    fun onConfirmClick() = applicationScope.launch {
       // Log.i("sth", "sth is wrong here")
         wordDao.deleteLearnedWords()
    }
}