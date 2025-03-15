package com.reishandy.chatroom.ui

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reishandy.chatroom.model.ChangeUiState
import com.reishandy.chatroom.factory.LoginViewModelFactory
import com.reishandy.chatroom.model.account.LoginViewModel
import com.reishandy.chatroom.ui.component.Login
import com.reishandy.chatroom.ui.component.Register
import com.reishandy.chatroom.ui.component.Verify
import com.reishandy.chatroom.model.LoginUiState
import com.reishandy.chatroom.model.RegisterUiState
import com.reishandy.chatroom.model.VerifyUiState
import com.reishandy.chatroom.factory.ChangeViewModelFactory
import com.reishandy.chatroom.factory.RegisterViewModelFactory
import com.reishandy.chatroom.factory.VerifyViewModelFactory
import com.reishandy.chatroom.model.account.ChangeViewModel
import com.reishandy.chatroom.model.account.RegisterViewModel
import com.reishandy.chatroom.model.account.VerifyViewModel
import com.reishandy.chatroom.ui.component.Password
import com.reishandy.chatroom.ui.component.Username

enum class ChatroomNavItems {
    LOGIN,
    REGISTER,
    VERIFY,
    HOME,
}

@Composable
fun ChatroomApp() {
    val context: Context = LocalContext.current
    val application: Application = context.applicationContext as Application

    // ViewModels
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(application))
    val loginUiState: LoginUiState by loginViewModel.uiState.collectAsState()
    val registerViewModel: RegisterViewModel =
        viewModel(factory = RegisterViewModelFactory(application))
    val registerUiState: RegisterUiState by registerViewModel.uiState.collectAsState()
    val verifyViewModel: VerifyViewModel = viewModel(factory = VerifyViewModelFactory(application))
    val verifyUiState: VerifyUiState by verifyViewModel.uiState.collectAsState()
    val changeViewModel: ChangeViewModel = viewModel(factory = ChangeViewModelFactory(application))
    val changeUiState: ChangeUiState by changeViewModel.uiState.collectAsState()

    // Navigation
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(modifier = Modifier.fillMaxSize()) {
            NavPoints(
                context = context,
                loginViewModel = loginViewModel,
                loginUiState = loginUiState,
                registerViewModel = registerViewModel,
                registerUiState = registerUiState,
                verifyViewModel = verifyViewModel,
                verifyUiState = verifyUiState
            )

            // Dimmed background when forms are visible, TODO: Update to include dialog
            AnimatedVisibility(
                visible = changeUiState.isUsernameFormVisible || changeUiState.isPasswordFormVisible,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                )
            }

            // TODO: Use this feature
            ChangeForm(
                changeViewModel = changeViewModel,
                changeUiState = changeUiState
            )

            // TODO: Dialog popup
        }
    }
}

@Composable
fun NavPoints(
    context: Context,
    loginViewModel: LoginViewModel,
    loginUiState: LoginUiState,
    registerViewModel: RegisterViewModel,
    registerUiState: RegisterUiState,
    verifyViewModel: VerifyViewModel,
    verifyUiState: VerifyUiState
) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ChatroomNavItems.LOGIN.name,
        modifier = Modifier
    ) {
        // Login
        composable(
            route = ChatroomNavItems.LOGIN.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            // Close the app when back button is pressed
            BackHandler { (context as? Activity)?.finishAffinity() }

            Login(
                emailValue = loginViewModel.email,
                onEmailValueChange = {
                    loginViewModel.updateEmail(it)
                },
                emailLabel = loginUiState.emailLabel,
                isEmailError = loginUiState.isEmailError,
                passwordValue = loginViewModel.password,
                onPasswordValueChange = {
                    loginViewModel.updatePassword(it)
                },
                passwordLabel = loginUiState.passwordLabel,
                isPasswordError = loginUiState.isPasswordError,
                onLoginClick = {
                    if (loginViewModel.login()) {
                        loginViewModel.clearFields()
                        navController.navigate(ChatroomNavItems.HOME.name)
                    }
                },
                onChangeToRegisterClick = {
                    navController.navigate(ChatroomNavItems.REGISTER.name)
                },
            )
        }

        // Register
        composable(
            route = ChatroomNavItems.REGISTER.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            BackHandler { (context as? Activity)?.finishAffinity() }

            Register(
                usernameValue = registerViewModel.username,
                onUsernameValueChange = {
                    registerViewModel.updateUsername(it)
                },
                usernameLabel = registerUiState.usernameLabel,
                isUsernameError = registerUiState.isUsernameError,
                emailValue = registerViewModel.email,
                onEmailValueChange = {
                    registerViewModel.updateEmail(it)
                },
                emailLabel = registerUiState.emailLabel,
                isEmailError = registerUiState.isEmailError,
                passwordValue = registerViewModel.password,
                onPasswordValueChange = {
                    registerViewModel.updatePassword(it)
                },
                passwordLabel = registerUiState.passwordLabel,
                isPasswordError = registerUiState.isPasswordError,
                confirmPasswordValue = registerViewModel.confirmPassword,
                onConfirmPasswordValueChange = {
                    registerViewModel.updateConfirmPassword(it)
                },
                confirmPasswordLabel = registerUiState.confirmPasswordLabel,
                isConfirmPasswordError = registerUiState.isConfirmPasswordError,
                onRegisterClick = {
                    if (registerViewModel.register()) {
                        verifyViewModel.updateMailSentTo(registerViewModel.email)
                        navController.navigate(ChatroomNavItems.VERIFY.name)
                    }
                },
                onChangeToLoginClick = {
                    navController.navigate(ChatroomNavItems.LOGIN.name)
                }
            )
        }

        // Verify
        composable(
            route = ChatroomNavItems.VERIFY.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            BackHandler {
                navController.navigate(ChatroomNavItems.REGISTER.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                }
            }

            Verify(
                emailSentTo = verifyUiState.mailSentTo,
                verificationCodeValue = verifyViewModel.verificationCode,
                onVerificationCodeValueChange = {
                    verifyViewModel.updateVerificationCode(it)
                },
                verificationCodeLabel = verifyUiState.verificationCodeLabel,
                isVerificationCodeError = verifyUiState.isVerificationCodeError,
                onVerifyClick = {
                    if (verifyViewModel.verify()) {
                        // Clear both register and verify fields
                        registerViewModel.clearFields()
                        verifyViewModel.clearFields()

                        // Navigate to login, and clear the back stack
                        navController.navigate(ChatroomNavItems.LOGIN.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    }
                },
                onResendClick = {
                    verifyViewModel.resendVerificationCode()
                }
            )
        }

        // Home
        composable(route = ChatroomNavItems.HOME.name) {
            // TODO
        }

        // TODO: Other screens
    }
}

@Composable
fun ChangeForm(
    changeViewModel: ChangeViewModel,
    changeUiState: ChangeUiState
) {
    // Username form
    AnimatedVisibility(
        visible = changeUiState.isUsernameFormVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(300)
        ) + fadeIn(animationSpec = tween(300)),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(300)
        ) + fadeOut(animationSpec = tween(300))
    ) {
        Username(
            usernameValue = changeViewModel.username,
            onUsernameValueChange = { changeViewModel.updateUsername(it) },
            usernameLabel = changeUiState.usernameLabel,
            isUsernameError = changeUiState.isUsernameError,
            onChangeUsernameClick = {
                if (changeViewModel.changeUsername()) {
                    changeViewModel.clearFields()
                    changeViewModel.hideUsernameForm()
                }
            },
            onCancelClick = { changeViewModel.hideUsernameForm() }
        )
    }

    // Password form
    AnimatedVisibility(
        visible = changeUiState.isPasswordFormVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(300)
        ) + fadeIn(animationSpec = tween(300)),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(300)
        ) + fadeOut(animationSpec = tween(300))
    ) {
        Password(
            oldPasswordValue = changeViewModel.oldPassword,
            onOldPasswordValueChange = { changeViewModel.updateOldPassword(it) },
            oldPasswordLabel = changeUiState.oldPasswordLabel,
            isOldPasswordError = changeUiState.isOldPasswordError,
            newPasswordValue = changeViewModel.newPassword,
            onNewPasswordValueChange = { changeViewModel.updateNewPassword(it) },
            newPasswordLabel = changeUiState.newPasswordLabel,
            isNewPasswordError = changeUiState.isNewPasswordError,
            confirmPasswordValue = changeViewModel.confirmPassword,
            onConfirmPasswordValueChange = { changeViewModel.updateConfirmPassword(it) },
            confirmPasswordLabel = changeUiState.confirmPasswordLabel,
            isConfirmPasswordError = changeUiState.isConfirmPasswordError,
            onChangePasswordClick = {
                if (changeViewModel.changePassword()) {
                    changeViewModel.clearFields()
                    changeViewModel.hidePasswordForm()
                }
            },
            onCancelClick = { changeViewModel.hidePasswordForm() }
        )
    }
}