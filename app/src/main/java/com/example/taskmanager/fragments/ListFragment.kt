package com.example.taskmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.AppDatabase
import com.example.taskmanager.CustomAdapter
import com.example.taskmanager.Item
import com.example.taskmanager.ItemsFragmentViewModel
import com.example.taskmanager.ItemsViewModel
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentListBinding
import com.google.android.material.tabs.TabLayout


class ListFragment : Fragment(), CustomAdapter.Listener {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    val viewModel: ItemsFragmentViewModel by viewModels()

    private var dataItems: ArrayList<ItemsViewModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Создание БД
//        val db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java, "items"
//        ).allowMainThreadQueries().build() //убрать allowMainThreadQueries (либо в отдельном потоке либо корутины)


        val context = requireActivity().applicationContext
        val db = AppDatabase.getDatabase(requireActivity().applicationContext)
        val itemDao = db.itemDao()

        //var item = Item(1, "Test new") //todo

//        viewModel.insertTransaction(
//            1,
//            "Test new"
//        )
        //itemDao.insertItem(item)




        itemDao.getAll().asLiveData().observe(getViewLifecycleOwner()) {list ->

            val recyclerview = getView()?.findViewById<RecyclerView>(R.id.recyclerview)

            recyclerview?.layoutManager = LinearLayoutManager(context)

            dataItems = ArrayList<ItemsViewModel>()

            list.forEach {
                dataItems.add(ItemsViewModel(it.uid!!, it.title!!, it.description!!, it.isDone, it.creation_date!!)) //TODO  String -> String?
            }

            val adapter = CustomAdapter(dataItems, this)

            recyclerview?.adapter = adapter
        }

        binding.button.setOnClickListener {
            val fragment = CreateFragment()
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit()
        }

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    TAB_ALL -> {
                        binding.recyclerview.adapter = CustomAdapter(
                            dataItems,
                            this@ListFragment)
                    }
                    TAB_DONE -> {
                        binding.recyclerview.adapter = CustomAdapter(
                            dataItems.filter { element -> element.isDone },
                            this@ListFragment)
                    }
                    TAB_UNDONE -> {
                        binding.recyclerview.adapter = CustomAdapter(
                            dataItems.filter { element -> !element.isDone },
                            this@ListFragment)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }

            override fun onTabReselected(tab: TabLayout.Tab?) { }

        })
    }

    companion object {
        private val TAB_ALL = 0
        private val TAB_DONE = 1
        private val TAB_UNDONE = 2

        @JvmStatic
        fun newInstance() = ListFragment()
    }

    override fun onClick(id: Int) { //todo Int? -> Long
        val bundle = Bundle()
        bundle.putInt("id", id)
        val fragment = ItemDetailedInfoFragment()
        fragment.arguments = bundle
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }
}