package com.reishandy.chatroom.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Pin
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.reishandy.chatroom.R
import com.reishandy.chatroom.ui.theme.ChatroomTheme
import kotlinx.coroutines.delay


@Composable
fun Login(
    modifier: Modifier = Modifier,
    emailValue: String,
    onEmailValueChange: (String) -> Unit,
    isEmailError: Boolean,
    passwordValue: String,
    onPasswordValueChange: (String) -> Unit,
    isPasswordError: Boolean,
    onLoginClick: () -> Unit,
    onChangeToRegisterClick: () -> Unit
) {
    AuthenticationWrapper(
        modifier = modifier,
        title = R.string.login,
        textFields = {
            CorrectOutlinedTextFields(
                value = emailValue,
                onValueChange = onEmailValueChange,
                label = R.string.email,
                icon = Icons.Outlined.AlternateEmail,
                iconDescription = R.string.email,
                isError = isEmailError,
                imeAction = ImeAction.Next
            )

            CorrectOutlinedTextFields(
                value = passwordValue,
                onValueChange = onPasswordValueChange,
                label = R.string.password,
                icon = Icons.Outlined.Key,
                iconDescription = R.string.password,
                isError = isPasswordError,
                imeAction = ImeAction.Done,
                isPassword = true
            )
        },
        buttons = {
            ButtonComplex(
                primaryText = R.string.send,
                onPrimaryClick = onLoginClick,
                secondaryText = R.string.have_account,
                onSecondaryClick = onChangeToRegisterClick
            )
        }
    )
}


@Composable
fun Register(
    modifier: Modifier = Modifier,
    usernameValue: String,
    onUsernameValueChange: (String) -> Unit,
    emailValue: String,
    onEmailValueChange: (String) -> Unit,
    isEmailError: Boolean,
    passwordValue: String,
    onPasswordValueChange: (String) -> Unit,
    isPasswordError: Boolean,
    confirmPasswordValue: String,
    onConfirmPasswordValueChange: (String) -> Unit,
    isConfirmPasswordError: Boolean,
    onRegisterClick: () -> Unit,
    onChangeToLoginClick: () -> Unit
) {
    AuthenticationWrapper(
        modifier = modifier,
        title = R.string.register,
        textFields = {
            CorrectOutlinedTextFields(
                value = usernameValue,
                onValueChange = onUsernameValueChange,
                label = R.string.username,
                icon = Icons.Outlined.AccountCircle,
                iconDescription = R.string.username,
                imeAction = ImeAction.Next
            )

            CorrectOutlinedTextFields(
                value = emailValue,
                onValueChange = onEmailValueChange,
                label = R.string.email,
                icon = Icons.Outlined.AlternateEmail,
                iconDescription = R.string.email,
                isError = isEmailError,
                imeAction = ImeAction.Next
            )

            CorrectOutlinedTextFields(
                value = passwordValue,
                onValueChange = onPasswordValueChange,
                label = R.string.password,
                icon = Icons.Outlined.Key,
                iconDescription = R.string.password,
                isError = isPasswordError,
                imeAction = ImeAction.Next,
                isPassword = true
            )

            CorrectOutlinedTextFields(
                value = confirmPasswordValue,
                onValueChange = onConfirmPasswordValueChange,
                label = R.string.confirm_password,
                icon = Icons.Outlined.Key,
                iconDescription = R.string.confirm_password,
                isError = isConfirmPasswordError,
                imeAction = ImeAction.Done,
                isPassword = true
            )
        },
        buttons = {
            ButtonComplex(
                primaryText = R.string.register,
                onPrimaryClick = onRegisterClick,
                secondaryText = R.string.have_account,
                onSecondaryClick = onChangeToLoginClick
            )
        }
    )
}


@Composable
fun Verify(
    modifier: Modifier = Modifier,
    emailSentTo: String,
    verificationCodeValue: String,
    onVerificationCodeValueChange: (String) -> Unit,
    onVerifyClick: () -> Unit,
    onResendClick: () -> Unit
) {
    var countdown by remember { mutableIntStateOf(60) }
    var isResendEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(countdown) {
        if (countdown > 0) {
            delay(1000)
            countdown--
        } else {
            isResendEnabled = true
        }
    }

    AuthenticationWrapper(
        modifier = modifier,
        title = R.string.verify,
        textFields = {
            Text(
                text = stringResource(R.string.sent_to, emailSentTo),
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )

            CorrectOutlinedTextFields(
                value = verificationCodeValue,
                onValueChange = onVerificationCodeValueChange,
                label = R.string.verification_code,
                icon = Icons.Outlined.Pin,
                iconDescription = R.string.verification_code,
                isError = false,
                imeAction = ImeAction.Done
            )
        },
        buttons = {
            ButtonComplex(
                primaryText = R.string.verify,
                onPrimaryClick = onVerifyClick,
                secondaryText = if (countdown > 0)
                    R.string.resend
                else
                    R.string.resend_now,
                onSecondaryClick = {
                    if (isResendEnabled) {
                        onResendClick()
                        countdown = 60
                        isResendEnabled = false
                    }

                    onResendClick()
                },
                isSecondaryEnabled = isResendEnabled,
                countdown = countdown
            )
        }
    )
}


@Composable
fun AuthenticationWrapper(
    modifier: Modifier = Modifier,
    title: Int,
    textFields: @Composable (() -> Unit),
    buttons: @Composable (() -> Unit)
) {
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
            elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(title),
                    style = MaterialTheme.typography.headlineMedium
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium)),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )

                textFields()

                buttons()
            }
        }
    }
}


@Composable
fun ButtonComplex(
    modifier: Modifier = Modifier,
    primaryText: Int,
    onPrimaryClick: () -> Unit,
    secondaryText: Int,
    onSecondaryClick: () -> Unit,
    isSecondaryEnabled: Boolean = true,
    countdown: Int = 0
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onPrimaryClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
            Text(stringResource(primaryText))
        }

        TextButton(
            onClick = onSecondaryClick,
            enabled = isSecondaryEnabled
        ) {
            Text(
                text = if (isSecondaryEnabled)
                    stringResource(secondaryText)
                else
                    stringResource(secondaryText, countdown)
            )
        }
    }
}


@Composable
fun CorrectOutlinedTextFields(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: Int,
    icon: ImageVector,
    iconDescription: Int,
    isError: Boolean = false,
    imeAction: ImeAction,
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(R.dimen.padding_small)),
        label = {
            Text(
                text = stringResource(label),
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(iconDescription),
            )
        },
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Default.VisibilityOff
                        else
                            Icons.Default.Visibility,
                        contentDescription = if (passwordVisible)
                            "Hide password"
                        else
                            "Show password"
                    )
                }
            }
        } else null,
        visualTransformation = if (isPassword && !passwordVisible)
            PasswordVisualTransformation()
        else
            VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
        ),
        singleLine = true,
        isError = isError
    )
}


@Preview
@Composable
fun AuthenticationMainPreview() {
    ChatroomTheme(darkTheme = true) {
        LazyColumn {
            item {
                Login(
                    emailValue = "",
                    onEmailValueChange = {},
                    isEmailError = false,
                    passwordValue = "",
                    onPasswordValueChange = {},
                    isPasswordError = false,
                    onLoginClick = {},
                    onChangeToRegisterClick = {},
                )
            }

            item {
                Register(
                    usernameValue = "",
                    onUsernameValueChange = {},
                    emailValue = "",
                    onEmailValueChange = {},
                    isEmailError = false,
                    passwordValue = "",
                    onPasswordValueChange = {},
                    isPasswordError = false,
                    confirmPasswordValue = "",
                    onConfirmPasswordValueChange = {},
                    isConfirmPasswordError = false,
                    onRegisterClick = {},
                    onChangeToLoginClick = {}
                )
            }

            item {
                Verify(
                    emailSentTo = "example@example.com",
                    verificationCodeValue = "",
                    onVerificationCodeValueChange = {},
                    onVerifyClick = {},
                    onResendClick = {}
                )
            }
        }
    }
}