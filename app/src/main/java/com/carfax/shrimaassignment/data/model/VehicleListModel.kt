package com.carfax.shrimaassignment.data.model

data class VehicleListModel(
    val listings: ArrayList<VehicleList>
)

data class VehicleList(
    val images: VehicleImage,
    val year: Int,
    val make: String,
    val model: String,
    val trim: String,
    val currentPrice: Int,
    val mileage: Int,
    val dealer: DealerDetails,
    val interiorColor: String,
    val exteriorColor: String,
    val drivetype: String,
    val transmission: String,
    val engine: String,
    val bodytype: String,
    val fuel: String
)

data class DealerDetails(
    val city: String,
    val state: String,
    val phone: String
)

data class VehicleImage(
    val firstPhoto: PhotoSize
)

data class PhotoSize(
    val large: String
)