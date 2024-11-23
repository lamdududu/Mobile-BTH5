package com.example.bookinghotel.ui.theme

import android.content.ContentProviderClient
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.bookinghotel.R
import com.example.bookinghotel.data.Datasource
import com.example.bookinghotel.model.BookingUiState
import com.example.bookinghotel.ui.theme.screens.BookingSummaryScreen
import com.example.bookinghotel.ui.theme.screens.RoomDetailScreen
import com.example.bookinghotel.ui.theme.screens.RoomListScreen

enum class BookingHotelScreen(
    @StringRes val title: Int
) {
    Start(title = R.string.room_list),
    Details(title = R.string.room_details),
    Booking(title = R.string.booking_summary)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingHotelAppTopBar(
    @StringRes currentScreenTitle: Int,
    bookingViewModel: BookingViewModel = viewModel(),
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(currentScreenTitle)
            )
        },
        modifier = modifier,
        navigationIcon = {
            if(canNavigateBack) {
                IconButton(
                    onClick = navigateUp

                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back button"
                    )
                }
            }
        }
    )
}

@Composable
fun BookingHotelApp(
   bookingViewModel: BookingViewModel = viewModel(),
   navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BookingHotelScreen.valueOf(
        backStackEntry?.destination?.route ?: BookingHotelScreen.Start.name
    )

    Scaffold(
        topBar = {
            BookingHotelAppTopBar(
                currentScreenTitle = currentScreen.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by bookingViewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = BookingHotelScreen.Start.name,
            modifier = Modifier
                .padding(innerPadding),
        ) {
            composable(route = BookingHotelScreen.Start.name) {
                RoomListScreen(
                    rooms = Datasource.rooms,
                    onClick = { room ->
                        bookingViewModel.getUiStateRoom(room)
                        navController.navigate(BookingHotelScreen.Details.name)
                    },
                    modifier = Modifier
                        .padding(innerPadding)
                )
            }

            composable(route = BookingHotelScreen.Details.name) {
                RoomDetailScreen(
                    bookingUiState = uiState,
                    onValueChange = { newBooking ->
                        if (newBooking != null) {
                            Log.d("LogPrice", "$newBooking newBooking")
                            bookingViewModel.onUpdateNewBooking(newBooking)
                            Log.d("LogPrice", "${uiState.newBookedQuantity}")
                        }
                    },
                    onBookClick = {
                        if(bookingViewModel.checkRoom() == true) {
                            bookingViewModel.onBookClick()
                            navController.navigate(BookingHotelScreen.Booking.name)
                        }
                        else {
                            bookingViewModel.updateBookingSucess()
                        }
                        Log.d("LOG BOOKING", "${uiState.bookedQuantity} ${uiState.newBookedQuantity} Booking app room details")
                    },
                    onCancelClick = {
                        bookingViewModel.onCancelClick()
                        navController.popBackStack(BookingHotelScreen.Start.name, inclusive = false)
                    },
                    modifier = Modifier
                        .padding(innerPadding)
                )
            }

            composable(route = BookingHotelScreen.Booking.name) {
                Log.d("LOG BOOKING", "${uiState.bookedQuantity} ${uiState.newBookedQuantity} Booking app booking summary")
                BookingSummaryScreen(
                    bookingUiState = uiState,
                    onCancelClick = {
                        bookingViewModel.onCancelClick()
                        navController.popBackStack(BookingHotelScreen.Start.name, inclusive = false)
                    },
                    modifier = Modifier
                        .padding(innerPadding)
                )
            }
        }
        warningBooking(uiState, bookingViewModel)
    }
}

@Composable
fun warningBooking(
    bookingUiState: BookingUiState,
    bookingViewModel: BookingViewModel = viewModel()
) {
    if(bookingUiState.bookingSuccess) {
        AlertDialog(
            onDismissRequest = { bookingViewModel.updateBookingSucess() },
            text = {
                Text(
                    text = stringResource(R.string.warning_booking),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Red
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { bookingViewModel.updateBookingSucess() }
                ) {
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        )
    }
}