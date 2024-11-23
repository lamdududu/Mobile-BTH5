package com.example.bookinghotel.ui.theme.screens

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookinghotel.data.Datasource
import com.example.bookinghotel.model.Room
import com.example.bookinghotel.ui.theme.theme.BookingHotelTheme
import com.example.bookinghotel.R
import com.example.bookinghotel.ui.theme.BookingViewModel
import java.text.NumberFormat

@Composable
fun RoomListScreen(
    bookingViewModel: BookingViewModel = viewModel(),
    rooms: List<Room>,
    onClick: (Room) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val uiState by bookingViewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 10.dp, horizontal = 5.dp)
    ) {
        items(rooms) { room ->
            RoomCard(
                room = room,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_card))
                    .clickable (
                        onClick = {
                            onClick(room)
                            Log.d("LOG DETAILS", "${bookingViewModel.uiState.value.room?.type ?: "error"}")
                        }
                    )
            )
//                                }
//                            }
        }
    }
    //LaunchedEffect đảm bảo điều hướng chỉ xảy ra khi uiState đã được cập nhật
////    LaunchedEffect(uiState.room) {
//        if (uiState.room != null) {
//            onClick()
//            Log.d("LOG DETAILS", "${bookingViewModel.uiState.value.room?.type ?: "error"}. Launch effect")
//        }
////    }
}

@Composable
fun RoomCard(
    room: Room,
    modifier: Modifier,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation_card))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "Room image",
                )
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "Room image"
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row {
                    Text(
                        text = stringResource(R.string.room_type),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacer_width)))

                    Text(
                        text = stringResource(room.type),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height)))

                Row {
                    Text(
                        text = stringResource(R.string.room_available),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacer_height)))

                    Text(
                        text = room.availableRooms.toString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height)))

                Row {
                    Text(
                        text = stringResource(R.string.price),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacer_height)))

                    Text(
                        text = NumberFormat.getCurrencyInstance().format(room.pricePerNight),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RoomListScreenPreview() {
    BookingHotelTheme {
        Surface (
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RoomListScreen(rooms = Datasource.rooms)
        }
    }
}