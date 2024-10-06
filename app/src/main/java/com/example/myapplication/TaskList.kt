package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityTaskListBinding

class TaskList : AppCompatActivity() {

    private lateinit var binding: ActivityTaskListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get Intent Data
        val intent = intent
        val title = intent.getStringExtra("TITLE")
        val date = intent.getStringExtra("DATE")
        val time = intent.getStringExtra("TIME")
        val repeat = intent.getStringExtra("REPEAT")

        // Display the data in the TextViews
        binding.tittleText.text = title
        binding.tvDate.text = date

        // Extract hours and minutes from the time string if it's in the format HH:mm:ss
        val formattedTime = time?.let { formatTime(it) } ?: "Time Not Set"
        binding.textTime1.text = formattedTime
        binding.textTime2.text = repeat

        binding.btnAddTask.setOnClickListener {
            // Navigate to AddTask activity
            val addTaskIntent = Intent(this@TaskList, AddTask::class.java)
            startActivity(addTaskIntent)
        }
    }

    private fun formatTime(time: String): String {
        // Split the time string and return only hours and minutes
        val timeParts = time.split(":")
        return if (timeParts.size >= 2) {
            "${timeParts[0]}:${timeParts[1]}" // Return only HH:mm
        } else {
            "Invalid Time"
        }
    }
}
