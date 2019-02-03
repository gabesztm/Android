package com.example.random

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class MainActivity : AppCompatActivity() {

    var latestNumber=0.toString()
    var latestMode=false.toString()

    val rollDice=Runnable{
            val rndInt = (1..6).shuffled().first()
            findViewById<TextView>(R.id.randomText).text = rndInt.toString()
            }

    suspend fun anim() {
        for (i in 1..50) {
            GlobalScope.async{runOnUiThread(rollDice)}.await()
            Thread.sleep(10)
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollBtn = findViewById<Button>(R.id.randomBtn)
        rollBtn.setOnClickListener {
            GlobalScope.async { anim() }
            }

        val nightSwitch=findViewById<Switch>(R.id.nightMode)
        nightSwitch.setOnClickListener(){
            changeMode()
        }
        changeMode()
        }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val latestValue=findViewById<TextView>(R.id.randomText)
        val latestValueNumber= latestValue.text
        outState?.putCharSequence(latestNumber, latestValueNumber)
        val nightModeSwitch=findViewById<Switch>(R.id.nightMode)
        val currentState=nightModeSwitch.isChecked
        outState?.putBoolean(latestMode, currentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        val restorableNumber=savedInstanceState?.getCharSequence(latestNumber)
        val latesValue=findViewById<TextView>(R.id.randomText)
        latesValue.text=restorableNumber.toString()
        changeMode()

    }

    fun changeMode(){
        val nightSwitch=findViewById<Switch>(R.id.nightMode)
        if (nightSwitch.isChecked) {
            val bg = findViewById<ConstraintLayout>(R.id.appBG)
            bg.setBackgroundResource(R.color.screenBackgroundNight)
        }
        else{
            val bg = findViewById<ConstraintLayout>(R.id.appBG)
            bg.setBackgroundResource(R.color.screenBackground)
        }

    }
}
