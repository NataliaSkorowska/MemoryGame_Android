package com.example.gamememory

//Dla każdego przycisku specjalna MemoryCard
//Na początku gry isFaceUp i isMatched przyjmują wartość false, dla każdego przycisku
data class MemoryCard(val identifier: Int, var isFaceUp: Boolean = false, var isMatched: Boolean = false)