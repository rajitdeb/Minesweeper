package com.rajit.testminesweeper

data class MineCell(var value: Int = 0, var isRevealed: Boolean = false,
                    var isFlagged: Boolean = false){
    companion object{
        const val BOMB = -1
        const val BLANK = 0
        val movement = intArrayOf(-1, 0, 1)
    }
}
