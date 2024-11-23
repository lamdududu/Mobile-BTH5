package com.example.bookinghotel.model

import androidx.annotation.StringRes

data class Room(
    @StringRes val id: Int,
    @StringRes val type: Int,
    val pricePerNight: Int,
    val amenities: List<Int>,
    var availableRooms: Int,
)
