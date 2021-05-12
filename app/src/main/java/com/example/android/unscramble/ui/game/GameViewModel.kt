package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    companion object {
        const val TAG = "Game Fragment"
    }

    init {
        Log.d(TAG, "GameViewModel created")
        getNextWord()
    }

    // List of words used in the game
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    // Declare private mutable variable that can only be modified
    // within the class it is declared.
    private var _score = 0
    private var _currentWordCount = 0
    private lateinit var _currentScrambledWord: String

    /**
     * Backing properties
     */
    // Declare another public immutable field and override its getter method.
    // Return the private property's value in the getter method.
    // When score is accessed, the get() function is called and
    // the value of _score is returned.
    val score: Int get() = _score
    val currentWordCount: Int get() = _currentWordCount
    val currentScrambledWord: String get() = _currentScrambledWord

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "GameViewModel destroyed")
    }

    /**
    * Updates currentWord and currentScrambledWord with the next word.
    */
    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (tempWord.toString().equals(currentWord, false)) {
            tempWord.shuffle()
        }

        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }
}