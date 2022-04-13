package com.carfax.shrimaassignment.ui.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.carfax.shrimaassignment.data.model.VehicleList
import com.carfax.shrimaassignment.databinding.FragmentVehicleDetailsBinding
import com.carfax.shrimaassignment.ui.viewmodel.MainViewModel
import java.text.NumberFormat
import java.util.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.carfax.shrimaassignment.R

class VehicleDetailsFragment : Fragment() {

    private lateinit var shareViewModel: MainViewModel
    private lateinit var vehicleList: VehicleList
    private var position: Int = 0
    private lateinit var binding: FragmentVehicleDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = arguments?.getInt("position")!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentVehicleDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.hide()
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.app_name)

        shareViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        vehicleList = shareViewModel.getVehicleDetails(position)!!
        Glide.with(requireActivity())
            .load(vehicleList.images.firstPhoto.large)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(binding.vehicleImageView)
        binding.yearTextView.text = vehicleList.year.toString()
        binding.makeTextView.text = vehicleList.make
        binding.modelTextView.text = vehicleList.model
        binding.trimTextView.text = vehicleList.trim

        val price = "$" + NumberFormat.getNumberInstance(Locale.US).format(vehicleList.currentPrice)
        binding.priceTextView.text = price

        val mileage = (vehicleList.mileage.times(1.609).toFloat()).toString() + "k mi"
        binding.mileageTextView.text = mileage

        val location = vehicleList.dealer.city + ", " + vehicleList.dealer.state
        binding.locationTextView.text = location

        binding.exteriorColorTextView.text = vehicleList.exteriorColor
        binding.interiorColorTextView.text = vehicleList.interiorColor
        binding.driveTypeTextView.text = vehicleList.drivetype
        binding.transmissionTextView.text = vehicleList.transmission
        binding.bodyStyleTextView.text = vehicleList.bodytype
        binding.engineTextView.text = vehicleList.engine
        binding.fuelTextView.text = vehicleList.fuel

        binding.callDealerButton.setOnClickListener {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CALL_PHONE), 101)
                    return@setOnClickListener
                }
                makeACall(vehicleList.dealer.phone)
            } else {
                makeACall(vehicleList.dealer.phone)
            }
        }
    }

    private fun makeACall(phoneNumber:String){
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        requireActivity().startActivity(callIntent)
    }

}