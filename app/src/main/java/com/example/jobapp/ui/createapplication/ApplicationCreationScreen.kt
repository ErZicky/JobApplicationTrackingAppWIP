package com.example.jobapp.ui.createapplication

import android.graphics.ColorFilter
import androidx.activity.viewModels
import androidx.appcompat.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jobapp.ui.theme.CBackgroundColor
import com.example.jobapp.ui.theme.CText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.jobapp.data.model.Status
import com.example.jobapp.ui.theme.CPrimary
import com.example.jobapp.ui.createapplication.AddStatusDialog
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import java.net.URL
import android.location.Geocoder
import android.preference.PreferenceManager
import android.view.ViewGroup
import androidx.compose.foundation.gestures.snapping.SnapPosition.Center.position
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.io.IOException

var selectedMillis : Long?  = Date().time;

var statusOptions =  mutableListOf<Status>()
var selectedStatus = "Submitted"



@Composable
fun ApplicationCreationScreen(
    onDismiss : () -> Unit,
    viewModel: ApplicationCreationViewModel = viewModel(factory = ApplicationCreationViewModel.Factory),
    statusViewModel: StatusViewModel = viewModel(factory = StatusViewModel.Factory))
{

    val context = LocalContext.current

    //osm configuration
    remember {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        Configuration.getInstance().userAgentValue = context.packageName // Identifica la tua app
        true
    }


    var companyField by remember { mutableStateOf("") }
    var positionField by remember { mutableStateOf("") }
    var jobLinkField by remember { mutableStateOf("") }
    var companyLinkField  by remember { mutableStateOf("") }
    var companyLocation  by remember { mutableStateOf("") }
    var domain : String?


    val mapCenter by viewModel.mapCenter.collectAsState()
    val mapZoom by viewModel.mapZoom.collectAsState()
    val markerPoint by viewModel.markerPoint.collectAsState()
    val markerPointOverlay by viewModel.markerPointTitle.collectAsState()


    val statusOptionsTmp by statusViewModel.statusList.collectAsState()



    statusOptions = statusOptionsTmp as MutableList<Status>;
    statusOptions.add(Status(0,"+ new status"))



    Scaffold(

        modifier = Modifier
            .background(CBackgroundColor),
        bottomBar = {

            bottomButtonsBar(


                onSaveApplication =
                    {
                        viewModel.onSaveClicked(companyField, positionField, selectedStatus, jobLinkField, companyLinkField, selectedMillis!!)
                        onDismiss();
                    },

                onCancelApplication =
                    {
                        onDismiss();
                    }

            )

        }




    )
    {

        paddingValues ->

                Column (

                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp)
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState()),

                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                )
                {


                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(90.dp)
                            .border(
                                BorderStroke(2.dp, CText), // BorderStroke è utile qui
                                shape = RoundedCornerShape(90.dp) // Stessa shape
                            )
                            .background(CBackgroundColor, RoundedCornerShape(90.dp))

                    )
                    {



                        if(!companyLinkField.replace(" ", "").isEmpty())
                            domain = companyLinkField
                        else
                            domain = jobLinkField

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)

                                .data("https://www.google.com/s2/favicons?sz=256&domain=" + domain)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Company logo" ,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .fillMaxSize()
                                .clip(RoundedCornerShape(90.dp))
                        )



                    }



                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = companyField,
                        onValueChange = { companyField = it },
                        label = { Text("Company name") }

                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),

                        value = positionField,
                        onValueChange = { positionField = it },
                        label = { Text("Position title") }

                        )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = jobLinkField,
                        onValueChange = { jobLinkField = it },
                        label = { Text("Job listing link (optional)") }

                    )

                    OutlinedTextField(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = companyLinkField,
                        onValueChange = { companyLinkField = it },
                        label = { Text("Company website (use it to override icon fetched from the job listing link)") }

                    )

                    dropDownField("Initial Status:", modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), onDialogSubmit = {statusViewModel.onDialogSubmit(it)})

                    DatePickerField(label = "When you applied?",modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp))



                    OutlinedTextField(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = companyLocation,
                        onValueChange = { companyLocation = it },
                        label = { Text("Application work location (or company location)") },
                        trailingIcon = {
                            Image(
                                painter = painterResource(id = com.example.jobapp.R.drawable.searchlocation),
                                contentDescription = "search location",
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(CPrimary),
                                // **Il modificatore .clickable deve essere applicato direttamente all'Icona**
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable { viewModel.searchLocation(context, companyLocation) }
                            )
                        }

                    )


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(BorderStroke(1.dp, CText), RoundedCornerShape(12.dp))
                    )
                    {
                        AndroidView(
                            modifier = Modifier.fillMaxSize(),
                            factory = { ctx ->
                                MapView(ctx).apply {
                                    setTileSource(TileSourceFactory.MAPNIK)
                                    isTilesScaledToDpi = true
                                    setMultiTouchControls(false)
                                    tilesScaleFactor = 1.8f
                                    layoutParams = ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                    )
                                }
                            },
                            update = { view ->
                                // camera update
                                view.controller.setCenter(mapCenter)
                                view.controller.setZoom((mapZoom + 5))

                                // Gestione Marker
                                view.overlays.clear()
                                markerPoint?.let { point ->
                                    val marker = Marker(view)
                                    marker.position = point
                                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                                    val iconDrawable = ContextCompat.getDrawable(view.context, com.example.jobapp.R.drawable.work)

                                    iconDrawable?.setTint(android.graphics.Color.RED)

                                    marker.icon = iconDrawable


                                    marker.title = markerPointOverlay
                                    view.overlays.add(marker)
                                }
                                view.invalidate()
                            }
                        )
                    }


                    //   DropdownMenu() { }  change in no answer







                }

    }


}

@Composable
fun DatePickerField(
    label: String,
    modifier: Modifier = Modifier
) {
    // 1. Stato per la gestione del Dialog
    var showDatePicker by remember { mutableStateOf(false) }

    // 2. Stato per memorizzare e formattare la data selezionata
    var selectedDate by remember { mutableStateOf("") }

    // La data formattata da mostrare nel TextField
    val displayDate = selectedDate.ifEmpty { convertDateisToString(Date()) }

    // 3. OutlinedTextField che funge da "rettangolo"
    OutlinedTextField(
        value = displayDate,
        onValueChange = { },
        label = { Text(label) },
        readOnly = true, // Impedisce la comparsa della tastiera
        modifier = modifier,
        trailingIcon = {
            Image(
               painter = painterResource(id = com.example.jobapp.R.drawable.calendarico),
                contentDescription = "Seleziona data",
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(CPrimary),
                // **Il modificatore .clickable deve essere applicato direttamente all'Icona**
                modifier = Modifier
                    .size(40.dp)
                    .clickable { showDatePicker = true }
            )
        },
    )

    // 5. Logica del Dialog Calendario
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        // Controlla se una data è stata selezionata per abilitare il tasto OK
        val confirmEnabled by remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }

        DatePickerDialog(
            onDismissRequest = {
                // Chiude il dialog quando si clicca fuori o con tasto Indietro
                showDatePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        // Formatta la data selezionata in una stringa leggibile
                         selectedMillis = datePickerState.selectedDateMillis
                        if (selectedMillis != null) {
                            selectedMillis?.let{selectedDate = convertMillisToDate(it)}
                        }
                    },
                    enabled = confirmEnabled // Abilita solo se una data è selezionata
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            // Qui inseriamo il DatePicker vero e proprio
            DatePicker(state = datePickerState)
        }
    }
}


@Composable
fun dropDownField(

    label: String,
    modifier: Modifier = Modifier,
    onDialogSubmit: (value : String) -> Unit

)
{


    var isStatusMenuExpanded by remember { mutableStateOf(false) } // Stato per gestire l'apertura/chiusura del menu
    var showAddStatusDialog by remember { mutableStateOf(false) }



    Box {
        // Questo OutlinedTextField mostra la selezione e apre il menu al click
        OutlinedTextField(
            value = selectedStatus,
            onValueChange = { /* Non facciamo nulla qui, è di sola lettura */ },
            label = { Text(label) },
            readOnly = true, // Impedisce la modifica tramite tastiera
            modifier = modifier,
            trailingIcon = {
                Image(
                    painter = painterResource(id = com.example.jobapp.R.drawable.droparrow),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(CPrimary),
                    contentDescription = "Open status menu",
                    modifier = Modifier.clickable { isStatusMenuExpanded = true }

                )
            },

        )

        // Questo è il menu a tendina vero e proprio
        DropdownMenu(
            expanded = isStatusMenuExpanded,
            onDismissRequest = { isStatusMenuExpanded = false } // Chiude il menu se si clicca fuori
        ) {
            for(statusItem in statusOptions)
            {
                DropdownMenuItem(
                    text = {Text(statusItem.status)},
                    modifier = Modifier
                        .background(CBackgroundColor),
                  //      .border(1.dp, CText),
                    onClick = {
                        selectedStatus = statusItem.status
                        isStatusMenuExpanded = false
                        if(selectedStatus == statusOptions.get(statusOptions.size-1).status)
                        {
                            showAddStatusDialog = true;
                        }
                    }
                )
            }
        }
    }

    if(showAddStatusDialog)
    {
        AddStatusDialog(
            onDismiss = {
                selectedStatus = statusOptions[0].status
                showAddStatusDialog = false},
            onSubmit = {
                onDialogSubmit(it)
                showAddStatusDialog = false
            }

        )
    }

}



fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun convertDateisToString(date: Date): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(date)
}



@Composable
fun bottomButtonsBar( onCancelApplication: () -> Unit ,onSaveApplication: () -> Unit)
{

    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFDD3A44), Color(0xFFFF8A6F)), // Esempio di colori del gradiente
        // Puoi anche definire start e end offset per controllare la direzione del gradiente
        // Il gradiente parte da (0, 0) - angolo in alto a sinistra
        start = Offset(0f, 0f),
        // Il gradiente finisce a circa 1/3 della card, concentrandolo in quell'area
        end = Offset(500f, 500f) // Questi valori in pixel densi (dp) sono approssimativi.
    )

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .windowInsetsPadding(WindowInsets.navigationBars),

        contentAlignment = Alignment.Center
    )
    {

        HorizontalDivider(
            color = CText,
            thickness = 2.dp,
            modifier = Modifier
               .fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()

        )
        {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(6.dp)
                    .size(56.dp)
                    .border(
                        BorderStroke(2.dp, CText),
                        shape = RoundedCornerShape(90.dp) // Stessa shape
                    )
                    .background(CBackgroundColor, RoundedCornerShape(90.dp)),
            )
            {
                    Text(
                        text = "X",
                        color = CText,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .clickable{onCancelApplication()}
                    )
            }


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    // .size(56.dp)
                    .padding(horizontal = 6.dp)
                    .height(56.dp)
                    .width(100.dp)
                    .border(
                        BorderStroke(2.dp, CText),
                        shape = RoundedCornerShape(90.dp) // Stessa shape
                    )
                    .background(gradientBrush, RoundedCornerShape(90.dp)),
            )
            {


                Image(
                    painter = painterResource(id = com.example.jobapp.R.drawable.floppy),
                    contentDescription = "Save",
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(CBackgroundColor),
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onSaveApplication() }
                )
            }


        }


    }

}






@Preview
@Composable
fun CreationPreview()
{
    ApplicationCreationScreen({})
}
