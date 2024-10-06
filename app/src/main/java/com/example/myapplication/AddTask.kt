package com.example.myapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityAddTaskBinding
import java.util.*

class AddTask : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private var selectedTime: String? = null // Menyimpan waktu yang dipilih

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Click Listeners
        binding.tvDate.setOnClickListener { showDatePicker() }
        binding.timeText.setOnClickListener { setupTimeInputWatcher() }

        // Setup Spinner for Repeat Options
        val repeatOptions = arrayOf("Once", "Daily", "Mon to Fri")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, repeatOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRepeat.adapter = adapter // Set the adapter to the Spinner

        // Initialize the TextWatcher for time inputs
        setupTimeInputWatcher()
        binding.btnAddTask.setOnClickListener {
            // Show confirmation dialog
            showConfirmationDialog()
        }

    }
    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("SimpliRemind")
            .setMessage("Do you want to add this as new task?")
            .setPositiveButton("Yes") { _, _ ->
                // User clicked Yes, proceed to add the task
                if (isTimeValid()) {
                    val resultIntent = Intent(this@AddTask, TaskList::class.java).apply {
                        putExtra("DATE", binding.tvDate.text.toString())
                        putExtra("TIME", selectedTime)
                        putExtra("REPEAT", binding.spinnerRepeat.selectedItem.toString())
                        putExtra(
                            "TITLE",
                            binding.textTitle.text.toString()
                        ) // Get title from EditText
                    }
                    startActivity(resultIntent)
                    finish() // Close AddTask activity
                } else {
                    // Show a message to the user about invalid time
                    AlertDialog.Builder(this)
                        .setTitle("Invalid Time")
                        .setMessage("Please enter a valid time.")
                        .setPositiveButton("OK", null)
                        .show()
                }
            }
            .setNegativeButton("No") { dialog, _ ->
                // User clicked No, dismiss the dialog
                dialog.dismiss()
            }
            .show()
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear" // Format as DD/MM/YYYY
            binding.tvDate.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun setupTimeInputWatcher() {
        binding.teks1.addTextChangedListener(timeInputWatcher)
        binding.teks3.addTextChangedListener(timeInputWatcher)
        binding.teks5.addTextChangedListener(timeInputWatcher)
    }

    private val timeInputWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Whenever input changes, update the time display
            updateTimeDisplay()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun updateTimeDisplay() {
        val hour = binding.teks1.text.toString().toIntOrNull() ?: 0
        val minute = binding.teks3.text.toString().toIntOrNull() ?: 0
        val second = binding.teks5.text.toString().toIntOrNull() ?: 0

        // Validate hour, minute and seconds to ensure they are within acceptable ranges
        if (hour in 0..23 && minute in 0..59 && second in 0..59) {
            selectedTime = String.format("%02d:%02d:%02d", hour, minute, second)
        } else {
            selectedTime = null // Invalidate selected time if inputs are out of range
        }
    }

    private fun isTimeValid(): Boolean {
        return selectedTime != null
    }
}
