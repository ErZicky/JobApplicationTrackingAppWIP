package com.example.jobapp.ui.navbar

import android.content.Intent
import android.view.RoundedCorner
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jobapp.R
import com.example.jobapp.data.model.Application
import com.example.jobapp.data.model.NavBarItem
import com.example.jobapp.ui.applicationsList.ApplicationCard
import com.example.jobapp.ui.createapplication.ApplicationCreationActivity
import com.example.jobapp.ui.createapplication.ApplicationCreationScreen
import java.util.Date
import com.example.jobapp.ui.theme.*

@Composable
fun NavBarScreen(viewModel: NavBarViewModel = viewModel()) {
    // fetch index status from viewmodel
    val selectedIndex by viewModel.selectedIndex.collectAsState()

    // Passiamo lo stato e la funzione di callback al composable di rendering
    NavBarContent(
        items = viewModel.items,
        selectedIndex = selectedIndex,
        onItemClicked = { index -> viewModel.onTabSelected(index) }
    )


}

@Composable
fun NavBarContent(items: List<NavBarItem>, selectedIndex : Int, onItemClicked: (Int) -> Unit)
{

    val context = LocalContext.current

    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFDD3A44), Color(0xFFFF8A6F)), // Esempio di colori del gradiente
        // Puoi anche definire start e end offset per controllare la direzione del gradiente
        // Il gradiente parte da (0, 0) - angolo in alto a sinistra
        start = Offset(0f, 0f),
        // Il gradiente finisce a circa 1/3 della card, concentrandolo in quell'area
        end = Offset(600f, 600f) // Questi valori in pixel densi (dp) sono approssimativi.
    )

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 18.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(CText, RoundedCornerShape(28.dp))
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                AnimatedTabIcon(
                    item = item,
                    isSelected = selectedIndex == index,
                    onClick = {onItemClicked(index)}
                )
            }
        }




        val plusClick : () -> Unit = {
            val intent = Intent(context, ApplicationCreationActivity::class.java);
           context.startActivity(intent)
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset(y = -50.dp)
                .align(Alignment.Center)
                .size(75.dp)
                .background(gradientBrush, RoundedCornerShape(90.dp))
                .clickable(onClick =  plusClick)

        )
        {
            Text(

                fontWeight = FontWeight.Normal,
                text = "+",
                color = CBackgroundColor,
                fontSize = 50.sp
            )
        }
    }


}

@Composable
private fun AnimatedTabIcon(item: NavBarItem, isSelected: Boolean, onClick: () -> Unit)
{
    // Le animazioni restano nella View (UI Logic)
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.5f),
        label = "color"
    )

    val scale by animateFloatAsState(
        targetValue = if(isSelected) 1.4f else 1.0f,
        animationSpec = spring( dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "scale"

    )

    Box(
        modifier = Modifier
            .size(50.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            tint = CPrimary,
            modifier = Modifier
                .size(24.dp)
                .scale(scale),
            contentDescription = null
        )
    }
}


@Preview
@Composable
private fun NavBarPreview(viewModel: NavBarViewModel = viewModel()) {

    NavBarContent(
        items = viewModel.items,
        selectedIndex = 0,
        onItemClicked = {}
    )

}