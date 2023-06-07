package com.example.taskmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.AppDatabase
import com.example.taskmanager.Item
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentItemDetailedInfoBinding

class ItemDetailedInfoFragment() : Fragment() {
    private var _binding: FragmentItemDetailedInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailedInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getDatabase(requireActivity().applicationContext)
        val itemDao = db.itemDao()

        val bundle = this.arguments
        val uid = bundle!!.getInt("id")
        Toast.makeText(context, "" + bundle.getInt("id"), Toast.LENGTH_LONG).show()
        val item = itemDao.getById(uid.toLong()) //todo

        val titleView = getView()?.findViewById<TextView>(R.id.titleView)
        val descriptionView = getView()?.findViewById<TextView>(R.id.descriptionView)
        val dateTimeView = getView()?.findViewById<TextView>(R.id.dateTimeView)
        titleView?.text = item.title
        descriptionView?.text = item.description
        dateTimeView?.text = item.creation_date
        //TODO?

        // Hide button
        if (item.isDone) {
            binding.buttonDone.visibility = View.INVISIBLE
        }

        binding.buttonBack.setOnClickListener {
            // Back Redirect
            val fragment = ListFragment()
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit()
        }

        binding.buttonDone.setOnClickListener {
            // Done Item
            itemDao.doneItem(uid)

            // Redirect
            val fragment = ListFragment()
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit()

            //Toast.makeText(context, R.string.text_done_item_successful, Toast.LENGTH_LONG).show()
            Toast.makeText(context, id.toString(), Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ItemDetailedInfoFragment()
    }
}