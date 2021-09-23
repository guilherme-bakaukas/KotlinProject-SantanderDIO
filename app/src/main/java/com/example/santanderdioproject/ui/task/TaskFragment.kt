package com.example.santanderdioproject.ui.task


import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.santanderdioproject.R
import com.example.santanderdioproject.databinding.FragmentTaskBinding
import com.example.santanderdioproject.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar


class TaskFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentTaskBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = TaskAdapter(TaskClickListener { taskEntry ->
            findNavController().navigate( TaskFragmentDirections.actionTaskFragmentToUpdateFragment(taskEntry))
        })

        viewModel.getAllTasks.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        binding.apply {

            binding.recyclerView.adapter = adapter

            floatingActionButton.setOnClickListener {
                Log.d(TAG, "Clickado!")
                findNavController().navigate(R.id.action_taskFragment_to_addFragment)
            }
        }


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback( 0 ,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val taskEntry = adapter.currentList[position]
                viewModel.delete(taskEntry)

                Snackbar.make(binding.root, "Deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.insert(taskEntry)
                    }
                    show()
                }
            }

        }

        ).attachToRecyclerView((binding.recyclerView))

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_delete_all -> deleteAllItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllItem() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete All")
            .setMessage("Are you sure")
            .setPositiveButton("Yes"){dialog, _ ->
                viewModel.deleteAll()
                dialog.dismiss()
            }.setNegativeButton("No"){dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

}