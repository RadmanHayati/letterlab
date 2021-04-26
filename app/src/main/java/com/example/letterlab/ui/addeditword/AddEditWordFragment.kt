package com.example.letterlab.ui.addeditword

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.letterlab.R
import com.example.letterlab.databinding.FragmentAddEditWordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditWordFragment : Fragment(R.layout.fragment_add_edit_word) {
    private val viewModel: AddEditWordViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAddEditWordBinding.bind(view)
        binding.apply {
            editTextWord.setText(viewModel.wordName)
            checkBoxImportant.isChecked = viewModel.wordImportance
            checkBoxImportant.jumpDrawablesToCurrentState()
            textViewDateCreated.isVisible = viewModel.word != null
            textViewDateCreated.text = "Added:${viewModel.word?.createdDateFormated}"
        }

    }
}