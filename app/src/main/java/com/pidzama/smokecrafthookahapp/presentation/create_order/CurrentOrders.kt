package com.pidzama.smokecrafthookahapp.presentation.create_order

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.presentation.common.TopBarContent
import com.pidzama.smokecrafthookahapp.ui.theme.dimens

val listOrders = listOf(1, 2, 3, 4, 5)

@Composable
fun CurrentOrders(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            TopBarContent(
                navController = navController,
                canNavigateBack = false,
                title = R.string.current_orders
            )
        },
        floatingActionButton = {
            ButtonAddNewOrder(
                onClick = { navController.navigate(MainScreen.RecipeGenerationMethod.route) },
                modifier = Modifier.padding(bottom = 60.dp)
            )
        },
        modifier = modifier
    ) { innerPadding ->
        ListOrders(
            listOrders = listOrders,
            contentPadding = innerPadding
        )
    }
}


@Composable
fun ListOrders(
    listOrders: List<Int>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = contentPadding
    ) {
        items(listOrders) {
            CardItemOrder(modifier = Modifier.padding(12.dp))
        }
    }
}

@Composable
fun CardItemOrder(
    modifier: Modifier = Modifier
) {
    Card(
        border = BorderStroke(
            width = MaterialTheme.dimens.dp_2,
            MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(
                topStart = MaterialTheme.dimens.dp_10,
                topEnd = MaterialTheme.dimens.dp_10,
            ),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        ) {
            Text(
                text = "Заказ №1",
                fontWeight = FontWeight.W600,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(
                        start = MaterialTheme.dimens.dp_10,
                        top = 5.dp,
                        bottom = 5.dp
                    )
                    .fillMaxWidth()
            )
        }
        Card(
            shape = RoundedCornerShape(
                bottomStart = MaterialTheme.dimens.dp_10,
                bottomEnd = MaterialTheme.dimens.dp_10,
            ),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            DescriptionCardOrder(
                number = 2,
                infoTitle = "Количество кальянов: ",
                modifier = Modifier.fillMaxWidth()
            )
            DescriptionCardOrder(
                number = 1,
                infoTitle = "Номер стола: ",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun DescriptionCardOrder(
    number: Int,
    infoTitle: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = MaterialTheme.dimens.dp_2)
    ) {
        Image(
            painter = painterResource(id = R.drawable.double_arrow),
            contentDescription = null,
            modifier = Modifier.padding(start = MaterialTheme.dimens.dp_10)
        )
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.W600
                    )
                ) {
                    append(infoTitle)
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.W600
                    )
                ) {
                    append("$number")
                }
            },
            modifier = Modifier.padding(MaterialTheme.dimens.dp_2)
        )
    }
}

@Composable
fun ButtonAddNewOrder(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_plus),
            contentDescription = stringResource(
                id = R.string.create_new_order
            ),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
            modifier = Modifier.size(26.dp)
        )

    }
}
