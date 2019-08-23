package k.bs.tictactoe.game.contract

import k.bs.tictactoe.mvpbase.presenter.BaseMvpPresenter
import k.bs.tictactoe.mvpbase.view.BaseMvpView

object TicTacToeContract {
    interface View : BaseMvpView {
        fun showWinner(winningPlayerDisplayLabel: String)
        fun clearWinnerDisplay()
        fun clearButtons()
        fun setButtonText(row: Int, col: Int, text: String)
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun onButtonSelected(row: Int, col: Int)
        fun onResetSelected()
    }
}