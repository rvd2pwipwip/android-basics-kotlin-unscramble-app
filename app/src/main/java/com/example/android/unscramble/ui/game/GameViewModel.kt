package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

/**
 * ViewModel containing the app data and methods to process the data
 */
class GameViewModel: ViewModel() {
    companion object {
        const val TAG = "Game Fragment"
    }

    // List of words used in the game
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    // Declare private mutable variable that can only be modified
    // within the class it is declared.
    private var _score = 0
    private var _currentWordCount = 0
    private lateinit var _currentScrambledWord: String

    init {
        Log.d(TAG, "GameViewModel created")
        getNextWord()
    }

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

    /**
    * Returns true if the player word is correct.
    * Increases the score accordingly.
    */
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    /**
    * Increases the game score if the player's word is correct.
    */
    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    /**
    * Returns true if the current word count is less than MAX_NO_OF_WORDS.
    * Updates the next word.
    */
    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    /**
    * Re-initializes the game data to restart the game.
    */
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }
}