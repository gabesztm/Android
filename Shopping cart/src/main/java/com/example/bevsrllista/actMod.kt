package com.example.bevsrllista

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

class actMod : AppCompatActivity() {

    val fileName="widgetNotes.txt"
    val charset=Charsets.UTF_8
    lateinit var notesInput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_mod)
        notesInput = findViewById<TextView>(R.id.mtNotes)
        notesInput.setMovementMethod(ScrollingMovementMethod())

        try {
            readNotes()
        }
        catch (e: Exception){
            println(e)
        }

        val btnSave=findViewById<Button>(R.id.btnSave)
        val btnCancel=findViewById<Button>(R.id.btnCancel)

        btnSave.setOnClickListener(){
            saveNotes()
            navigateToMain()
        }

        btnCancel.setOnClickListener(){
            readNotes()
            navigateToMain()
        }
    }

    fun readNotes(){
        val fIn=openFileInput(fileName)
        val notesByteArray=fIn.readBytes()
        val notesToRead=notesByteArray.toString(charset)
        fIn.close()
//        val notesInput = findViewById<TextView>(R.id.mtNotes)
        notesInput.text=notesToRead



    }


    fun saveNotes() {
//        val notesInput = findViewById<TextView>(R.id.mtNotes)
        val notes = notesInput.text
        val notesToSave = notes.toString()
        val fOut = openFileOutput(fileName, Context.MODE_PRIVATE)
        fOut.write(notesToSave.toByteArray(charset))
        fOut.close()
        val toast = Toast.makeText(applicationContext, "Sikeresen mentve!", Toast.LENGTH_SHORT)
        toast.show()



    }

    fun navigateToMain(){
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
