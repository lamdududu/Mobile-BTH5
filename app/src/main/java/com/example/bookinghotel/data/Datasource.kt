package com.example.bookinghotel.data

import com.example.bookinghotel.model.Room
import com.example.bookinghotel.R

object Datasource {
    val rooms = listOf (
        Room(
            id = R.string.suite_room_id,
            type = R.string.standard_room,
            pricePerNight = 30,
            amenities = listOf(
                R.string.gym_amenity,
                R.string.pool_amenity,
            ),
            availableRooms = 10,
        ),

        Room(
            id = R.string.deluxe_room_id,
            type = R.string.deluxe_room,
            pricePerNight = 40,
            amenities = listOf(
                R.string.gym_amenity,
                R.string.pool_amenity,
                R.string.breakfast_amenity,
            ),
            availableRooms = 5,
        ),

        Room(
            id = R.string.suite_room_id,
            type = R.string.suite_room,
            pricePerNight = 50,
            amenities = listOf(
                R.string.gym_amenity,
                R.string.pool_amenity,
                R.string.lunch_amenity,
            ),
            availableRooms = 5,
        ),

        Room(
            id = R.string.executive_room_id,
            type = R.string.executive_room,
            pricePerNight = 60,
            amenities = listOf(
                R.string.gym_amenity,
                R.string.pool_amenity,
                R.string.breakfast_amenity,
                R.string.dinner_amenity,
            ),
            availableRooms = 3,
        ),

        Room(
            id = R.string.family_room_id,
            type = R.string.family_room,
            pricePerNight = 70,
            amenities = listOf(
                R.string.gym_amenity,
                R.string.pool_amenity,
                R.string.breakfast_amenity,
            ),
            availableRooms = 2,
        )
    )
}