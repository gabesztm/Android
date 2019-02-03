package com.example.bevsrllista

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener



class MainActivity : AppCompatActivity() {

    val fileName="widgetNotes.txt"
    val charset=Charsets.UTF_8
    lateinit var goodsList:MutableList<String>
    lateinit var itemsAdapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            initGoods()
            itemsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, goodsList)
            val lvList = findViewById<ListView>(R.id.lvGoods)
            lvList.adapter = itemsAdapter
            lvList.onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {


                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle(goodsList[position])
                    builder.setMessage(R.string.dialogMessage)
                    builder.setPositiveButton(R.string.yes){dialog, which ->
                       deleteGoods(position,goodsList, itemsAdapter, lvList)

                    }
                    builder.setNegativeButton(R.string.no){dialog, which ->  }
                    val dialog=builder.create()
                    dialog.show()




                }
            }


        }
        catch (e: Exception){
            println(e)
        }

        val btnMod=findViewById<Button>(R.id.btnModify)
        btnMod.setOnClickListener(){
            val intent=Intent(this, actMod::class.java)
            startActivity(intent)
        }
    }
    fun readGoods():String {
        val fIn = openFileInput(fileName)
        val notesByteArray = fIn.readBytes()
        val notesToRead = notesByteArray.toString(charset)
        fIn.close()
        return notesToRead
    }

    fun initGoods(){
        val goodsString = readGoods()
        goodsList = goodsString.lines().toMutableList()

    }

    fun deleteGoods(position:Int, goodslist:MutableList<String>, adapter:ArrayAdapter<String>, lvlist:ListView){
        val convertedList= goodslist.toMutableList()

        convertedList.removeAt(position)
        var goods = ""


        convertedList.forEach{
            goods+= it
            goods+="\n"

        }
        val goodsToSave=goods.removeSuffix("\n")
        val fOut = openFileOutput(fileName, Context.MODE_PRIVATE)
        fOut.write(goodsToSave.toByteArray(charset))
        fOut.close()
        initGoods()
        itemsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, goodsList)
        lvlist.adapter = itemsAdapter


    }



}
