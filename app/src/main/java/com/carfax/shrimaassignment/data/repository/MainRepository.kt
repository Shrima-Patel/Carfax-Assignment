package com.carfax.shrimaassignment.data.repository

import androidx.lifecycle.MutableLiveData
import com.carfax.shrimaassignment.data.model.VehicleListModel
import com.carfax.shrimaassignment.data.network.RetroInstance
import com.carfax.shrimaassignment.data.network.RetroService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainRepository {

    private var vehicleList = MutableLiveData<VehicleListModel?>()

    fun getVehiclesList(): MutableLiveData<VehicleListModel?> {
        return vehicleList
    }

    fun makeApiCall() {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        retroInstance.getVehicleListFromApi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getBookListObserverRx())
    }

    private fun getBookListObserverRx(): Observer<VehicleListModel> {
        return object : Observer<VehicleListModel> {
            override fun onComplete() {
            }

            override fun onError(e: Throwable) {
                vehicleList.postValue(null)
            }

            override fun onNext(t: VehicleListModel) {
                vehicleList.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {
            }
        }
    }

}