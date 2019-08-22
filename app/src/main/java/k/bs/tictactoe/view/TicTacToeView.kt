package k.bs.tictactoe.view

interface TicTacToeView {
    fun showWinner(winningPlayerDisplayLabel: String)
    fun clearWinnerDisplay()
    fun clearButtons()
    fun setButtonText(row: Int, col: Int, text: String)
}