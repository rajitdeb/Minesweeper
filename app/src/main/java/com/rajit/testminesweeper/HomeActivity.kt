package com.rajit.testminesweeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast

class HomeActivity : AppCompatActivity() {

    private lateinit var numberOfRows: EditText
    private lateinit var numberOfColumns: EditText
    private lateinit var numberOfMines: EditText
    private lateinit var makeACustomBoard: Button
    private lateinit var startBtn: Button
    private lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var isEdtVisible = false
        var isCustomBoardSelected = false

        numberOfRows = findViewById(R.id.numberOfRows)
        numberOfColumns = findViewById(R.id.numberOfColumns)
        numberOfMines = findViewById(R.id.numberOfMines)
        makeACustomBoard = findViewById(R.id.makeACustomBoard)
        startBtn = findViewById(R.id.startBtn)
        radioGroup = findViewById(R.id.radioGroup)

        makeACustomBoard.setOnClickListener{
            isCustomBoardSelected = true
            if(!isEdtVisible) showEdts() else hideEdts()
            isEdtVisible = !isEdtVisible
        }

        startBtn.setOnClickListener{

            val intent = Intent(applicationContext, MainActivity::class.java)

            if(isCustomBoardSelected){

                val numberOfRowsTxt = numberOfRows.text.toString()
                val numberOfColumnsTxt = numberOfColumns.text.toString()
                val numberOfMinesTxt = numberOfMines.text.toString()

                intent.putExtra("numberOfRows", numberOfRowsTxt.toInt())
                intent.putExtra("numberOfColumns", numberOfColumnsTxt.toInt())
                intent.putExtra("numberOfMines", numberOfMinesTxt.toInt())

            }else{
                val size = onRadioButtonClicked(radioGroup.checkedRadioButtonId)
                intent.putExtra("sizeOfGrid", size)
            }

            startActivity(intent)
        }
    }

    private fun onRadioButtonClicked(viewID: Int): Int {
        var size = 0
        var message = ""
        when(viewID){

            R.id.level_easy -> {
                message = resources.getString(R.string.level_easy)
                size = 8
            }

            R.id.level_medium -> {
                message = resources.getString(R.string.level_medium)
                size = 10
            }

            R.id.level_hard -> {
                message = resources.getString(R.string.level_hard)
                size = 12
            }

            else -> message = "Please select an option"
        }

        displayToast(message, size)
        return size
    }

    private fun displayToast(message: String, size: Int) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun hideEdts() {
        numberOfRows.visibility = View.GONE
        numberOfColumns.visibility = View.GONE
        numberOfMines.visibility = View.GONE
    }

    private fun showEdts() {
        numberOfRows.visibility = View.VISIBLE
        numberOfColumns.visibility = View.VISIBLE
        numberOfMines.visibility = View.VISIBLE
    }

}