package com.example.taskmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.taskmanager.AppDatabase
import com.example.taskmanager.Item
import com.example.taskmanager.ItemsFragmentViewModel
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentCreateBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date


class CreateFragment : Fragment() {
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    val viewModel: ItemsFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getDatabase(requireActivity().applicationContext)
        val itemDao = db.itemDao()

        binding.buttonCreate.setOnClickListener {
            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
            val dateTimeNow: String = formatter.format(Date())

            // Create Item
            var item = Item(
                null,
                binding.etTitle.text.toString(),
                binding.etDescription.text.toString(),
                false,
                dateTimeNow
            ) //todo переименовать на ItemEntity? + поместить по папкам
            itemDao.insertItem(item)

//            viewModel.insertTransaction(
//                binding.etTitle.text.toString(),
//                binding.etDescription.text.toString(),
//                false
//            ) TODO

            // Redirect
            val fragment = ListFragment()
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit()

            Toast.makeText(context, R.string.text_create_item_successful, Toast.LENGTH_LONG).show()
        }

        binding.buttonBack.setOnClickListener {
            // Back Redirect
            val fragment = ListFragment()
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreateFragment()
    }
}