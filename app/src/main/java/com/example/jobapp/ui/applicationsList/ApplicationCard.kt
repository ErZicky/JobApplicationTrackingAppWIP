package com.example.jobapp.ui.applicationsList

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReusableComposition
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.stylusHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.volley.toolbox.ImageRequest
import com.example.jobapp.R
import com.example.jobapp.data.model.Application
import com.example.jobapp.ui.theme.CAccent
import com.example.jobapp.ui.theme.CBackgroundColor
import com.example.jobapp.ui.theme.CError
import com.example.jobapp.ui.theme.CPrimary
import com.example.jobapp.ui.theme.CText
import com.example.jobapp.ui.theme.blackCard
import com.example.jobapp.ui.theme.grainGray
import com.example.jobapp.ui.theme.orangeCard
import com.example.jobapp.ui.theme.purpleCard
import com.example.jobapp.ui.theme.whiteCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.core.net.toUri

@Composable
fun ApplicationCard(app: Application,onClick: (Application) -> Unit)
{

    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    val domain = app.companyWebsite ?: app.link


    val context = LocalContext.current

    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFF302f2c), Color(0xFF3C3C3C)), // Esempio di colori del gradiente
        // Puoi anche definire start e end offset per controllare la direzione del gradiente
        // Il gradiente parte da (0, 0) - angolo in alto a sinistra
        start = Offset(0f, 0f),
        // Il gradiente finisce a circa 1/3 della card, concentrandolo in quell'area
        end = Offset(800f, 800f) // Questi valori in pixel densi (dp) sono approssimativi.
        )


     Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(app) },
         colors = CardDefaults.cardColors( containerColor = CText),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
         shape = MaterialTheme.shapes.medium
    ) {
         Box(
             modifier = Modifier
                 .fillMaxWidth()
                 .height(200.dp)
                 .background(gradientBrush)

         )
         {

             val onClickLink: () -> Unit = {

                 var link = app.link

                 if( !(link.startsWith("http://") || link.startsWith("https://")))
                 {
                     link = "https://" + link
                 }
                 val intent = Intent(Intent.ACTION_VIEW, link.toUri());
                 context.startActivity(intent);
             }

             Image(
                 painter = painterResource(id = R.drawable.angulararrow),
                 contentDescription = null,
                 contentScale = ContentScale.Crop,
                 colorFilter = ColorFilter.tint(CBackgroundColor),
                 modifier = Modifier
                     .align (Alignment.TopEnd)
                     .offset(-10.dp, 0.dp)
                     .size(50.dp)
                     .clickable(onClick = onClickLink)

             )

             AsyncImage(
                 model = coil.request.ImageRequest.Builder(LocalContext.current)

                     .data("https://www.google.com/s2/favicons?sz=256&domain=" + domain)
                     .crossfade(true)
                     .build(),
                 contentDescription = "Company logo" ,
                 contentScale = ContentScale.FillBounds,
                 modifier = Modifier
                     .align(Alignment.CenterEnd)
                     .offset(25.dp, 50.dp)
                     .rotate(-32f)
                     .size(150.dp)
                     .alpha(.5f)
             )

             Column(
                 modifier = Modifier
                     .fillMaxHeight()
                     .padding(16.dp), // Aggiungiamo il padding qui
                 verticalArrangement = Arrangement.SpaceBetween
             ) {

                 // Parte Superiore: Ruolo e Azienda
                 Column {
                     Text(
                         text = app.role,
                         style = MaterialTheme.typography.titleLarge,
                         fontWeight = FontWeight.Bold,
                         color = CBackgroundColor
                     )
                     Text(
                         text = app.company,
                         style = MaterialTheme.typography.titleMedium,
                         fontWeight = FontWeight.Medium,
                         color = CBackgroundColor
                     )
                     Text(
                         text = "Date: ${dateFormat.format(app.date)}",
                         style = MaterialTheme.typography.bodySmall,
                         fontWeight = FontWeight.Light,
                         color = CBackgroundColor
                     )
                 }

                 // Parte Inferiore: Status
                 Text(
                     text = "Status: ${app.status}",
                     style = MaterialTheme.typography.bodyMedium,
                     fontWeight = FontWeight.Medium,
                     color = CError
                 )

             }
         }
    }
}


@Preview
@Composable
private fun ApplicationCardPreview() {

    ApplicationCard(
        Application(1, "Meta", "Android Developer", "rifiutato", 0L, "google.com", null),
        onClick = {}
    )

}





