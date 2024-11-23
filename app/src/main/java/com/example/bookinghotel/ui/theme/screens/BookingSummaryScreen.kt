package com.example.bookinghotel.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookinghotel.data.Datasource
import com.example.bookinghotel.model.BookingUiState
import com.example.bookinghotel.ui.theme.theme.BookingHotelTheme
import com.example.bookinghotel.R
import java.text.NumberFormat

@Composable
fun BookingSummaryScreen(
    bookingUiState: BookingUiState,
    onCancelClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
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

        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.successful_notification),
                style = MaterialTheme.typography.titleLarge,
                color = Color.Green,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
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

                                if (i != bookingUiState.room.amenities.size) {
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

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height)))

                Row {
                    Text(
                        text = stringResource(R.string.booked_quantity),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacer_height)))

                    Text(
                        text = bookingUiState.bookedQuantity.toString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height)))

                Row {
                    Text(
                        text = stringResource(R.string.total_payment),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacer_height)))

                    Text(
                        text = NumberFormat.getCurrencyInstance().format(bookingUiState.totalPayment),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height)))

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height)))

                Row {
                    Text(
                        text = stringResource(R.string.warning_notification),
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Red
                    )
                }

            }

            Spacer(modifier = Modifier.height(25.dp))

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

        }
    }
}

@Preview
@Composable
fun BookingSummaryScreenPreview() {
    BookingHotelTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BookingSummaryScreen(
                bookingUiState = BookingUiState(
                    room = Datasource.rooms[1],
                    availableRoom = Datasource.rooms[1].availableRooms,
                    bookedQuantity = 1,
                    totalPayment = Datasource.rooms[1].pricePerNight
                )
            )
        }
    }
}