package com.example.piggy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)


        var User= findViewById<EditText>(R.id.Username)
        var Pass= findViewById<EditText>(R.id.Password)
        var log= findViewById<Button>(R.id.Login)
        var grid_view= findViewById<GridView>(R.id.grid)
        var Share= findViewById<Button>(R.id.Share)
        var grid_display=findViewById<TextView>(R.id.grid_display)



        val aplications=listOf<String>("Instagram", "Facebook")

        // initialize a new array adapter instance
        val adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,aplications)

        // set the grid view adapter
        grid_view.adapter=adapter

        // set an Item click listener for grid view items
        grid_view.onItemClickListener=object: AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                // get the GridView selected/clicked item text

                val selectedItem=parent.getItemAtPosition(position).toString()


                grid_display.text="$selectedItem "


            }
        }
        var random= "hello"
        log.setOnClickListener {


            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Welcome",random)
            startActivity(intent)

        }


            Share.setOnClickListener {

                var intent = Intent()
                var msg =
                    "Hey there, Try out Piggie, the only financial planner you will ever need!! <URL>"
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, msg)
                intent.type = "text/plain "

                startActivity(Intent.createChooser(intent, "Share Via"))
            }
        }


    }
