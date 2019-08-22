package k.bs.tictactoe.presenter

import k.bs.tictactoe.model.Board
import k.bs.tictactoe.view.TicTacToeView

class MainPresenter(private val view: TicTacToeView) {

    private val model: Board = Board()

    fun onButtonSelected(row: Int, col: Int) {
        val playerThatMoved = model.mark(row, col)

        if (playerThatMoved != null) {
            view.setButtonText(row, col, playerThatMoved.toString())

            if (model.winner != null) {
                view.showWinner(playerThatMoved.toString())
            }
        }
    }

    fun onResetSelected() {
        view.clearWinnerDisplay()
        view.clearButtons()
        model.restart()
    }
}