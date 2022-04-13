package com.carfax.shrimaassignment.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carfax.shrimaassignment.data.model.VehicleList
import com.carfax.shrimaassignment.data.model.VehicleListModel
import com.carfax.shrimaassignment.data.repository.MainRepository

class MainViewModel: ViewModel() {

    private var vehicleList = MutableLiveData<VehicleListModel?>()
    private var mainRepository = MainRepository()

    init {
        mainRepository.makeApiCall()
        vehicleList = mainRepository.getVehiclesList()
    }

    fun getVehicleList() : MutableLiveData<VehicleListModel?> = vehicleList

    fun getVehicleDetails(position: Int): VehicleList? {
        return vehicleList.value?.listings?.get(position)
    }

}