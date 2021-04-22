package com.example.letterlab.ui.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.letterlab.data.Word
import com.example.letterlab.databinding.ItemWordBinding

class WordAdapter(private val listener :OnItemClickListener) : ListAdapter<Word, WordAdapter.WordsViewHolder>(DiffCallback()) {

   inner class WordsViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init{
            binding.apply {
                root.setOnClickListener{
                    val position=adapterPosition
                    if (position != RecyclerView.NO_POSITION){
                        val word=getItem(position)
                        listener.onItemClick(word)
                    }
                }
            checkBoxLearned.setOnClickListener {
                val position=adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val word=getItem(position)
                   listener.onCheckBoxClick(word,checkBoxLearned.isChecked)
                }
            }
            }
        }
        fun bind(word: Word) {
            binding.apply {
                checkBoxLearned.isChecked = word.learned
                textViewWord.text = word.word
                textViewWord.paint.isStrikeThruText = word.learned
                labelPriority.isVisible = word.important
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    interface OnItemClickListener {
        fun onItemClick(word: Word)
        fun onCheckBoxClick(word: Word, isChecked: Boolean)
    }


    class DiffCallback : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean = oldItem == newItem
    }
}