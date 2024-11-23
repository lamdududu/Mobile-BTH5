package com.example.bookinghotel.model

data class BookingUiState(
    val room: Room? = null,
    val availableRoom: Int = room?.availableRooms ?: 0,
//    val pricePerNight: Int = room?.pricePerNight ?: 0,
    val bookedQuantity: Int = 0,
    var newBookedQuantity: Int = 1,
    val totalPayment: Int = 0,
    val bookingSuccess: Boolean = false,
)
