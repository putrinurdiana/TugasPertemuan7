package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import com.example.myapplication.AddTask
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.AddTask

class MainActivity : AppCompatActivity() {

    // Deklarasi binding untuk ActivityMain
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tambahkan logika untuk klik tombol
        binding.btnAddTask.setOnClickListener {
            // Intent untuk pindah ke AddTaskActivity
            val intent = Intent(this@MainActivity, AddTask::class.java)
            startActivity(intent)
        }
    }
}
