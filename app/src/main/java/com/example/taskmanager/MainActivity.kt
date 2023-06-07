package com.example.taskmanager

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.fragment.app.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.databinding.ActivityMainBinding
import com.example.taskmanager.fragments.CreateFragment
import com.example.taskmanager.fragments.CreateItemFragment
import com.example.taskmanager.fragments.ListFragment
//import com.example.eduapplication.data.local.FinanceManagerDatabase
//import com.example.eduapplication.data.local.entity.TransactionEntity
//import com.example.eduapplication.domain.enums.TransactionType
//import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var binding: ActivityMainBinding

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val viewModel: ItemsFragmentViewModel by viewModels()




        //val button = findViewById<Button>(R.id.button)


 // todo Заменить класс App на использование dagger

        val fragment = ListFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }
}