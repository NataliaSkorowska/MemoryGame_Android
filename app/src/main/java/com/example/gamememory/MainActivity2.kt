package com.example.gamememory
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*


private const val TAG = "Main Activity"
class MainActivity2 : AppCompatActivity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val images = mutableListOf(R.drawable.ic_cut, R.drawable.ic_flower, R.drawable.ic_star, R.drawable.ic_pet)

        //stworzenie par z obrazków
        images.addAll(images)
        //wymieszanie przypadkowo obrazków
        images.shuffle()
        buttons = listOf(imageButton1, imageButton2, imageButton3, imageButton4, imageButton5,
                imageButton6, imageButton7, imageButton8)

        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                Log.i(TAG, "Wciśnięto przycisk")
                updateModels(index)
                updateViews()
            }
        }
    }
    //zmiana - jeśli znaleziono parę, zmniejsz widoczność


    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.isMatched) {
                button.alpha = 0.1f
            }
            button.setImageResource(if (card.isFaceUp) card.identifier else R.drawable.question)
        }
    }


    private fun updateModels(position: Int) {
        val card = cards[position]

        if (card.isFaceUp) {
            Toast.makeText(this, "Nieprawidłowy ruch", Toast.LENGTH_SHORT).show()
            return
        }
        // 3 przypadki
        // 0 kart przewróconych na planszy - przewróć kartę
        // 1 karta przewrócona wcześniej -obróc kartę i sprawdź czy zgadza się z poprzednią
        // 2 karty przewrócone wcześniej - przewróć kartę
        if (indexOfSingleSelectedCard == null) {
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    // sprawdzanie czy znaleziono dwie takie same karty
    private fun checkForMatch(position1: Int, position2: Int) {
        if (cards[position1].identifier == cards[position2].identifier) {
            Toast.makeText(this, "Znaleziono parę!", Toast.LENGTH_SHORT).show()
            cards[position1].isMatched = true
            cards[position2].isMatched = true
        }
    }
}
