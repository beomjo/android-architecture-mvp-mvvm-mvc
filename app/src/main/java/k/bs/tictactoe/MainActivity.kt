package k.bs.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import k.bs.tictactoe.model.Board
import kotlinx.android.synthetic.main.activity_tictactoe.*

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.name

    private val model = Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_tictactoe, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset -> {
                reset()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

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
                winnerPlayerLabel.text = playerThatMoved.toString()
                winnerPlayerViewGroup.visibility = View.VISIBLE
            }
        }

    }

    private fun reset() {
        winnerPlayerViewGroup.visibility = View.GONE
        winnerPlayerLabel.text = ""

        model.restart()

        for (i in 0 until buttonGrid.childCount) {
            (buttonGrid.getChildAt(i) as Button).text = ""
        }
    }

}
