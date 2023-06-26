package com.example.piggy

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.piggy.utils.CustomAdapter
import com.example.piggy.utils.SpendItem

class MainActivity : AppCompatActivity() {
    private val list = mutableListOf<SpendItem>()
    private lateinit var listAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var Login = findViewById<Button>(R.id.Login)

        listAdapter = CustomAdapter(list)


        configAddButton()
        configListView()
        configCalc()

        Login.setOnClickListener {
            intent = Intent(this, login::class.java)

            intent.putExtra("Hi", "Working")
            startActivity(intent)
        }
    }

    private fun configAddButton() {
        val addBtn: Button = findViewById(R.id.addItem)
        addBtn.setOnClickListener {
            showInputDialog()
        }
    }

    private fun configListView() {
        val listView: RecyclerView = findViewById(R.id.recycle)
        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = listAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showInputDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.input_dialog, null)

        val nameField = dialogView.findViewById<EditText>(R.id.name)
        val priceField = dialogView.findViewById<EditText>(R.id.price)

        builder.setTitle("Add Spend Item")
        builder.setView(dialogView)

        builder.setPositiveButton("Add") { _, _ ->
            val label = nameField.text.toString()
            val amount = priceField.text.toString().toFloat()

            val item = SpendItem(label, amount)
            list.add(item)
            listAdapter.notifyDataSetChanged()

        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        builder.create().show()
    }

    private fun configCalc() {
        val calcBtn: Button = findViewById(R.id.Calculate)

        calcBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            val total = list.fold(0f) { acc, spendItem -> acc + spendItem.price }

            builder.setTitle("Total")
            builder.setMessage("$total")

            builder.create().show()
        }
    }
}
