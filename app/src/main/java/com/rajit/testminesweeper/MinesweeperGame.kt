package com.rajit.testminesweeper

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.rajit.testminesweeper.MineCell.Companion.BOMB
import kotlin.random.Random

/* This class generates the grid for the game
* @param1 -> sizeOfTheGrid(if user selects from the presets size = (8, 10, 12), else 0)
* @param2 -> numberOfMines(if user selects from the presets, then 1/4 of grid size else
             specified no. of mines)
* @param3 -> numberOfxs(if user selects from the presets, then 0 else specified no. of xs)
* @param4 -> numberOfColumns(if user selects from the presets, then 0 else specified no.
             of columns)
*/
class MinesweeperGame(
        private val context: Context,
        private val size: Int = 0,
        private val numberOfMines: Int,
        private val numberOfRows: Int = 0,
        private val numberOfColumns: Int = 0
) {
    private val revealMode: Boolean = true
    private var minesPlaced: Int = 0
    private var isGameOver: Boolean = false
    private var status: Status = Status.ONGOING

    //sets board cells according to the parameters passed
    private var board: Array<Array<MineCell>> = if (size == 0) {
        Array(numberOfRows * numberOfColumns) { Array(numberOfColumns) { MineCell() } }
    } else {
        Array(size * size) { Array(size) { MineCell() } }
    }

    fun setMines(x: Int, y: Int, numberOfMines: Int) {
//        TODO("CODE CLEANUP NEEDED AND NO OF MINES ARE STILL NOT CORRECT")
        val cellClickedPosition = intArrayOf(x, y)
        val mineCellPosition =
                if (size == 0) {
                    generateRandomPosition(numberOfColumns, numberOfRows)
                } else {
                    generateRandomPosition(size, size)
                }
        if (minesPlaced < numberOfMines) {
            setBombsInTheSpecifiedCell(
                    mineCellPosition[0],
                    mineCellPosition[1],
                    cellClickedPosition,
                    size
            )
        }
        minesPlaced++
    }

    private fun setBombsInTheSpecifiedCell(mineCellXPos: Int, mineCellYPos: Int,
                                           clickedCellCoordinates: IntArray, size: Int) {

        var cell = board[mineCellXPos][mineCellYPos]
        var mineCoordinates: IntArray = intArrayOf()
        if (cell.value != BOMB && (mineCellXPos != clickedCellCoordinates[0]
                        || mineCellYPos != clickedCellCoordinates[1])) {
            cell.value = BOMB
            Log.i("MINES", "MINES at: x: ${mineCellXPos}, y: ${mineCellYPos}")
        } else if (cell.value != BOMB && mineCellXPos == clickedCellCoordinates[0]
                && mineCellYPos == clickedCellCoordinates[1]) {

            Toast.makeText(context, "Same Cell", Toast.LENGTH_LONG).show()
            mineCoordinates =
                    if (size == 0) {
                        generateRandomPosition(numberOfColumns, numberOfRows)
                    } else {
                        generateRandomPosition(size, size)
                    }
            while (mineCoordinates[0] != clickedCellCoordinates[0] &&
                    mineCellYPos != clickedCellCoordinates[1]) {
                mineCoordinates =
                        if (size == 0) {
                            generateRandomPosition(numberOfColumns, numberOfRows)
                        } else {
                            generateRandomPosition(size, size)
                        }
            }


            cell = board[mineCoordinates[0]][mineCoordinates[1]]
            cell.value = BOMB
            Log.i("MINES", "MINES at: x: ${mineCoordinates[0]}, y: ${mineCoordinates[1]}")
        } else {
            mineCoordinates = if (size == 0) {
                generateRandomPosition(numberOfColumns, numberOfRows)
            } else {
                generateRandomPosition(size, size)
            }
            cell = board[mineCoordinates[0]][mineCoordinates[1]]
            cell.value = BOMB
            Toast.makeText(context, "Error Occurred", Toast.LENGTH_LONG).show()
            Log.i("ERROR!!!", "ERROR!!!: Fatal Error :( ")
            Log.i("ERROR DETAILS!!!",
                    "Cell Value: ${cell.value}," +
                            " mineCoordinatesX: ${mineCellXPos}," +
                            " mineCoordinatesY: ${mineCellYPos}," +
                            " clickedCell: x: ${clickedCellCoordinates[0]}, y: ${clickedCellCoordinates[1]}"
            )
            Log.i("MINES", "MINES at: x: ${mineCoordinates[0]}, y: ${mineCoordinates[1]}")
        }


    }

    private fun generateRandomPosition(x: Int, y: Int): IntArray {
        val mineXPos = Random.nextInt(x)
        val mineYPos = Random.nextInt(y)

        return intArrayOf(mineXPos, mineYPos)
    }

    private fun updateNeighbours(x: Int, y: Int) {
//        TODO("Check the implementation and update if necessary")
        for (i in MineCell.movement) {
            for (j in MineCell.movement) {
                if (((x + i) in 0 until size) && ((y + j) in 0 until size)
                        && board[x + i][y + j].value != BOMB
                )
                    board[x + i][y + j].value++
            }
        }

    }

    fun handleCellClick(x: Int, y: Int) {
        if (!isGameOver) {
            if (revealMode) {
                reveal(x, y)
            }
        }
    }

    private fun reveal(x: Int, y: Int) {
        val cell = board[x][y]

        if (!cell.isRevealed) {
            cell.isRevealed = true
            if (cell.value == BOMB) {
                isGameOver = true
            } else if (cell.value == MineCell.BLANK) {
                for (i in MineCell.movement) {
                    for (j in MineCell.movement) {
                        if ((i != 0 || j != 0) && (x + i) >= 0 && (x + i) < size && (y + j) >= 0 && (y + j) < size) {
                            reveal(x + i, y + j)
                        }
                    }
                }
            }
        }

    }

    fun toXY(index: Int): IntArray {
        var y = 0
        var x = 0
        if (size != 0) {
            y = index.div(size)
            x = index - (y * size)
        } else {
            y = index.div(numberOfColumns)
            x = index - (y * numberOfRows)
        }

        return intArrayOf(x, y)
    }

    fun isGameOver(): Boolean {
        return isGameOver
    }

    fun getGameStatus(): Status {
        return status
    }

    fun getBoardGrid(): Array<Array<MineCell>> {
        return board
    }
}