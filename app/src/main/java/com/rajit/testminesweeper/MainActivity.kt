package com.rajit.testminesweeper

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), onCellClickListener{

    private lateinit var game: MinesweeperGame
    private lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //gets respective values from the intent extra text
        val sizeOfGrid = intent.getIntExtra("sizeOfGrid", 0)
        val customBoardRows = intent.getIntExtra("numberOfRows", 0)
        val customBoardColumns = intent.getIntExtra("numberOfColumns", 0)
        val customBoardMines = intent.getIntExtra("numberOfMines", 0)
        val presetMines = ((1.0 / 4.0) * (sizeOfGrid * sizeOfGrid)).toInt()

        //this view is used to display snackbar
        val contextView = findViewById<ConstraintLayout>(R.id.rootLayout)
        //snackbar msg
        val msg = "SizeOfGrid: $sizeOfGrid, CBRow: $customBoardRows," +
                " CBColumn: $customBoardColumns, CBMines: $customBoardMines" +
                " BoardMinesPreset: $presetMines"
        //snackbar function
        showSnackBar(contextView, msg)

        //RecyclerView for the game
        val gameRv: RecyclerView = findViewById(R.id.minegridRv)

        /* Sets the board according to the size of the grid
        * For e.g. Presets(Easy -> GridSize = 8, Medium -> Size = 10, Hard -> Size = 12)
        * For Custom Board: numberOfRows, numberOfColumns, numberOfMines are passed
        * Also sets the grid layout span count to the size of grid
        */
        if (sizeOfGrid != 0) {
            game = MinesweeperGame(applicationContext, sizeOfGrid, presetMines)
            gameRv.layoutManager = GridLayoutManager(this, sizeOfGrid)
            gameAdapter = GameAdapter(this, game.getBoardGrid(), this, presetMines, game)
        } else {
            game = MinesweeperGame(applicationContext,
                numberOfMines = customBoardMines, numberOfRows = customBoardRows,
                numberOfColumns = customBoardColumns
            )
            gameRv.layoutManager = GridLayoutManager(this, customBoardColumns)
            gameAdapter = GameAdapter(this, game.getBoardGrid(), this, customBoardMines, game)
        }

        //Recyclerview Adapter

        gameRv.adapter = gameAdapter

    }

    //Generates a snackbar on the screen
    private fun showSnackBar(contextView: ConstraintLayout, msg: String) {
        Snackbar.make(contextView, msg, Snackbar.LENGTH_INDEFINITE)
            .setAction("Ok") {
                //automatically dismisses the snackbar
            }
            .show()
    }

    //Responds to the clicks on the Cell Click
    override fun onCellClick() {
        Toast.makeText(
            applicationContext,
            "Cell Clicked",
            Toast.LENGTH_SHORT
        ).show()
        gameAdapter.setCells(game.getBoardGrid())
    }
}