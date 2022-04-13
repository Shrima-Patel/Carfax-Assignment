package com.carfax.shrimaassignment.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.carfax.shrimaassignment.R
import com.carfax.shrimaassignment.databinding.FragmentVehicleListBinding
import com.carfax.shrimaassignment.ui.adapter.VehicleListAdapter
import com.carfax.shrimaassignment.ui.viewmodel.MainViewModel
import com.carfax.shrimaassignment.utils.AppUtils

class VehicleListFragment : Fragment() {

    private var _binding: FragmentVehicleListBinding? = null
    private val binding get() = _binding!!
    private lateinit var vehicleListAdapter: VehicleListAdapter
    private lateinit var shareViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVehicleListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.app_name)

        initRecyclerView()
        checkInternetConnected()

        binding.tryAgainButton.setOnClickListener {
            binding.internetErrorLinearLayout.visibility = View.INVISIBLE
            checkInternetConnected()
        }
    }

    private fun checkInternetConnected(){
        if(!AppUtils.checkForInternet(requireActivity())){
            binding.internetErrorLinearLayout.visibility = View.VISIBLE
        } else {
            createView()
        }
    }

    private fun createView(){
        shareViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        shareViewModel.getVehicleList().observe(requireActivity(), {
            if(it != null) {
                vehicleListAdapter.vehicleListData = it.listings
                vehicleListAdapter.notifyDataSetChanged()
                binding.internetErrorLinearLayout.visibility = View.INVISIBLE
            }
            else {
                checkInternetConnected()
                Toast.makeText(requireActivity(), "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        binding.vehicleListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            val decoration  = DividerItemDecoration(requireContext(), 0)
            addItemDecoration(decoration)
            vehicleListAdapter = VehicleListAdapter(context!!)
            adapter = vehicleListAdapter
        }
    }

}