package com.pidzama.smokecrafthookahapp.presentation.auth

import android.content.res.Configuration
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.presentation.auth.common.UiEvents
import com.pidzama.smokecrafthookahapp.ui.theme.ScreenOrientation
import com.pidzama.smokecrafthookahapp.ui.theme.dimens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthScreens(
    navController: NavHostController,
) {
    val keyboardController = LocalSoftwareKeyboardController.current!!
    val snackBarHostState = remember { SnackbarHostState() }
    val viewModel = hiltViewModel<AuthViewModel>()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        content = {
            if (ScreenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                PortraitAuthScreen(
                    navController = navController,
                    keyboardController = keyboardController,
                    snackBar = snackBarHostState,
                    viewModel = viewModel
                )
            } else {
                LandscapeAuthScreen(
                    navController = navController,
                    keyboardController = keyboardController,
                    snackBar = snackBarHostState,
                    viewModel = viewModel
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
fun PortraitAuthFieldSection(
    keyboardController: SoftwareKeyboardController,
    snackBar: SnackbarHostState,
    viewModel: AuthViewModel,
    navController: NavHostController,
    screenHeight: Int = LocalConfiguration.current.screenHeightDp,
    screenWidth: Int = LocalConfiguration.current.screenWidthDp,
    localFocusManager: FocusManager = LocalFocusManager.current,
    scope: CoroutineScope = rememberCoroutineScope()
) {

    val loginState = viewModel.loginState.value
    val passwordState = viewModel.passwordState.value
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = (screenWidth / 8).dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Вход в систему",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = (screenHeight / 10).dp, bottom = (screenHeight / 24).dp)
        )
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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_login),
                    contentDescription = "ic_login",
                    tint = MaterialTheme.colorScheme.inverseSurface
                )
            },
            shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.buttonHeight)
                .width(MaterialTheme.dimens.buttonWidth)
        )
        if (loginState.error != "") {
            Text(
                text = loginState.error ?: "",
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error
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
            shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.buttonHeight)
                .width(MaterialTheme.dimens.buttonWidth),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer),
            keyboardActions = KeyboardActions(onNext = {
                viewModel.loginUser()
                localFocusManager.clearFocus()
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
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.large3)
                .width(MaterialTheme.dimens.buttonWidth)
                .padding(top = (screenHeight / 42).dp),
            shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
            onClick = {
                scope.launch {
                    viewModel.eventFlow.collectLatest { event ->
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
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PortraitAuthScreen(
    navController: NavHostController,
    keyboardController: SoftwareKeyboardController,
    snackBar: SnackbarHostState,
    viewModel: AuthViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .clickable { keyboardController.hide() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LogoSection()
        PortraitAuthFieldSection(
            navController = navController,
            keyboardController = keyboardController,
            snackBar = snackBar,
            viewModel = viewModel
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LandscapeAuthScreen(
    navController: NavHostController,
    keyboardController: SoftwareKeyboardController,
    snackBar: SnackbarHostState,
    viewModel: AuthViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { keyboardController.hide() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoSection()
        LandscapeAuthFieldSection(
            navController = navController,
            keyboardController = keyboardController,
            snackBar = snackBar,
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LandscapeAuthFieldSection(
    navController: NavHostController,
    keyboardController: SoftwareKeyboardController,
    snackBar: SnackbarHostState,
    viewModel: AuthViewModel,
    screenHeight: Int = LocalConfiguration.current.screenHeightDp,
    screenWidth: Int = LocalConfiguration.current.screenWidthDp,
    localFocusManager: FocusManager = LocalFocusManager.current,
    scope: CoroutineScope = rememberCoroutineScope()
) {

    val loginState = viewModel.loginState.value
    val passwordState = viewModel.passwordState.value
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .clickable { keyboardController.hide() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = (screenWidth / 4).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Вход в систему",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = (screenHeight / 10).dp, bottom = (screenHeight / 24).dp)
            )
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
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                maxLines = 1,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_login),
                        contentDescription = "ic_login",
                        tint = MaterialTheme.colorScheme.inverseSurface
                    )
                },
                shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.buttonHeight)
            )
            if (loginState.error != "") {
                Text(
                    text = loginState.error ?: "",
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
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
                shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.buttonHeight),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                maxLines = 1,
                colors = TextFieldDefaults.colors(disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer),
                keyboardActions = KeyboardActions(onNext = {
                    viewModel.loginUser()
                    localFocusManager.clearFocus()
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
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.buttonHeight)
                    .padding(top = (screenHeight / 42).dp),
                shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                onClick = {
                    scope.launch {
                        viewModel.eventFlow.collectLatest { event ->
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
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.large1 / 2))
        }
    }
}