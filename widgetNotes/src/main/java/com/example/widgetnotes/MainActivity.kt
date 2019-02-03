package com.example.widgetnotes

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView


import java.lang.Exception
import android.widget.Toast




class MainActivity : AppCompatActivity() {

    val fileName="widgetNotes.txt"
    val charset=Charsets.UTF_8
    lateinit var notesInput:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        }

        btnCancel.setOnClickListener(){
            readNotes()
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

}
