package com.example.letterlab.ui.addeditword

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.letterlab.R
import com.example.letterlab.databinding.FragmentAddEditWordBinding
import com.example.letterlab.util.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_edit_word.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddEditWordFragment : Fragment(R.layout.fragment_add_edit_word) {
    private val viewModel: AddEditWordViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAddEditWordBinding.bind(view)
        binding.apply {
            editTextWord.setText(viewModel.wordName)
            editTextMeaning.setText(viewModel.wordMeaning)
            checkBoxImportant.isChecked = viewModel.wordImportance
            checkBoxImportant.jumpDrawablesToCurrentState()
            textViewDateCreated.isVisible = viewModel.word != null
            textViewDateCreated.text = "Added:${viewModel.word?.createdDateFormated}"

            editTextWord.addTextChangedListener {
                viewModel.wordName = it.toString()
            }
            checkBoxImportant.setOnCheckedChangeListener { _, isChecked ->
                viewModel.wordImportance = isChecked

            }
            editTextMeaning.addTextChangedListener {
                viewModel.wordMeaning = it.toString()
            }
            fabSaveWord.setOnClickListener {
                viewModel.onSaveClick()
            }

        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.addEditWordEvent.collect { event ->
                when(event){
                    is AddEditWordViewModel.AddEditWordEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(),event.msg,Snackbar.LENGTH_LONG).show()
                    }
                    is AddEditWordViewModel.AddEditWordEvent.NavigateBackWithResult -> {
                       edit_text_word.clearFocus()
                        setFragmentResult(
                            "add_edit_request",
                            bundleOf("add_edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                }.exhaustive
            }
        }
    }
}