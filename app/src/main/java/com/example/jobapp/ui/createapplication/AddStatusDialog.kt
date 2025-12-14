package com.example.jobapp.ui.createapplication

import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.jobapp.ui.theme.CBackgroundColor
import com.example.jobapp.ui.theme.CPrimary
import com.example.jobapp.ui.theme.CText

@Composable
fun AddStatusDialog(//callback per le azioni
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit)
{

    var userStatus by remember { mutableStateOf("") }
    val isInputValid = userStatus.isNotBlank()


    Dialog(onDismissRequest = onDismiss) {


        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.CardDefaults.cardColors(
                containerColor = CBackgroundColor
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    text = "Add new status",
                    color = CText,
                    fontSize = 20.sp)

                Spacer(modifier = Modifier.height(36.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = userStatus,
                    onValueChange = { userStatus = it },
                    label = { Text("Insert here new status") }

                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {

                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text(
                            text = "Cancel",
                            color = CText
                        )
                    }

                    TextButton(
                        onClick =  {onSubmit(userStatus)},
                        enabled = isInputValid
                    ) {
                        Text(
                            text = "Submit",
                            color = CPrimary,

                        )
                    }


                }




            }
        }

    }

}


@Preview
@Composable
fun previewDialog()
{
    AddStatusDialog({}, {});
}