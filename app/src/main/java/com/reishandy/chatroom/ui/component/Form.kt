package com.reishandy.chatroom.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Pin
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import com.reishandy.chatroom.R
import com.reishandy.chatroom.ui.theme.ChatroomTheme
import kotlinx.coroutines.delay

@Composable
fun Password(
    modifier: Modifier = Modifier,
    oldPasswordValue: String,
    onOldPasswordValueChange: (String) -> Unit,
    @StringRes oldPasswordLabel: Int,
    isOldPasswordError: Boolean,
    newPasswordValue: String,
    onNewPasswordValueChange: (String) -> Unit,
    @StringRes newPasswordLabel: Int,
    isNewPasswordError: Boolean,
    confirmPasswordValue: String,
    onConfirmPasswordValueChange: (String) -> Unit,
    @StringRes confirmPasswordLabel: Int,
    isConfirmPasswordError: Boolean,
    onChangePasswordClick: () -> Unit,
    onCancelClick: () -> Unit,
    isLoading: Boolean,
    generalError: String?
) {
    Wrapper(
        modifier = modifier,
        title = R.string.password_title,
        textFields = {
            Text(
                text = stringResource(R.string.change_password),
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )

            if (generalError != null) {
                Text(
                    text = generalError,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            CorrectOutlinedTextFields(
                value = oldPasswordValue,
                onValueChange = onOldPasswordValueChange,
                label = oldPasswordLabel,
                icon = Icons.Outlined.Key,
                iconDescription = R.string.old_password,
                isError = isOldPasswordError,
                imeAction = ImeAction.Next,
                isPassword = true
            )

            CorrectOutlinedTextFields(
                value = newPasswordValue,
                onValueChange = onNewPasswordValueChange,
                label = newPasswordLabel,
                icon = Icons.Outlined.Key,
                iconDescription = R.string.new_password,
                isError = isNewPasswordError,
                imeAction = ImeAction.Next,
                isPassword = true
            )

            CorrectOutlinedTextFields(
                value = confirmPasswordValue,
                onValueChange = onConfirmPasswordValueChange,
                label = confirmPasswordLabel,
                icon = Icons.Outlined.Key,
                iconDescription = R.string.confirm_password,
                isError = isConfirmPasswordError,
                imeAction = ImeAction.Done,
                isPassword = true
            )
        },
        buttons = {
            ButtonComplex(
                primaryText = R.string.confirm,
                onPrimaryClick = onChangePasswordClick,
                secondaryText = R.string.cancel,
                onSecondaryClick = onCancelClick,
                isLoading = isLoading
            )
        }
    )
}

@Composable
fun Username(
    modifier: Modifier = Modifier,
    usernameValue: String,
    onUsernameValueChange: (String) -> Unit,
    @StringRes usernameLabel: Int,
    isUsernameError: Boolean,
    onChangeUsernameClick: () -> Unit,
    onCancelClick: () -> Unit,
    isLoading: Boolean,
    generalError: String?
) {
    Wrapper(
        modifier = modifier,
        title = R.string.username_title,
        textFields = {
            Text(
                text = stringResource(R.string.change_username),
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )

            if (generalError != null) {
                Text(
                    text = generalError,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            CorrectOutlinedTextFields(
                value = usernameValue,
                onValueChange = onUsernameValueChange,
                label = usernameLabel,
                icon = Icons.Outlined.AccountCircle,
                iconDescription = R.string.username,
                isError = isUsernameError,
                imeAction = ImeAction.Done
            )
        },
        buttons = {
            ButtonComplex(
                primaryText = R.string.confirm,
                onPrimaryClick = onChangeUsernameClick,
                secondaryText = R.string.cancel,
                onSecondaryClick = onCancelClick,
                isLoading = isLoading
            )
        }
    )
}

@Composable
fun Login(
    modifier: Modifier = Modifier,
    emailValue: String,
    onEmailValueChange: (String) -> Unit,
    @StringRes  emailLabel: Int,
    isEmailError: Boolean,
    passwordValue: String,
    onPasswordValueChange: (String) -> Unit,
    @StringRes passwordLabel: Int,
    isPasswordError: Boolean,
    onLoginClick: () -> Unit,
    onChangeToRegisterClick: () -> Unit,
    isLoading: Boolean,
    generalError: String?
) {
    Wrapper(
        modifier = modifier,
        title = R.string.login_title,
        textFields = {
            if (generalError != null) {
                Text(
                    text = generalError,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            CorrectOutlinedTextFields(
                value = emailValue,
                onValueChange = onEmailValueChange,
                label = emailLabel,
                icon = Icons.Outlined.AlternateEmail,
                iconDescription = R.string.email,
                isError = isEmailError,
                imeAction = ImeAction.Next
            )

            CorrectOutlinedTextFields(
                value = passwordValue,
                onValueChange = onPasswordValueChange,
                label = passwordLabel,
                icon = Icons.Outlined.Key,
                iconDescription = R.string.password_title,
                isError = isPasswordError,
                imeAction = ImeAction.Done,
                isPassword = true
            )
        },
        buttons = {
            ButtonComplex(
                primaryText = R.string.send,
                onPrimaryClick = onLoginClick,
                secondaryText = R.string.dont_have_account,
                onSecondaryClick = onChangeToRegisterClick,
                isLoading = isLoading
            )
        }
    )
}

@Composable
fun Register(
    modifier: Modifier = Modifier,
    usernameValue: String,
    onUsernameValueChange: (String) -> Unit,
    @StringRes usernameLabel: Int,
    isUsernameError: Boolean,
    emailValue: String,
    onEmailValueChange: (String) -> Unit,
    @StringRes emailLabel: Int,
    isEmailError: Boolean,
    passwordValue: String,
    onPasswordValueChange: (String) -> Unit,
    @StringRes passwordLabel: Int,
    isPasswordError: Boolean,
    confirmPasswordValue: String,
    onConfirmPasswordValueChange: (String) -> Unit,
    @StringRes confirmPasswordLabel: Int,
    isConfirmPasswordError: Boolean,
    onRegisterClick: () -> Unit,
    onChangeToLoginClick: () -> Unit,
    isLoading: Boolean,
    generalError: String?
) {
    Wrapper(
        modifier = modifier,
        title = R.string.register_title,
        textFields = {
            if (generalError != null) {
                Text(
                    text = generalError,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            CorrectOutlinedTextFields(
                value = usernameValue,
                onValueChange = onUsernameValueChange,
                label = usernameLabel,
                icon = Icons.Outlined.AccountCircle,
                iconDescription = R.string.username_title,
                isError = isUsernameError,
                imeAction = ImeAction.Next
            )

            CorrectOutlinedTextFields(
                value = emailValue,
                onValueChange = onEmailValueChange,
                label = emailLabel,
                icon = Icons.Outlined.AlternateEmail,
                iconDescription = R.string.email,
                isError = isEmailError,
                imeAction = ImeAction.Next
            )

            CorrectOutlinedTextFields(
                value = passwordValue,
                onValueChange = onPasswordValueChange,
                label = passwordLabel,
                icon = Icons.Outlined.Key,
                iconDescription = R.string.password_title,
                isError = isPasswordError,
                imeAction = ImeAction.Next,
                isPassword = true
            )

            CorrectOutlinedTextFields(
                value = confirmPasswordValue,
                onValueChange = onConfirmPasswordValueChange,
                label = confirmPasswordLabel,
                icon = Icons.Outlined.Key,
                iconDescription = R.string.confirm_password,
                isError = isConfirmPasswordError,
                imeAction = ImeAction.Done,
                isPassword = true
            )
        },
        buttons = {
            ButtonComplex(
                primaryText = R.string.send,
                onPrimaryClick = onRegisterClick,
                secondaryText = R.string.have_account,
                onSecondaryClick = onChangeToLoginClick,
                isLoading = isLoading
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
    @StringRes verificationCodeLabel: Int,
    isVerificationCodeError: Boolean,
    onVerifyClick: () -> Unit,
    onResendClick: () -> Unit,
    isLoading: Boolean,
    generalError: String?
) {
    var countdown: Int by remember { mutableIntStateOf(60) }
    var isResendEnabled: Boolean by remember { mutableStateOf(false) }

    LaunchedEffect(countdown) {
        if (countdown > 0) {
            delay(1000)
            countdown--
        } else {
            isResendEnabled = true
        }
    }

    Wrapper(
        modifier = modifier,
        title = R.string.verify_title,
        textFields = {
            Text(
                text = stringResource(R.string.sent_to, emailSentTo),
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )

            if (generalError != null) {
                Text(
                    text = generalError,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            CorrectOutlinedTextFields(
                value = verificationCodeValue,
                onValueChange = onVerificationCodeValueChange,
                label = verificationCodeLabel,
                icon = Icons.Outlined.Pin,
                iconDescription = R.string.verification_code,
                isError = isVerificationCodeError,
                imeAction = ImeAction.Done
            )
        },
        buttons = {
            ButtonComplex(
                primaryText = R.string.verify_title,
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
                },
                isLoading = isLoading,
                isSecondaryEnabled = isResendEnabled,
                countdown = countdown
            )
        }
    )
}

@Composable
fun Wrapper(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
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
    @StringRes primaryText: Int,
    onPrimaryClick: () -> Unit,
    @StringRes secondaryText: Int,
    onSecondaryClick: () -> Unit,
    isLoading: Boolean,
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
                .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(stringResource(primaryText))
            }
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
    @StringRes label: Int,
    icon: ImageVector,
    @StringRes iconDescription: Int,
    isError: Boolean = false,
    imeAction: ImeAction,
    isPassword: Boolean = false
) {
    var passwordVisible: Boolean by remember { mutableStateOf(false) }

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
                Password(
                    oldPasswordValue = "",
                    onOldPasswordValueChange = {},
                    oldPasswordLabel = R.string.old_password,
                    isOldPasswordError = false,
                    newPasswordValue = "",
                    onNewPasswordValueChange = {},
                    newPasswordLabel = R.string.new_password,
                    isNewPasswordError = false,
                    confirmPasswordValue = "",
                    onConfirmPasswordValueChange = {},
                    confirmPasswordLabel = R.string.confirm_password,
                    isConfirmPasswordError = false,
                    onChangePasswordClick = {},
                    onCancelClick = {},
                    isLoading = false,
                    generalError = null
                )
            }

            item {
                Username(
                    usernameValue = "",
                    onUsernameValueChange = {},
                    usernameLabel = R.string.username,
                    isUsernameError = false,
                    onChangeUsernameClick = {},
                    onCancelClick = {},
                    isLoading = false,
                    generalError = null
                )
            }

            item {
                Login(
                    emailValue = "",
                    onEmailValueChange = {},
                    emailLabel = R.string.email,
                    isEmailError = false,
                    passwordValue = "",
                    onPasswordValueChange = {},
                    passwordLabel = R.string.password,
                    isPasswordError = false,
                    onLoginClick = {},
                    onChangeToRegisterClick = {},
                    isLoading = false,
                    generalError = null
                )
            }

            item {
                Register(
                    usernameValue = "",
                    onUsernameValueChange = {},
                    usernameLabel = R.string.username,
                    isUsernameError = false,
                    emailValue = "",
                    onEmailValueChange = {},
                    emailLabel = R.string.email,
                    isEmailError = false,
                    passwordValue = "",
                    onPasswordValueChange = {},
                    passwordLabel = R.string.password,
                    isPasswordError = false,
                    confirmPasswordValue = "",
                    onConfirmPasswordValueChange = {},
                    confirmPasswordLabel = R.string.confirm_password,
                    isConfirmPasswordError = false,
                    onRegisterClick = {},
                    onChangeToLoginClick = {},
                    isLoading = false,
                    generalError = null
                )
            }

            item {
                Verify(
                    emailSentTo = "example@example.com",
                    verificationCodeValue = "",
                    onVerificationCodeValueChange = {},
                    verificationCodeLabel = R.string.verification_code,
                    isVerificationCodeError = false,
                    onVerifyClick = {},
                    onResendClick = {},
                    isLoading = false,
                    generalError = null
                )
            }
        }
    }
}