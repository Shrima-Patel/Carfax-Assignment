package com.carfax.shrimaassignment.data.network

import com.carfax.shrimaassignment.data.model.VehicleListModel
import io.reactivex.Observable
import retrofit2.http.GET

interface RetroService {

    @GET("assignment.json")
    fun getVehicleListFromApi(): Observable<VehicleListModel>

}