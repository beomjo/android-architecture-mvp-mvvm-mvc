package k.bs.tictactoe.mvpbase.presenter

import k.bs.tictactoe.mvpbase.view.BaseMvpView

open class BaseMvpPresenterImpl<V : BaseMvpView> :
    BaseMvpPresenter<V> {

    protected var view: V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}