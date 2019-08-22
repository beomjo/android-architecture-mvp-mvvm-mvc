package k.bs.tictactoe

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import k.bs.tictactoe.model.Board

class MainVm {
    private val TAG = this::class.java.name

    private val model = Board()

    val winnerPlayerLabel = ObservableField<String>()
    val winnerPlayerViewGroup = ObservableBoolean(false)


    fun onCellClicked(v: View) {

        val button = v as Button

        val tag = button.tag.toString()
        val row = Integer.valueOf(tag.substring(0, 1))
        val col = Integer.valueOf(tag.substring(1, 2))
        Log.i(TAG, "Click Row: [$row,$col]")

        val playerThatMoved = model.mark(row, col)

        if (playerThatMoved != null) {
            button.text = playerThatMoved.toString()
            if (model.winner != null) {
                winnerPlayerLabel.set(playerThatMoved.toString())
                winnerPlayerViewGroup.set(true)
            }
        }

    }

    fun reset(buttonGrid: GridLayout) {
        winnerPlayerViewGroup.set(false)
        winnerPlayerLabel.set("")
        model.restart()

        for (i in 0 until buttonGrid.childCount) {
            (buttonGrid.getChildAt(i) as Button).text = ""
        }
    }

}