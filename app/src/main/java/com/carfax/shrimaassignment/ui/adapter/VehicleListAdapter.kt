package com.carfax.shrimaassignment.ui.adapter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carfax.shrimaassignment.R
import com.carfax.shrimaassignment.data.model.VehicleList
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.os.Build
import androidx.navigation.findNavController
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.carfax.shrimaassignment.ui.view.VehicleListFragmentDirections

class VehicleListAdapter(private val context: Context): RecyclerView.Adapter<VehicleListAdapter.MyViewHolder>() {

    var vehicleListData = ArrayList<VehicleList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_vehiclelist_items, parent, false )
        return MyViewHolder(inflater, parent.context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(vehicleListData[position])
        holder.vehicleCallDealer.setOnClickListener {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CALL_PHONE), 101)
                    return@setOnClickListener
                }
                makeACall(vehicleListData[position].dealer.phone)
            } else {
                makeACall(vehicleListData[position].dealer.phone)
            }
        }

        holder.itemView.setOnClickListener {
            val action = VehicleListFragmentDirections.actionVehicleListFragmentToVehicleDetailsFragment(position)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return vehicleListData.size
    }

    class MyViewHolder(view: View, private val context: Context): RecyclerView.ViewHolder(view) {
        private val vehicleImageView:ImageView = view.findViewById(R.id.vehicleImageView)
        private val vehicleYear:TextView = view.findViewById(R.id.yearTextView)
        private val vehicleMake:TextView = view.findViewById(R.id.makeTextView)
        private val vehicleModel:TextView = view.findViewById(R.id.modelTextView)
        private val vehicleTrim:TextView = view.findViewById(R.id.trimTextView)
        private val vehiclePrice:TextView = view.findViewById(R.id.priceTextView)
        private val vehicleMileage:TextView = view.findViewById(R.id.mileageTextView)
        private val vehicleLocation:TextView = view.findViewById(R.id.locationTextView)
        val vehicleCallDealer: Button = view.findViewById(R.id.callDealerButton)

        fun bind(data : VehicleList?){
            vehicleYear.text = data?.year.toString()
            vehicleMake.text = data?.make
            vehicleModel.text = data?.model
            vehicleTrim.text = data?.trim

            val price = "$" + NumberFormat.getNumberInstance(Locale.US).format(data?.currentPrice)
            vehiclePrice.text = price

            val mileage = (data?.mileage?.times(1.609)?.toFloat()).toString() + "k mi"
            vehicleMileage.text = mileage

            val location = data?.dealer?.city + ", " + data?.dealer?.state
            vehicleLocation.text = location

            val url  = data?.images?.firstPhoto?.large
            Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(vehicleImageView)
        }
    }

    private fun makeACall(phoneNumber:String){
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        context.startActivity(callIntent)
    }
}