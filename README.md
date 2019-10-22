# ArchitectureSample_mvp-mvvm-mvc
### Tictacteo Game with Architecture Pattern

<br/>

<img src="https://user-images.githubusercontent.com/39984656/67264246-85bdb000-f4e5-11e9-9b37-ebd564f3ad6f.png" width="300" height="450">  

<br/>  
<br/>  


## MVC  
#### MainActivity.kt  
```
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
```  

<br/>
<br/>


## MVP  
#### TicTacToePresenter.kt
```
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
```  
<br/>

#### MainActivity.kt    
```
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
```
<br/>
<br/>


## MVVM  
#### MainActivity.kt  
```
class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.name

    val vm = MainVm()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityTictactoeBinding>(this, R.layout.activity_tictactoe)
            .setVariable(BR.vm, vm)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_tictactoe, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset -> {
                vm.reset(buttonGrid)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
```  
#### MainVm.kt  
```
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
```  
#### activity_tictactoe.xml  
```
<?xml version="1.0" encoding="utf-8"?>
<layout>
    <data>
        <import type="android.view.View"/>
        <variable name="vm" type="k.bs.tictactoe.MainVm"/>
    </data>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                  xmlns:tools="http://schemas.android.com/tools"
                  android:id="@+id/tictactoe"
                  android:orientation="vertical"
                  android:layout_width="match_parent"
                  android:layout_height="match_parent"
                  android:paddingBottom="@dimen/activity_vertical_margin"
                  android:paddingLeft="@dimen/activity_horizontal_margin"
                  android:paddingRight="@dimen/activity_horizontal_margin"
                  android:paddingTop="@dimen/activity_vertical_margin"
                  android:gravity="center_horizontal">

        <GridLayout
                android:id="@+id/buttonGrid"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:columnCount="3"
                android:rowCount="3">

            <Button
                    android:tag="00"
                    android:onClick="@{(v)->vm.onCellClicked(v)}"
                    style="@style/tictactoebutton"/>

            <Button
                    android:tag="01"
                    android:onClick="@{(v)->vm.onCellClicked(v)}"
                    style="@style/tictactoebutton"/>

            <Button
                    android:tag="02"
                    android:onClick="@{(v)->vm.onCellClicked(v)}"
                    style="@style/tictactoebutton"/>

            <Button
                    android:tag="10"
                    android:onClick="@{(v)->vm.onCellClicked(v)}"
                    style="@style/tictactoebutton"/>

            <Button
                    android:tag="11"
                    android:onClick="@{(v)->vm.onCellClicked(v)}"
                    style="@style/tictactoebutton"/>

            <Button
                    android:tag="12"
                    android:onClick="@{(v)->vm.onCellClicked(v)}"
                    style="@style/tictactoebutton"/>

            <Button
                    android:tag="20"
                    android:onClick="@{(v)->vm.onCellClicked(v)}"
                    style="@style/tictactoebutton"/>

            <Button
                    android:tag="21"
                    android:onClick="@{(v)->vm.onCellClicked(v)}"
                    style="@style/tictactoebutton"/>

            <Button
                    android:tag="22"
                    android:onClick="@{(v)->vm.onCellClicked(v)}"
                    style="@style/tictactoebutton"/>

        </GridLayout>

        <LinearLayout
                android:id="@+id/winnerPlayerViewGroup"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:gravity="center"
                android:orientation="vertical"
                android:visibility="@{vm.winnerPlayerViewGroup ? View.VISIBLE :View.GONE}"
                tools:visibility="visible">

            <TextView
                    android:id="@+id/winnerPlayerLabel"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:textSize="40sp"
                    android:layout_margin="20dp"
                    android:text="@{vm.winnerPlayerLabel}"
                    tools:text="X"/>

            <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:textSize="30sp"
                    android:text="@string/winner"/>

        </LinearLayout>

    </LinearLayout>
</layout>
```
