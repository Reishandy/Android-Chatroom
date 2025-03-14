package com.reishandy.chatroom.ui

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reishandy.chatroom.factory.LoginViewModelFactory
import com.reishandy.chatroom.model.auth.LoginViewModel
import com.reishandy.chatroom.ui.component.Login
import com.reishandy.chatroom.ui.component.Register
import com.reishandy.chatroom.ui.component.Verify
import com.reishandy.chatroom.data.LoginUiState
import com.reishandy.chatroom.data.RegisterUiState
import com.reishandy.chatroom.data.VerifyUiState
import com.reishandy.chatroom.factory.RegisterViewModelFactory
import com.reishandy.chatroom.factory.VerifyViewModelFactory
import com.reishandy.chatroom.model.auth.RegisterViewModel
import com.reishandy.chatroom.model.auth.VerifyViewModel

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
    val registerViewModel: RegisterViewModel = viewModel(factory = RegisterViewModelFactory(application))
    val registerUiState: RegisterUiState by registerViewModel.uiState.collectAsState()
    val verifyViewModel: VerifyViewModel = viewModel(factory = VerifyViewModelFactory(application))
    val verifyUiState: VerifyUiState by verifyViewModel.uiState.collectAsState()

    // Navigation
    NavPoints(
        context = context,
        loginViewModel = loginViewModel,
        loginUiState = loginUiState,
        registerViewModel = registerViewModel,
        registerUiState = registerUiState,
        verifyViewModel = verifyViewModel,
        verifyUiState = verifyUiState
    )
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

    Surface(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = ChatroomNavItems.VERIFY.name,
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
                        // TODO: Store refresh token, access token, and user info

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
        }
    }
}