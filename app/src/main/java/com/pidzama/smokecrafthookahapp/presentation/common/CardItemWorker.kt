package com.pidzama.smokecrafthookahapp.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pidzama.smokecrafthookahapp.data.model.User
import com.pidzama.smokecrafthookahapp.data.model.Worker
import com.pidzama.smokecrafthookahapp.data.model.WorkerItem

@Composable
fun CardItemWorker(
    worker: WorkerItem,
//  navController: NavHostController
) {
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .size(width = 300.dp, height = 80.dp)
            .padding(4.dp)
            .fillMaxSize(),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                worker.user.first_name,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                color = Color.Black
            )
            Text(
                worker.user.user_role,
                color = Color.Black
            )
            Text(
                worker.user.email,
                color = Color.Black
            )
        }

    }
}

@Preview
@Composable
fun PreviewCard() {
    CardItemWorker(
        worker = WorkerItem(
            created = "11112",
            updated = "22434",
            is_active = true,
            id = 1,
            user = User(
                username = "Andrey",
                email = "email@e.com",
                first_name = "First",
                last_name = "last",
                user_role = "Role",
                password = "1234"
            ),
            organization = 1
        )
    )
}