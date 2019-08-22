package k.bs.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import k.bs.tictactoe.databinding.ActivityTictactoeBinding
import k.bs.tictactoe.model.Board
import kotlinx.android.synthetic.main.activity_tictactoe.*

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
