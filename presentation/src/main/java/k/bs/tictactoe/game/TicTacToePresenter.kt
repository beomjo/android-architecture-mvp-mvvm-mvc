package k.bs.tictactoe.game

import k.bs.tictactoe.game.contract.TicTacToeContract
import k.bs.tictactoe.model.Board
import k.bs.tictactoe.mvpbase.presenter.BaseMvpPresenterImpl

class TicTacToePresenter : BaseMvpPresenterImpl<TicTacToeContract.View>(),
    TicTacToeContract.Presenter {

    private val model: Board = Board()

    override fun onButtonSelected(row: Int, col: Int) {
        val playerThatMoved = model.mark(row, col)

        if (playerThatMoved != null) {
            view?.setButtonText(row, col, playerThatMoved.toString())

            if (model.winner != null) {
                view?.showWinner(playerThatMoved.toString())
            }
        }
    }

    override fun onResetSelected() {
        view?.clearWinnerDisplay()
        view?.clearButtons()
        model.restart()
    }
}