package com.example.letterlab.ui.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.letterlab.data.Word
import com.example.letterlab.databinding.ItemWordBinding

class WordAdapter : ListAdapter<Word, WordAdapter.WordsViewHOlder>(DiffCallback()) {

    class WordsViewHOlder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.apply {
                checkBoxLearned.isChecked = word.learned
                textViewWord.text = word.word
                textViewWord.paint.isStrikeThruText = word.learned
                labelPriority.isVisible = word.important
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHOlder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordsViewHOlder(binding)
    }

    override fun onBindViewHolder(holder: WordsViewHOlder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean = oldItem == newItem


    }
}