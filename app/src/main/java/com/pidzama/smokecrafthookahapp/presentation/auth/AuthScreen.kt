package com.pidzama.smokecrafthookahapp.presentation.auth

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.presentation.auth.common.UiEvents
import com.pidzama.smokecrafthookahapp.ui.theme.dimens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthScreens(
    navController: NavHostController,
) {
    val keyboardController = LocalSoftwareKeyboardController.current!!
    val viewModel = hiltViewModel<AuthViewModel>()
    val snackBarHostState = remember { SnackbarHostState() }


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
                    .clickable { keyboardController.hide() },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LogoSection()
                AuthFieldSection(
                    keyboardController,
                    snackBarHostState,
                    viewModel,
                    navController
                )
                Image(
                    painter = painterResource(id = R.drawable.logo_wave),
                    contentDescription = "logo",
                    alignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .weight(2f, false)
                        .fillMaxSize()
                )
            }

        }
    )
}

@Composable
fun LogoSection() {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.padding(top = (screenHeight / 6).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_smokecraft),
                contentDescription = "logo",
                colorFilter = ColorFilter.tint(color = Color.White),
            )
            Divider(
                modifier = Modifier
                    .padding(top = MaterialTheme.dimens.small1)
                    .width(MaterialTheme.dimens.small1 * 14)
                    .height((screenHeight / 200).dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthFieldSection(
    keyboardController: SoftwareKeyboardController,
    snackBar: SnackbarHostState,
    viewModel: AuthViewModel,
    navController: NavHostController
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Log.d("MyLog", "screenHeight -> $screenHeight")
    Log.d("MyLog", "screenHeight -> $screenHeight")
    val loginState = viewModel.loginState.value
    val passwordState = viewModel.passwordState.value
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = (screenWidth / 8).dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Вход в систему",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = (screenHeight / 10).dp, bottom = (screenHeight / 24).dp)
        )
        Log.d("MyLog", "TExt вход в системы падинг -> ${(screenHeight / 10).dp}")
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            value = loginState.text,
            onValueChange = {
                viewModel.setLogin(it)
            },
            isError = loginState.error != null,
            placeholder = {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            },
            colors = TextFieldDefaults.colors(disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_login),
                    contentDescription = "ic_login",
                    tint = MaterialTheme.colorScheme.inverseSurface
                )
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.large2)
        )
        if (loginState.error != "") {
            Text(
                text = loginState.error ?: "",
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.extraSmall))
        OutlinedTextField(
            value = passwordState.text,
            onValueChange = {
                viewModel.setPassword(it)
            },
            placeholder = {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.large2),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.colors(disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer),
            keyboardActions = KeyboardActions(onNext = {
                viewModel.loginUser()
            }),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_password),
                    contentDescription = "ic_password",
                    tint = MaterialTheme.colorScheme.inverseSurface
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = passwordState.error != null,
            trailingIcon = {
                val image = if (passwordVisible)
                    painterResource(id = R.drawable.ic_visibility)
                else painterResource(id = R.drawable.ic_visibility_off)
                val description =
                    if (passwordVisible) "Hide password" else "Show password"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = image, description)
                }
            },
        )
        if (passwordState.error != "") {
            Text(
                text = passwordState.error ?: "",
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Red
            )
        }
        Log.d("MyLog", "Button heiht -> ${(screenHeight / 13).dp}")
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.large2 + MaterialTheme.dimens.small2)
                .padding(top = (screenHeight / 42).dp),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                scope.launch {
                    viewModel.eventFlow.collectLatest { event ->
                        Log.d("MyLog", "$event")
                        when (event) {
                            is UiEvents.SnackBarEvent -> {
                                snackBar.showSnackbar(
                                    message = event.message,
                                    actionLabel = "OK",
                                    duration = SnackbarDuration.Short
                                )
                            }
                            is UiEvents.NavigateEvent -> {
                                navController.navigate(event.route)
                            }
                        }
                    }
                }
                keyboardController.hide()
                navController.popBackStack()
                viewModel.loginUser()
            },
        ) {
            Text(
                text = "Войти",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        }
    }
}


@Preview
@Composable
fun PreviewAuthScreen() {
    AuthScreens(navController = rememberNavController())
}