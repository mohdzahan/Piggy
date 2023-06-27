package com.example.piggy

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.piggy.utils.CalendarStore
import com.example.piggy.utils.CustomAdapter
import com.example.piggy.utils.SpendItem
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val list = mutableListOf<SpendItem>()
    private val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, Locale.UK)

    private lateinit var listAdapter: CustomAdapter
    private lateinit var calendarView: CalendarView
    private lateinit var curDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configAddButton()
        configLoginButton()
        configListView()
        configCalc()
        configCalendar()
    }

    private fun configAddButton() {
        val addBtn: Button = findViewById(R.id.addItem)
        addBtn.setOnClickListener {
            showInputDialog()
        }
    }

    private fun configLoginButton() {
        var login = findViewById<Button>(R.id.Login)
        login.setOnClickListener {
            intent = Intent(this, com.example.piggy.LoginActivity::class.java)

            intent.putExtra("Hi", "Working")
            startActivity(intent)
        }
    }

    private fun configListView() {
        listAdapter = CustomAdapter(list)
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

            CalendarStore.putItems(curDate, list.toMutableList())

            listAdapter.notifyDataSetChanged()
            println(CalendarStore.toString())
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

            builder.setNegativeButton("Close") {dialog, which ->
                dialog.cancel()
            }

            builder.create().show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun configCalendar() {
        calendarView = findViewById(R.id.calendar)

        curDate = getFormattedDateString()

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val dayStr = dayOfMonth.toString().padStart(2, '0')
            val monthStr = month.toString().padStart(2, '0')
            curDate = "$dayStr-${monthStr}-${year}"
            println(curDate)
            val newList = CalendarStore.getItems(curDate)

            list.clear()
            list.addAll(newList)
            listAdapter.notifyDataSetChanged()
        }
    }

    private fun getFormattedDateString(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}
