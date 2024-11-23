package com.example.bookinghotel.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookinghotel.model.Room
import com.example.bookinghotel.R
import com.example.bookinghotel.data.Datasource
import com.example.bookinghotel.model.BookingUiState
import com.example.bookinghotel.ui.theme.BookingViewModel
import com.example.bookinghotel.ui.theme.theme.BookingHotelTheme
import java.nio.file.WatchEvent
import java.text.NumberFormat

@Composable
fun RoomDetailScreen(
    bookingViewModel: BookingViewModel = viewModel(),
    bookingUiState: BookingUiState,
    onValueChange: (Int) -> Unit = {},
    onCancelClick: () -> Unit = {},
    onBookClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {

    val uiState by bookingViewModel.uiState.collectAsState()
    var bookingQuantity by remember { mutableStateOf(uiState.newBookedQuantity.toString()) }

    Column(
        modifier = modifier
            .padding(vertical = 10.dp, horizontal = 5.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(height = 200.dp, width = 200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Room image",
                contentScale = ContentScale.Crop
            )
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Room image",
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Column() {
            Column() {
                Row {
                    Text(
                        text = stringResource(R.string.room_type),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacer_height)))

                    Text(
                        text = stringResource(bookingUiState.room?.type ?: R.string.error),
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

                    if(bookingUiState.room?.pricePerNight != null) {
                        Text(
                            text = NumberFormat.getCurrencyInstance()
                                .format(bookingUiState.room.pricePerNight),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height)))

                Row {
                    Text(
                        text = stringResource(R.string.amenities),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacer_height)))

                    Row() {
                        var i = 0
                        if (bookingUiState.room?.amenities != null) {
                            val amenities = bookingUiState.room.amenities
                            for (amenity in amenities) {

                                var text = stringResource(amenity)

                                i++

                                if (i != amenities.size) {
                                    text = text + ","
                                }

                                Text(
                                    text = text,
                                    style = MaterialTheme.typography.bodyLarge,
                                )

                                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacer_width)))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Column(

            ) {
                Text(
                    text = stringResource(R.string.booking_quantity),
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height)))

                OutlinedTextField(
                    value = bookingQuantity,
                    onValueChange = { it ->
                        bookingQuantity = it
                        if(it.toIntOrNull() != null)
                            onValueChange(it.toInt())
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                )
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height)))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            OutlinedButton(
                onClick = onCancelClick,
                modifier = modifier
                    .sizeIn(minHeight = 30.dp, minWidth = 100.dp)
            ) {
                Text(
                    text = stringResource(R.string.cancel_button)
                )
            }

            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacer_width)))

            Button(
                onClick = onBookClick,
                modifier = modifier
                    .sizeIn(minHeight = 30.dp, minWidth = 100.dp)
            ) {
                Text(
                    text = stringResource(R.string.booking_button)
                )
            }
        }
    }
}

@Preview
@Composable
fun RoomDetailScreenPreview() {
    BookingHotelTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF8E7EB)
        ) {
            RoomDetailScreen(bookingUiState = BookingUiState(
                room = Datasource.rooms[1]
            ))
        }
    }
}