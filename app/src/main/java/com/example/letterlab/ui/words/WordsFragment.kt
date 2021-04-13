package com.example.letterlab.ui.words

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letterlab.R
import com.example.letterlab.databinding.FragmentWordsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordsFragment : Fragment(R.layout.fragment_words) {
    private val viewModel: WordsViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWordsBinding.bind(view)
        val wordAdapter = WordAdapter()
        binding.apply {
            recyclerViewWords.apply {
                adapter = wordAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewModel.words.observe(viewLifecycleOwner) {
            wordAdapter.submitList(it)
        }
    }
}