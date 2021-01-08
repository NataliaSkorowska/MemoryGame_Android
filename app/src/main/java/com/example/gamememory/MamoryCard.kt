package com.example.gamememory
//stan karty, na początku gry obie flagi ustawione na false
data class MemoryCard(val identifier: Int, var isFaceUp: Boolean = false, var isMatched: Boolean = false)

