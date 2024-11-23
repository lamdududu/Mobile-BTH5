package com.example.bookinghotel.ui.theme

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.bookinghotel.data.Datasource
import com.example.bookinghotel.model.Room
import com.example.bookinghotel.model.BookingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookingViewModel : ViewModel () {
    private val _uiState = MutableStateFlow(BookingUiState())
    val uiState: StateFlow<BookingUiState> = _uiState.asStateFlow()

    fun getUiStateRoom(room: Room) {
        _uiState.update { currentState ->
            currentState.copy(
                room = room,
                availableRoom = room.availableRooms,
                bookedQuantity = 0,
                newBookedQuantity = 1,
                totalPayment = 0,
                bookingSuccess = false,
            )
        }
        Log.d("LOG DETAILS", "${_uiState.value.room?.type ?: "error"}")
    }

    fun onUpdateNewBooking(quantity: Int = 1) {
//        if(_uiState.value.newBookedQuantity == quantity)
//            return
////        if(_uiState.value.newBookedQuantity != quantity) {
//            _uiState.update { currentState ->
//                currentState.copy(newBookedQuantity = quantity)
//            }
//            Log.d("LOG BOOKING", "${_uiState.value.newBookedQuantity} update new booking")
////        }
        _uiState.value.newBookedQuantity = quantity
    }

    fun onBookClick() {
        Log.d("LOG BOOKING", "${_uiState.value.bookedQuantity} ${_uiState.value.newBookedQuantity}")
        _uiState.update { currenState ->
            val availableRoom = currenState.availableRoom - currenState.newBookedQuantity
            val bookedQuantity = currenState.bookedQuantity + currenState.newBookedQuantity
            var totalPayment = currenState.room?.pricePerNight ?: 0

            if(totalPayment != 0) {
                totalPayment = totalPayment * currenState.newBookedQuantity
            }

            currenState.copy(
                availableRoom = availableRoom,
                bookedQuantity = bookedQuantity,
                totalPayment = totalPayment,
                bookingSuccess = false
            )
        }
    }

    fun onCancelClick() {
        _uiState.update { currentState ->
            currentState.copy(
                room = null,
                availableRoom = currentState.room?.availableRooms ?: 0,
                bookedQuantity = 0,
                newBookedQuantity = 1,
                totalPayment = 0,
                bookingSuccess = false,
            )
        }
    }

    fun updateBookingSucess() {
        _uiState.update { currentState ->
            currentState.copy(
                bookingSuccess = !currentState.bookingSuccess
            )
        }
    }

    fun checkRoom(): Boolean {
        val quantity = _uiState.value.newBookedQuantity + _uiState.value.bookedQuantity
        if(quantity > (_uiState.value.room?.availableRooms ?: 0))
            return false
        else return true
    }

    fun getRoom(): Room {
        return uiState.value.room ?: Datasource.rooms[1]
    }
}