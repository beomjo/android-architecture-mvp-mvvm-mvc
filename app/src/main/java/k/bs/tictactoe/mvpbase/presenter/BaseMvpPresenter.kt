package k.bs.tictactoe.mvpbase.presenter

import k.bs.tictactoe.mvpbase.view.BaseMvpView

interface BaseMvpPresenter<in V : BaseMvpView> {

    fun attachView(view: V)

    fun detachView()
}