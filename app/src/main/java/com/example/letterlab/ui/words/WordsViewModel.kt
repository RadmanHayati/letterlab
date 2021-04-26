package com.example.letterlab.ui.words

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.letterlab.data.PreferencesManager
import com.example.letterlab.data.SortOrder
import com.example.letterlab.data.Word
import com.example.letterlab.data.WordDao
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class WordsViewModel @ViewModelInject constructor(
    private val wordDao: WordDao,
    private val preferencesManager: PreferencesManager,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {
    val searchQuery = state.getLiveData("searchQuery", "")
    val preferencesFlow = preferencesManager.preferencesFlow
    private val wordEventChannel = Channel<WordsEvent>()
    val wordsEvent = wordEventChannel.receiveAsFlow()

    private val wordsFlow = combine(
        searchQuery.asFlow(),
        preferencesFlow
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }
        .flatMapLatest { (query, filterPreferences) ->
            wordDao.getWords(query, filterPreferences.sortOrder, filterPreferences.hideCompleted)
        }
    val words = wordsFlow.asLiveData()
    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun onHideCompletedClick(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesManager.updateHideCompleted(hideCompleted)
    }

    fun onWordSelected(word: Word) =viewModelScope.launch {
        wordEventChannel.send(WordsEvent.NavigateToEditWordScreen(word))
    }

    fun onWordCheckedChanged(word: Word, isChecked: Boolean) = viewModelScope.launch {
        wordDao.update(word.copy(learned = isChecked))
    }

    fun onWordSwiped(word: Word) = viewModelScope.launch {
        wordDao.delete(word)
        wordEventChannel.send(WordsEvent.ShowUndoDeleteTaskMessage(word))
    }

    fun onUndoDeleteClick(word: Word) = viewModelScope.launch {
        wordDao.insert(word)
    }

    fun onAddNewWordClick() = viewModelScope.launch {
        wordEventChannel.send(WordsEvent.NavigateToAddWordScreen)
    }

    sealed class WordsEvent {
        object NavigateToAddWordScreen : WordsEvent()
        data class NavigateToEditWordScreen(val word: Word) : WordsEvent()
        data class ShowUndoDeleteTaskMessage(val word: Word) : WordsEvent()
    }

}
