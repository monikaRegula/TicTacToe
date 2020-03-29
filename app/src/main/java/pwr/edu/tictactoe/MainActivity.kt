package pwr.edu.tictactoe

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var currentPlayer = 1
    var computerIsPlaying = false

    fun restartGame(view: View){
        btnPlayerVsComp.isVisible = true
        btnTwoPlayers.isVisible = true
        currentPlayer = 1
        computerIsPlaying = false
        player1.clear()
        player2.clear()
        btnTwoPlayers.setBackgroundColor(Color.BLUE)
        btnPlayerVsComp.setBackgroundColor(Color.RED)
        var board = listOf<Button>(button1,button2,button3,button4,button5,button6,button7,button8,button9)
        board.forEach {
            btn -> btn.text = ""
            btn.isEnabled = true
            btn.setBackgroundResource(android.R.drawable.btn_default)
        }
    }

    fun choosePlayer(view: View){
        val player:Button = view as Button
        when(player.id)
        {
            R.id.btnTwoPlayers ->{
                btnTwoPlayers.setBackgroundColor(Color.MAGENTA)
                btnPlayerVsComp.isVisible = false
            }
            R.id.btnPlayerVsComp ->{
                btnPlayerVsComp.setBackgroundColor(Color.MAGENTA)
                btnTwoPlayers.isVisible = false
                computerIsPlaying = true
            }
        }
    }

    fun buttonClick(view: View){
        val clickedButton: Button = view as Button
        var locationId = 0
        when(clickedButton.id){
            R.id.button1 -> locationId = 1
            R.id.button2 -> locationId = 2
            R.id.button3 -> locationId = 3
            R.id.button4 -> locationId = 4
            R.id.button5 -> locationId = 5
            R.id.button6 -> locationId = 6
            R.id.button7 -> locationId = 7
            R.id.button8 -> locationId = 8
            R.id.button9 -> locationId = 9
        }
        play(clickedButton, locationId)
    }

    fun play(clickedButton: Button, locationId: Int){
        if( currentPlayer == 1){
            clickedButton.text = "X"
            clickedButton.setBackgroundColor(Color.BLUE)
            clickedButton.setTextColor(Color.WHITE)
            player1.add(locationId)
            currentPlayer = 2
            if(computerIsPlaying){
                try{
                    computerPlays()
                }catch (ex:Exception)
                {
                    Toast.makeText(this,"GAME OVER :(",Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            clickedButton.text = "O"
            clickedButton.setBackgroundColor(Color.RED)
            clickedButton.setTextColor(Color.WHITE)
            player2.add(locationId)
            currentPlayer = 1
        }
        //make currently clicked button unclickable
        clickedButton.isEnabled = false
        // check all combination of winning locations:
        findWinner(player1,1)
        findWinner(player2,2)
    }

    fun findWinner(player: ArrayList<Int>, numberOfPlayer: Int){
        var winner = -1
        //all combination of winning locations:
        var row1 = listOf(1,2,3)
        var row2 = listOf(4,5,6)
        var row3 = listOf(7,8,9)
        var column1 = listOf(1,4,7)
        var column2 = listOf(2,5,8)
        var column3 = listOf(3,6,9)
        var diagonal1 = listOf(1,5,9)
        var diagonal2 = listOf(3,5,7)

        if (player.containsAll(row1) || player.containsAll(row2) || player.containsAll(row3)||
                player.containsAll(column1) || player.containsAll(column2) || player.containsAll(column3) ||
                player.containsAll(diagonal1) || player.containsAll(diagonal2)){
            winner = numberOfPlayer
            println("Winner is :$winner")
        }
        showWinner(winner)
    }

    fun showWinner(winner: Int){
        if (winner != -1){
            if (!computerIsPlaying){
                if (currentPlayer == 1 ){
                    Toast.makeText(this,"Second Player WINS !!!", Toast.LENGTH_SHORT).show()
                    forbidClicking()
                }else{
                    Toast.makeText(this,"First Player WINS !!!", Toast.LENGTH_SHORT).show()
                    forbidClicking()
                }
            }else{
                if (winner == 2){
                    Toast.makeText(this, "COMPUTER WINS !!!", Toast.LENGTH_SHORT).show()
                    forbidClicking()
                }else{
                    Toast.makeText(this, "HUMAN WINS !!!", Toast.LENGTH_SHORT).show()
                    forbidClicking()
                }
            }
        }
    }

    fun forbidClicking()
    {
        var board = listOf<Button>(button1,button2,button3,button4,button5,button6,button7,button8,button9)
        board.forEach {
            btn-> btn.isEnabled = false
        }
    }

    fun computerPlays(){
        val computersMove = ArrayList<Int>()
        for(id in 1..9){
            if (player1.contains(id) || player2.contains(id)){
            }else{
                computersMove.add(id)
            }
        }

        val r = Random()
        val randomId = r.nextInt(computersMove.size)
        val id = computersMove[randomId]

        val clickedButton: Button?
        when(id){
            1 -> clickedButton = button1
            2 -> clickedButton = button2
            3 -> clickedButton = button3
            4 -> clickedButton = button4
            5 -> clickedButton = button5
            6 -> clickedButton = button6
            7 -> clickedButton = button7
            8 -> clickedButton = button8
            9 -> clickedButton= button9
            else -> clickedButton = button1
        }
        play(clickedButton,id)
    }

}
