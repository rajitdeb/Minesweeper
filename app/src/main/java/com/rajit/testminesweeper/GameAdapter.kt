package com.rajit.testminesweeper

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class GameAdapter(
        var context: Context,
        private var board: Array<Array<MineCell>>,
        private var listener: onCellClickListener,
        private var numberOfMines: Int,
        private var game: MinesweeperGame
) :
        RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    inner class GameViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        private val valueTextView: TextView = itemView.findViewById(R.id.cell_value)
        private lateinit var cell: MineCell
        fun bind(x: Int, y: Int) {
            cell = board[x][y]
            valueTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.pink))
            itemView.setOnClickListener {
                if(game.isGameOver()){

                    Toast.makeText(context, "GameOver", Toast.LENGTH_SHORT).show()
                }else{
//                    game.handleCellClick(x, y)
//                    game.setMines(x, y, numberOfMines)
//                    listener.onCellClick()
                    valueTextView.setBackgroundColor(Color.GRAY)
                    setCells(game.getBoardGrid())
                }
            }

//            if (cell.isRevealed) {
//                if (cell.value == MineCell.BOMB) {
//                    valueTextView.setText(R.string.bomb)
//                    valueTextView.setTextColor(Color.BLACK)
//                    valueTextView.setBackgroundColor(Color.GRAY)
//                } else if (cell.value == MineCell.BLANK) {
//                    valueTextView.text = ""
//                    valueTextView.setBackgroundColor(Color.GRAY)
//                } else {
//                    valueTextView.text = cell.value.toString()
//                    valueTextView.setBackgroundColor(Color.GRAY)
//                    when (cell.value) {
//                        1 -> {
//                            valueTextView.text = cell.value.toString()
//                            valueTextView.setTextColor(Color.BLUE)
//                        }
//                        2 -> {
//                            valueTextView.text = cell.value.toString()
//                            valueTextView.setTextColor(Color.GREEN)
//                        }
//                        3 -> {
//                            valueTextView.text = cell.value.toString()
//                            valueTextView.setTextColor(Color.RED)
//                        }
//                        else -> {
//                            valueTextView.text = cell.value.toString()
//                        }
//                    }
//                }
//            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.GameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cell, parent, false)
        return this.GameViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: GameAdapter.GameViewHolder, position: Int) {
        val cellPos = game.toXY(position)
        holder.bind(cellPos[0], cellPos[1])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return board.size
    }

    fun setCells(cells: Array<Array<MineCell>>): Unit {
        this.board = cells
        notifyDataSetChanged()
    }

}