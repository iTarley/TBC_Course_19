package com.example.tbc_course_19.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tbc_course_19.R
import com.example.tbc_course_19.adapter.CustomAdapter
import com.example.tbc_course_19.databinding.FragmentMainBinding
import com.example.tbc_course_19.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response.error

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val adapter by lazy {
        CustomAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        viewModel.getContent()
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.apiResponse.collect{
                    when(it){
                        is MainViewModel.Resource.Success -> {
                            adapter.submitList(it.data)
                            binding.progressBar.visibility = View.INVISIBLE
                            Log.d("response", "onViewCreated: ${it.data}")
                        }
                        is MainViewModel.Resource.Error -> {
                            Log.d("ErrorResponse", it.message)
                            Snackbar.make(view,getString(R.string.error),Snackbar.LENGTH_LONG).show()
                        }
                        is MainViewModel.Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }



                    }
                }
            }
        }



    }



    private fun initRecycler(){
        binding.mainRecycler.adapter = adapter
        adapter.submitList(emptyList())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }







}