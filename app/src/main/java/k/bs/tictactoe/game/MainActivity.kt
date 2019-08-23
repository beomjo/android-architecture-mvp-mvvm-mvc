package k.bs.tictactoe.game

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import k.bs.tictactoe.R
import k.bs.tictactoe.game.contract.TicTacToeContract
import k.bs.tictactoe.mvpbase.view.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_tictactoe.*

class MainActivity : BaseMvpActivity<TicTacToeContract.View,
        TicTacToeContract.Presenter>(),
    TicTacToeContract.View {

    private val TAG = this::class.java.name
    override var presenter: TicTacToeContract.Presenter = TicTacToePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe)
    }

    override fun showWinner(winningPlayerDisplayLabel: String) {
        winnerPlayerLabel.text = winningPlayerDisplayLabel
        winnerPlayerViewGroup.visibility = View.VISIBLE
    }

    override fun clearWinnerDisplay() {
        winnerPlayerViewGroup.visibility = View.GONE
        winnerPlayerLabel.text = ""
    }

    override fun clearButtons() {
        for (i in 0 until buttonGrid.childCount) {
            (buttonGrid.getChildAt(i) as Button).text = ""
        }
    }

    override fun setButtonText(row: Int, col: Int, text: String) {
        val btn = buttonGrid.findViewWithTag<View>("" + row + col) as Button
        btn.text = text
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_tictactoe, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset -> {
                presenter.onResetSelected()
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

        presenter.onButtonSelected(row, col)
    }

}
