package com.pidzama.smokecrafthookahapp.presentation.current_order

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.data.model.orders.OrdersItem
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.presentation.common.TopBarContent
import com.pidzama.smokecrafthookahapp.ui.theme.dimens


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CurrentOrders(
    navController: NavHostController,
    viewModel: CurrentOrderViewModel,
    modifier: Modifier = Modifier
) {

    val state by viewModel.stateOrdersState.collectAsState()

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
        OrderScreenState(
            state = state,
            contentPadding = innerPadding
        )
    }
}

@Composable
fun OrderScreenState(
    state: OrdersState,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {

    val screenModifier = Modifier
        .fillMaxSize()
        .then(modifier)

    when (state) {
        is OrdersState.Content -> {
            OrdersScreenContent(
                state = state,
                contentPadding = contentPadding,
                modifier = screenModifier
            )
        }

        is OrdersState.Empty -> {
            OrderScreenEmpty(state = state, modifier = screenModifier)
        }

        is OrdersState.Error -> {
            OrderScreenError(state = state, modifier = screenModifier)
        }

        is OrdersState.Loading -> {
            OrderScreenLoading(state = state, modifier = screenModifier)
        }
    }

}


@Composable
fun OrdersScreenContent(
    state: OrdersState.Content,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = contentPadding
    ) {
        items(state.data, key = { order -> order.id }) { order ->
            CardItemOrder(order = order, modifier = Modifier.padding(12.dp))
        }
    }
}

@Composable
fun OrderScreenEmpty(
    state: OrdersState.Empty,
    modifier: Modifier = Modifier
) {
    Text(text = state.message, modifier = Modifier.then(modifier))
}

@Composable
fun OrderScreenError(
    state: OrdersState.Error,
    modifier: Modifier = Modifier
) {
    Text(text = state.error, modifier = Modifier.then(modifier))
}

@Composable
fun OrderScreenLoading(
    state: OrdersState.Loading,
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.then(modifier)) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(48.dp)
                .then(modifier),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
fun CardItemOrder(
    order: OrdersItem,
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)) {
                        append(stringResource(id = R.string.order))
                    }
                    withStyle(SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)) {
                        append(" №${order.order_number}")
                    }
                },
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(start = 16.dp, top = 5.dp, bottom = 5.dp)
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
                number = order.hookah_count,
                infoTitle = "Количество кальянов: ",
                modifier = Modifier.fillMaxWidth()
            )
            DescriptionCardOrder(
                number = order.table_number,
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